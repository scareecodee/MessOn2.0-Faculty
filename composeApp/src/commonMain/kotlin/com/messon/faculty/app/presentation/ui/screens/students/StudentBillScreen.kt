package com.messon.faculty.app.presentation.ui.screens.students

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import co.touchlab.kermit.Logger
import com.messon.faculty.app.domain.dataModel.StudentDetailedBillHistory
import com.messon.faculty.app.presentation.ui.components.StudentBillCard
import com.messon.faculty.app.presentation.ui.components.StudentMealTable
import com.messon.faculty.app.presentation.ui.uiState.StudentBillState
import com.messon.faculty.app.presentation.viewmodel.StudentBillVM
import com.messon.faculty.composeapp.generated.resources.Res
import com.messon.faculty.composeapp.generated.resources.publicsansbold
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.Font

@Composable
fun StudentBillScreen(
    onBackClick: () -> Unit,
    studentBillVM: StudentBillVM
) {
    val fetchState by studentBillVM.consumptionState.collectAsState()
    var data by remember { mutableStateOf<StudentDetailedBillHistory?>(null) }

    LaunchedEffect(fetchState) {
        when (fetchState) {
            is StudentBillState.Success -> {
                data = (fetchState as StudentBillState.Success).data
            }

            is StudentBillState.Error -> {
                showToast(message = (fetchState as StudentBillState.Error).message)
            }
            else -> {
            }
        }
    }

    val themeColors = MaterialTheme.colorScheme
    val dynamicBackground = themeColors.background
    val dynamicOnSurface = themeColors.onSurface
    val dynamicOutline = themeColors.outline

    val Log = Logger.withTag("student bill screen")
    Log.d(tag = "student bill screen", message = { data.toString() })

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = dynamicBackground
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
        ) {
            val (contentRef, buttonRef, statusBarBox, backBtnRef, headingRef, billCard) = createRefs()

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
                    onBackClick()
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
                text = "Student Bill",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                color = dynamicOnSurface,
                modifier = Modifier.constrainAs(headingRef) {
                    start.linkTo(backBtnRef.end, 5.dp)
                    centerVerticallyTo(backBtnRef)
                }
            )

            if (fetchState is StudentBillState.Success) {
                val bill = data?.bill
                val history = data?.history
                if (!bill.isNullOrEmpty()) {
                    StudentBillCard(
                        student = bill.first(),
                        modifier = Modifier.constrainAs(billCard) {
                            top.linkTo(headingRef.bottom, margin = 15.dp)
                            end.linkTo(parent.end, margin = 4.dp)
                            start.linkTo(parent.start, margin = 4.dp)
                            width = Dimension.fillToConstraints
                        }
                    )
                }

                if (!history.isNullOrEmpty()) {
                    StudentMealTable(
                        meals = history,
                        modifier = Modifier.constrainAs(contentRef) {
                            if (!bill.isNullOrEmpty()) {
                                top.linkTo(billCard.bottom, margin = 10.dp)
                            } else {
                                top.linkTo(headingRef.bottom, margin = 10.dp)
                            }
                            bottom.linkTo(parent.bottom, margin = 8.dp)
                            end.linkTo(parent.end, margin = 12.dp)
                            start.linkTo(parent.start, margin = 12.dp)
                            width = Dimension.fillToConstraints
                            height = Dimension.fillToConstraints
                        }
                    )
                }
            }
        }
    }
}