package com.messon.faculty.app.presentation.ui.screens.students

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.faculty.app.presentation.ui.components.ConflictItem
import com.messon.faculty.app.presentation.viewmodel.AddStudentManuallyVM
import com.messon.faculty.app.presentation.ui.uiState.AddStudentsManuallyState
import com.messon.faculty.composeapp.generated.resources.Res
import com.messon.faculty.composeapp.generated.resources.publicsansbold
import com.messon.faculty.composeapp.generated.resources.publicsansmedium
import org.jetbrains.compose.resources.Font

@Composable
fun DataValidityScreen(
    onCancel: () -> Unit,
    onBackBtnClick: () -> Unit,
    addStudentManuallyVM: AddStudentManuallyVM
) {
    val uiState by addStudentManuallyVM.state.collectAsState()
    var activeTab by remember { mutableStateOf(0) }

    val data = (uiState as? AddStudentsManuallyState.Success)?.addStudentsManuallyRes
        ?: return

    val themeColors = MaterialTheme.colorScheme
    val dynamicPrimary = themeColors.primary
    val dynamicBackground = themeColors.background
    val dynamicOnSurface = themeColors.onSurface
    val dynamicOutline = themeColors.outline
    val dynamicOnSurfaceVariant = themeColors.onSurfaceVariant

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = dynamicBackground
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(dynamicBackground)
        ) {
            val (statusBarBox, backBtnRef, listRef, subtitle, headingRef, progress) = createRefs()

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                    .background(dynamicOutline)
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
                    tint = dynamicOnSurface,
                    modifier = Modifier.size(28.dp)
                )
            }

            Text(
                text = "Data Validity",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                color = dynamicOnSurface,
                modifier = Modifier.constrainAs(headingRef) {
                    start.linkTo(backBtnRef.end, 5.dp)
                    centerVerticallyTo(backBtnRef)
                }
            )

            Text(
                text = "Review your data before finalizing",
                color = dynamicOnSurfaceVariant,
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
                    TabRow(
                        containerColor = Color.Transparent,
                        indicator = { tabPositions ->
                            TabRowDefaults.Indicator(
                                color = dynamicPrimary,
                                modifier = Modifier.tabIndicatorOffset(tabPositions[activeTab])
                            )
                        },
                        selectedTabIndex = activeTab,
                        contentColor = dynamicPrimary,
                        modifier = Modifier.padding(horizontal = 18.dp)
                    ) {
                        Tab(
                            enabled = false,
                            selected = activeTab == 1,
                            onClick = { activeTab = 1 },
                            text = {
                                Text(
                                    "Conflicts (${data.conflicts.size})",
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                                    color = if (activeTab == 1)
                                        dynamicPrimary
                                    else dynamicOnSurfaceVariant
                                )
                            }
                        )
                    }

                    Spacer(Modifier.height(16.dp))
                }

                if (data.conflicts.isEmpty()) {
                    item { EmptyState("No conflicts detected", MaterialTheme.colorScheme) }
                } else {
                    items(data.conflicts) { conflict ->
                        ConflictItem(conflict.toString())
                    }
                }

                item {
                    PreviewActionButtons(
                        isValid = data.conflicts.isEmpty(),
                        errorCount = data.conflicts.size,
                        onConfirm = {

                        },
                        onCancel = onCancel,
                        materialColors = MaterialTheme.colorScheme,
                    )
                }
            }
        }
    }
}