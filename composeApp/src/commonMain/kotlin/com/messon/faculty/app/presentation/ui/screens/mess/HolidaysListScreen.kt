package com.messon.faculty.app.presentation.ui.screens.mess

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.faculty.app.domain.dataModel.Holiday
import com.messon.faculty.app.domain.dataModel.HolidayRes
import com.messon.faculty.app.presentation.ui.components.HolidayCard
import com.messon.faculty.app.presentation.ui.uiState.FetchHolidaysState
import com.messon.faculty.app.presentation.viewmodel.MessClosureVM
import com.messon.faculty.app.presentation.viewmodel.MessListVM
import com.messon.faculty.composeapp.generated.resources.Res
import com.messon.faculty.composeapp.generated.resources.publicsansbold
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.Font

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MessHolidaysScreen(
    onBackBtnClick: () -> Unit,
    onAddIconClick: () -> Unit,
    messClosureVM: MessClosureVM,
    messListVM: MessListVM
) {
    val selectedMess by messListVM.selectedMess.collectAsState()
    val fetchState by messClosureVM.fetchHolidayState.collectAsState()
    var holidays by remember { mutableStateOf(emptyList<HolidayRes>()) }

    LaunchedEffect(Unit) {
        messClosureVM.fetchHoliday(selectedMess?.id ?: "")
    }

    LaunchedEffect(fetchState) {
        when (fetchState) {
            is FetchHolidaysState.Success -> {
                holidays = (fetchState as FetchHolidaysState.Success).holidays
            }
            is FetchHolidaysState.Error -> {
                showToast("Error loading holidays")
            }
            else -> {}
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (list, addIcon, statusBarBox, backBtnRef, headingRef) = createRefs()

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .constrainAs(statusBarBox) {
                        top.linkTo(parent.top)
                    }
            )

            IconButton(
                onClick = { onBackBtnClick() },
                modifier = Modifier.constrainAs(backBtnRef) {
                    start.linkTo(parent.start, 15.dp)
                    top.linkTo(statusBarBox.bottom, 10.dp)
                }
            ) {
                Icon(
                    Icons.Default.ChevronLeft,
                    contentDescription = "back",
                    modifier = Modifier.size(28.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            Text(
                text = "Holidays List",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.constrainAs(headingRef) {
                    start.linkTo(backBtnRef.end, 5.dp)
                    centerVerticallyTo(backBtnRef)
                }
            )

            IconButton(
                onClick = onAddIconClick,
                shape = RoundedCornerShape(15.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .constrainAs(addIcon) {
                        end.linkTo(parent.end, margin = 16.dp)
                        centerVerticallyTo(backBtnRef)
                    }
                    .size(45.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add holidays",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(25.dp)
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(list) {
                        top.linkTo(backBtnRef.bottom, margin = 10.dp)
                        bottom.linkTo(parent.bottom, margin = 5.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                contentPadding = PaddingValues(bottom = 120.dp)
            ) {
                items(holidays) { holiday ->
                    HolidayCard(
                        holiday = Holiday(
                            startDate = holiday.startDate,
                            endDate = holiday.endDate,
                            reason = holiday.reason
                        )
                    )
                }
            }
        }
    }
}