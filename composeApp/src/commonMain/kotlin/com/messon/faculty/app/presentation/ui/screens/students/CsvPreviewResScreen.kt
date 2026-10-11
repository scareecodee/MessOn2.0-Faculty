package com.messon.faculty.app.presentation.ui.screens.students

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.faculty.app.domain.dataModel.CsvPreviewRes
import com.messon.faculty.app.presentation.ui.components.ConflictItem
import com.messon.faculty.app.presentation.ui.components.ErrorItem
import com.messon.faculty.app.presentation.viewmodel.CsvUploadViewModel
import com.messon.faculty.app.theme.MessonColors
import com.messon.faculty.app.theme.MessonPalette
import com.messon.faculty.app.presentation.ui.uiState.CsvSaveChangesState
import com.messon.faculty.app.presentation.ui.uiState.CsvState
import com.messon.faculty.app.presentation.viewmodel.MessListVM
import com.messon.faculty.composeapp.generated.resources.Res
import com.messon.faculty.composeapp.generated.resources.publicsansbold
import com.messon.faculty.composeapp.generated.resources.publicsansmedium
import com.messon.faculty.composeapp.generated.resources.publicsansregular
import com.messon.faculty.composeapp.generated.resources.publicsanssemibold
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.Font

@Composable
fun CsvPreviewResScreen(
    onCancel: () -> Unit,
    onBackBtnClick: () -> Unit,
    csvUploadViewModel: CsvUploadViewModel,
    messListVM: MessListVM,
    messonColors: MessonColors
) {
    val saveChangesState by csvUploadViewModel.saveChangesState.collectAsState()
    val uploadState by csvUploadViewModel.state.collectAsState()
    var activeTab by remember { mutableStateOf(0) }

    val materialColors = MaterialTheme.colorScheme

    val data = (uploadState as? CsvState.Success)?.csvPreviewRes
        ?: return

    val selectedMess by messListVM.selectedMess.collectAsState()

    LaunchedEffect(Unit) {
        csvUploadViewModel.resetSaveChangesState()
    }

    LaunchedEffect(saveChangesState) {
        when (saveChangesState) {
            is CsvSaveChangesState.Success -> {
                if ((saveChangesState as CsvSaveChangesState.Success).isActive) {
                    showToast(
                        message = "Changes saved successfully"
                    )
                } else {
                    showToast(
                        message = "Token Expired. Try again"
                    )
                }
            }
            is CsvSaveChangesState.Error -> {
                showToast(
                    message = (uploadState as CsvState.Error).message
                )
                csvUploadViewModel.resetSaveChangesState()
            }
            else -> Unit
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(materialColors.background)
    ) {

        val (statusBarBox, backBtnRef, listRef, subtitle, headingRef, progress) = createRefs()

        Box(
            Modifier
                .fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                .background(materialColors.surface)
                .constrainAs(statusBarBox) {
                    top.linkTo(parent.top)
                }
        )

        IconButton(
            onClick = {
                onBackBtnClick()
            },
            modifier = Modifier.constrainAs(backBtnRef) {
                start.linkTo(parent.start, 15.dp)
                top.linkTo(statusBarBox.bottom, 10.dp)
            }
        ) {
            Icon(
                Icons.Default.ChevronLeft,
                contentDescription = "back",
                modifier = Modifier.size(28.dp),
                tint = materialColors.onBackground
            )
        }

        Text(
            text = "Data Validity",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            color = materialColors.onBackground,
            modifier = Modifier.constrainAs(headingRef) {
                start.linkTo(backBtnRef.end, 5.dp)
                centerVerticallyTo(backBtnRef)
            }
        )

        Text(
            text = "Review your data before finalizing",
            color = materialColors.onSurfaceVariant,
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
            modifier = Modifier.constrainAs(subtitle) {
                top.linkTo(backBtnRef.bottom, 4.dp)
                start.linkTo(backBtnRef.start)
            }
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.constrainAs(listRef) {
                top.linkTo(subtitle.bottom, margin = 5.dp)
                bottom.linkTo(parent.bottom, margin = 5.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
            },
            contentPadding = PaddingValues(bottom = 12.dp)
        ) {

            item {
                HeaderSection(
                    data = data,
                    messonColors = messonColors,
                    materialColors = materialColors
                )
            }

            item {
                TabRow(
                    containerColor = Color.Transparent,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            color = materialColors.primary,
                            modifier = Modifier.tabIndicatorOffset(tabPositions[activeTab])
                        )
                    },
                    selectedTabIndex = activeTab,
                    contentColor = materialColors.primary,
                    modifier = Modifier.padding(horizontal = 18.dp)
                ) {

                    Tab(
                        selected = activeTab == 0,
                        onClick = { activeTab = 0 },
                        text = {
                            Text(
                                "Errors (${data.errors.size})",
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                                color = if (activeTab == 0)
                                    materialColors.primary
                                else materialColors.onSurfaceVariant
                            )
                        }
                    )

                    Tab(
                        selected = activeTab == 1,
                        onClick = { activeTab = 1 },
                        text = {
                            Text(
                                "Conflicts (${data.conflicts.size})",
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                                color = if (activeTab == 1)
                                    materialColors.primary
                                else materialColors.onSurfaceVariant
                            )
                        }
                    )
                }

                Spacer(Modifier.height(16.dp))
            }

            if (activeTab == 0) {

                if (data.errors.isEmpty()) {
                    item { EmptyState(
                        message = "No validation errors found",
                        materialColors = materialColors
                    ) }
                } else {
                    items(data.errors) { error ->
                        ErrorItem(
                            error = error,
                        )
                    }
                }

            } else {

                if (data.conflicts.isEmpty()) {
                    item { EmptyState(
                        message = "No conflicts detected",
                        materialColors = materialColors
                    ) }
                } else {
                    items(data.conflicts) { conflict ->
                        ConflictItem(
                            message = conflict.toString(),
                        )
                    }
                }
            }

            item {
                PreviewActionButtons(
                    isValid = data.errors.isEmpty(),
                    errorCount = data.errors.size,
                    onConfirm = {
                        data.importToken?.let { importToken ->
                            selectedMess?.id?.let { messId ->
                                csvUploadViewModel.saveChanges(
                                    importToken = importToken,
                                    messId = messId
                                )
                            }
                        }
                    },
                    onCancel = onCancel,
                    materialColors = materialColors
                )
            }
        }

        when (saveChangesState) {
            is CsvSaveChangesState.Loading -> {
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

@Composable
fun HeaderSection(
    data: CsvPreviewRes,
    messonColors: MessonColors,
    materialColors: ColorScheme
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp, start = 24.dp, end = 24.dp)
    ) {
        val (stats, expiry) = createRefs()

        Row(
            modifier = Modifier.constrainAs(stats) {
                top.linkTo(parent.top, 24.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SummaryCard(
                label = "New Rows",
                value = data.newRows ?: 0,
                color = materialColors.primary,
                bgColor = messonColors.infoContainer,
                icon = Icons.Default.AddCircle,
                materialColors = materialColors,
                modifier = Modifier.weight(1f)
            )
            SummaryCard(
                label = "Updates",
                value = data.updateRows ?: 0,
                color = materialColors.secondary,
                bgColor = messonColors.warningContainer,
                icon = Icons.Default.Refresh,
                materialColors = materialColors,
                modifier = Modifier.weight(1f)
            )
        }

        if (!data.expiresAt.isNullOrEmpty()) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = materialColors.surface,
                border = BorderStroke(1.dp, materialColors.outline.copy(alpha = 0.2f)),
                modifier = Modifier.constrainAs(expiry) {
                    top.linkTo(stats.bottom, 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = null,
                        tint = materialColors.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Expires at ${data.expiresAt}",
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(Res.font.publicsansregular)),
                        color = materialColors.onBackground
                    )
                }
            }
        }
    }
}

@Composable
fun SummaryCard(
    label: String,
    value: Int,
    color: Color,
    bgColor: Color,
    icon: ImageVector,
    materialColors: ColorScheme,
    modifier: Modifier = Modifier
) {
    Surface(
        color = bgColor,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    label,
                    color = materialColors.onSurface.copy(alpha = 0.6f),
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsansbold))
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(16.dp)
                )
            }
            Text(
                value.toString(),
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansregular)),
                color = color
            )
        }
    }
}

@Composable
fun PreviewActionButtons(
    isValid: Boolean,
    errorCount: Int,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    materialColors: ColorScheme
) {
    Surface(
        color = materialColors.background,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(24.dp)) {
            Button(
                onClick = onConfirm,
                enabled = isValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = materialColors.primary,
                    contentColor = materialColors.onPrimary,
                    disabledContainerColor = materialColors.primary.copy(alpha = 0.3f),
                    disabledContentColor = materialColors.onPrimary.copy(alpha = 0.5f)
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = if (materialColors.background == MessonPalette.DarkOffWhite) 2.dp else 4.dp
                )
            ) {
                Text(
                    if (isValid) "Confirm & Import" else "Fix $errorCount errors to continue",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                    color=Color.White
                )
            }
            TextButton(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = materialColors.onBackground
                ),
                onClick = onCancel,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(
                    "Cancel",
                    color = materialColors.onBackground,
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsanssemibold))
                )
            }
        }
    }
}

@Composable
fun EmptyState(
    message: String,
    materialColors: ColorScheme
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(48.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
            color = materialColors.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}