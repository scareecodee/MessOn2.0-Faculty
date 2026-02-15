package com.messon.faculty.app.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.messon.faculty.app.presentation.ui.components.TableCell
import com.messon.faculty.app.presentation.viewmodel.CsvUploadViewModel
import com.messon.faculty.app.presentation.viewmodel.FilePickerViewModel
import com.messon.faculty.app.theme.CustomColors
import com.messon.faculty.app.theme.CustomColors.RichCerulean
import com.messon.faculty.app.utils.CsvUploadState
import com.messon.faculty.app.utils.FilePickerState
import com.messon.faculty.app.utils.parseCsv
import multiplatform.network.cmptoast.showToast


@Composable
fun CsvPreviewScreen(
    filePickerViewModel: FilePickerViewModel,
    csvUploadViewModel: CsvUploadViewModel,   // 🔥 NEW
    token: String,                            // 🔥 JWT
    onBackClick: () -> Unit,
    onUploadSuccess: () -> Unit,              // 🔥 Navigate next
    modifier: Modifier = Modifier,
) {


    val filePickerState by filePickerViewModel.state
        .collectAsState(FilePickerState.Idle)

    val uploadState by csvUploadViewModel.state
        .collectAsStateWithLifecycle()

    val fileContent = (filePickerState as? FilePickerState.Success)?.fileContent
    val selectedFile = (filePickerState as? FilePickerState.Success)?.selectedFile

    val tableData = remember(fileContent) {
        fileContent?.let { parseCsv(it) } ?: emptyList()
    }

    // 🔥 HANDLE UPLOAD STATE SIDE EFFECTS
    LaunchedEffect(uploadState) {

        when (uploadState) {

            is CsvUploadState.Success -> {
                onUploadSuccess()
            }

            is CsvUploadState.Error -> {
                showToast(
                    message =(uploadState as CsvUploadState.Error).message
                )

                csvUploadViewModel.resetState()
            }

            else -> Unit
        }
    }

    if (tableData.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No data found")
        }
        return
    }

    val headers = tableData.first()
    val rows = tableData.drop(1).take(20)

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        val (topBar, table, proceedBtn, progress) = createRefs()

        // ----------- YOUR EXISTING UI -----------

        // TABLE UI (unchanged)
        Box(
            modifier = Modifier
                .heightIn(max = 400.dp)
                .padding(horizontal = 15.dp)
                .constrainAs(table) {
                    top.linkTo(parent.top, margin = 80.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .border(1.dp, CustomColors.BlackberryCream.copy(alpha = 0.3f))
        ) {
            Column(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .verticalScroll(rememberScrollState())
            ) {
                Row(modifier = Modifier.background(RichCerulean)) {
                    headers.forEach { header ->
                        TableCell(text = header, isHeader = true)
                    }
                }

                rows.forEachIndexed { index, row ->
                    Row(
                        modifier = Modifier.background(
                            if (index % 2 == 0) Color(0xFFF0F6FA)
                            else Color.White
                        )
                    ) {
                        row.forEach { cell ->
                            TableCell(text = cell, isHeader = false)
                        }
                    }
                }
            }
        }

        // 🔥 CONTINUE BUTTON → START UPLOAD
        Button(
            onClick = {
                selectedFile?.let {
                    csvUploadViewModel.uploadCsv(token, it)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(16.dp)
                .constrainAs(proceedBtn) {
                    top.linkTo(table.bottom, margin = 20.dp)
                },
            colors = ButtonDefaults.buttonColors(
                containerColor = RichCerulean
            )
        ) {
            Text("Continue")
        }

        // 🔥 LOADING INDICATOR
        if (uploadState is CsvUploadState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .constrainAs(progress) {
                        top.linkTo(parent.top)
                    },
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
