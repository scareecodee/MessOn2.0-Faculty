package com.messon.faculty.app.presentation.ui.screens.students

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.messon.faculty.app.presentation.ui.components.FilterChipsRow
import com.messon.faculty.app.presentation.ui.components.TableCell
import com.messon.faculty.app.presentation.viewmodel.CsvUploadViewModel
import com.messon.faculty.app.presentation.viewmodel.FilePickerViewModel
import com.messon.faculty.app.theme.MessonColors
import com.messon.faculty.app.theme.MessonPalette
import com.messon.faculty.app.utils.CsvUploadMode
import com.messon.faculty.app.presentation.ui.uiState.CsvState
import com.messon.faculty.app.presentation.viewmodel.MessListVM
import com.messon.faculty.app.utils.FilePickerState
import com.messon.faculty.app.utils.parseCsv
import io.github.vinceglb.filekit.name
import kotlinx.coroutines.delay
import com.messon.faculty.composeapp.generated.resources.Res
import com.messon.faculty.composeapp.generated.resources.publicsansbold
import com.messon.faculty.composeapp.generated.resources.publicsansmedium
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.Font

@Composable
fun CsvPreviewScreen(
    filePickerViewModel: FilePickerViewModel,
    csvUploadViewModel: CsvUploadViewModel,
    messListVM: MessListVM,
    onBackClick: () -> Unit,
    onUploadSuccess: () -> Unit,
    messonColors: MessonColors,
    modifier: Modifier = Modifier,
) {

    val uploadState by csvUploadViewModel.state
        .collectAsState()

    val filePickerState by filePickerViewModel.state
        .collectAsState(FilePickerState.Idle)

    val materialColors = MaterialTheme.colorScheme
    val isDarkTheme = materialColors.background == MessonPalette.DarkOffWhite

    val chips = listOf(CsvUploadMode.INSERT.value, CsvUploadMode.UPDATE.value)
    var selected by remember { mutableStateOf(setOf(0)) }
    val fileContent = (filePickerState as? FilePickerState.Success)?.fileContent
    val selectedFile = (filePickerState as? FilePickerState.Success)?.selectedFile
    val tableData = remember(fileContent) {
        fileContent?.let { parseCsv(it) } ?: emptyList()
    }
    val selectedMess by messListVM.selectedMess.collectAsState()

    LaunchedEffect(Unit) {
        csvUploadViewModel.resetState()
    }

    LaunchedEffect(uploadState) {
        when (uploadState) {
            is CsvState.Success -> {
                delay(300)
                onUploadSuccess()
            }
            is CsvState.Error -> {
                showToast(
                    message = (uploadState as CsvState.Error).message
                )
                csvUploadViewModel.resetState()
            }
            else -> Unit
        }
    }

    if (tableData.isEmpty()) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No data found",
                color = materialColors.onBackground
            )
        }
        return
    }

    val headers = tableData.first()
    val rows = tableData.drop(1).take(20)

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(materialColors.background)
    ) {

        val (backBtnRef, headingRef, statusBarBox, table, proceedBtn, progress, fileNameRef, modeSelector) = createRefs()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                .background(materialColors.surface) // Using Material surface
                .constrainAs(statusBarBox) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        )

        IconButton(
            shape = CircleShape,
            onClick = {
                onBackClick()
            },
            modifier = Modifier.constrainAs(backBtnRef) {
                start.linkTo(parent.start, margin = 15.dp)
                top.linkTo(statusBarBox.bottom, margin = 10.dp)
            }
        ) {
            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = "back btn",
                modifier = Modifier.size(28.dp),
                tint = materialColors.onBackground
            )
        }

        Text(
            maxLines = 1,
            text = "CSV Preview",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            color = materialColors.onBackground,
            modifier = Modifier
                .constrainAs(headingRef) {
                    start.linkTo(backBtnRef.end, margin = 5.dp)
                    top.linkTo(backBtnRef.top)
                    bottom.linkTo(backBtnRef.bottom)
                }
        )

        Text(
            maxLines = 1,
            text = selectedFile?.name ?: "Empty File",
            fontSize = 14.sp,
            color = materialColors.primary, // Using Material primary color
            fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
            modifier = Modifier
                .constrainAs(fileNameRef) {
                    start.linkTo(backBtnRef.start, margin = 5.dp)
                    top.linkTo(backBtnRef.bottom, margin = 15.dp)
                }
        )

        Box(
            modifier = Modifier
                .heightIn(max = 400.dp)
                .padding(horizontal = 15.dp)
                .constrainAs(table) {
                    top.linkTo(fileNameRef.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .border(1.dp, materialColors.outline.copy(alpha = 0.3f))
        ) {
            Column(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .verticalScroll(rememberScrollState())
            ) {
                Row(modifier = Modifier.background(materialColors.primary)) {
                    headers.forEach { header ->
                        TableCell(
                            text = header,
                            isHeader = true,
                        )
                    }
                }

                rows.forEachIndexed { index, row ->
                    Row(
                        modifier = Modifier.background(
                            if (index % 2 == 0) messonColors.infoContainer
                            else materialColors.surface
                        )
                    ) {
                        row.forEach { cell ->
                            TableCell(
                                text = cell,
                                isHeader = false
                            )
                        }
                    }
                }
            }
        }

        FilterChipsRow(
            modifier = Modifier
                .constrainAs(modeSelector) {
                    top.linkTo(table.bottom, margin = 20.dp)
                    start.linkTo(parent.start, margin = 20.dp)
                    end.linkTo(parent.end, margin = 20.dp)
                }
                .padding(horizontal = 15.dp),
            chips = chips,
            selectedChips = selected,
            onSelectionChange = {
                selected = it
            }
        )

        Button(
            onClick = {
                selectedFile?.let { file ->
                    val selectedIndex = selected.firstOrNull() ?: return@Button
                    val mode = chips[selectedIndex]
                    selectedMess?.id?.let { messId ->
                        csvUploadViewModel.uploadCsv(
                            file = file,
                            mode = mode,
                            messId = messId
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(45.dp)
                .constrainAs(proceedBtn) {
                    top.linkTo(modeSelector.bottom, margin = 15.dp)
                },
            colors = ButtonDefaults.buttonColors(
                containerColor = materialColors.primary,
                contentColor = materialColors.onPrimary,
                disabledContainerColor = materialColors.surfaceVariant,
                disabledContentColor = materialColors.onSurfaceVariant
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = if (isDarkTheme) 2.dp else 4.dp
            )
        ) {
            Text(
                text = "Continue",
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White,
                fontFamily = FontFamily(Font(Res.font.publicsansbold))
            )
        }

        when (uploadState) {
            is CsvState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(materialColors.scrim.copy(alpha = 0.5f))
                        .constrainAs(progress) {
                            top.linkTo(parent.top)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = materialColors.primary
                    )
                }
            }
            else -> {}
        }
    }
}