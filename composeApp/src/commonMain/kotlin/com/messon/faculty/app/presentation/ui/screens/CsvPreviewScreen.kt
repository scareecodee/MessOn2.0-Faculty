package com.messon.faculty.app.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.messon.faculty.app.presentation.ui.components.TableCell
import com.messon.faculty.app.presentation.viewmodel.FilePickerViewModel
import com.messon.faculty.app.theme.CustomColors
import com.messon.faculty.app.theme.CustomColors.RichCerulean
import com.messon.faculty.app.utils.FilePickerState
import com.messon.faculty.app.utils.parseCsv
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansbold
import org.jetbrains.compose.resources.Font


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CsvPreviewScreen(
    filePickerViewModel: FilePickerViewModel,
    onBackClick: () -> Unit,
    onProceedClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val filePickerState by filePickerViewModel.state.collectAsState(FilePickerState.Idle)
    val fileContent = (filePickerState as? FilePickerState.Success)?.fileContent
    val tableData = remember(fileContent) {
        fileContent?.let { parseCsv(it) } ?: emptyList()
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

        val (topBar, table, proceedBtn,statusBarBox,fileNameRef) = createRefs()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                .background(CustomColors.LightGrayBlue)
                .constrainAs(statusBarBox) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .constrainAs(topBar) {
                    top.linkTo(statusBarBox.bottom, margin = 10.dp)
                    start.linkTo(parent.start)
                }
        ) {
            IconButton(
                shape = CircleShape,
                onClick =onBackClick,
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = "back btn",
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(Modifier.width(5.dp))

            Text(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                text = "CSV Preview",
                color = Color.Black
            )
        }

        Text(
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            text =when(filePickerState){
                is FilePickerState.Success-> (filePickerState as FilePickerState.Success).selectedFile.name
                else -> "Unable to load"
            },
            color = Color.Black,
            modifier = Modifier.constrainAs(fileNameRef){
                top.linkTo(topBar.bottom, margin = 8.dp)
                start.linkTo(topBar.start)
            }
        )

        Box(
            modifier = Modifier.wrapContentHeight()
                .heightIn(max =400.dp)
                .padding(horizontal = 16.dp)
                .constrainAs(table) {
                    top.linkTo(topBar.bottom, margin = 5.dp)
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

        Button(
            onClick = onProceedClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(16.dp)
                .constrainAs(proceedBtn) {
                    top.linkTo(table.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            colors = ButtonDefaults.buttonColors(
                containerColor = RichCerulean
            )
        ) {
            Text("Proceed")
        }
    }
}
