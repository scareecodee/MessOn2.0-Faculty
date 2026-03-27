package com.messon.faculty.app.presentation.ui.screens.mess

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.faculty.app.domain.dataModel.Holiday
import com.messon.faculty.app.presentation.ui.components.CustomInputField
import com.messon.faculty.app.presentation.ui.components.TimeRow
import com.messon.faculty.app.presentation.ui.uiState.SaveHolidayState
import com.messon.faculty.app.presentation.viewmodel.MessClosureVM
import com.messon.faculty.app.presentation.viewmodel.MessListVM
import com.messon.faculty.app.theme.CustomColors
import com.messon.faculty.app.utils.formatDate
import com.messon.faculty.app.utils.toHumanReadable
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansbold
import messon_faculty.composeapp.generated.resources.publicsansmedium
import messon_faculty.composeapp.generated.resources.publicsanssemibold
import multiplatform.network.cmptoast.showToast
import network.chaintech.kmp_date_time_picker.ui.datepicker.WheelDatePickerView
import network.chaintech.kmp_date_time_picker.ui.datetimepicker.WheelDateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.TimeFormat
import network.chaintech.kmp_date_time_picker.utils.WheelPickerDefaults
import org.jetbrains.compose.resources.Font

@Composable
fun CreateHolidayScreen(
    onBackBtnClick:()-> Unit,
    messClosureVM: MessClosureVM,
    messListVM: MessListVM
) {
    val selectedMess by messListVM.selectedMess.collectAsState()
    var reason by remember { mutableStateOf("") }
    var isClosingSelected by remember { mutableStateOf(false) }
    var showDateTimePicker by remember{ mutableStateOf(false) }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }

    val saveState by messClosureVM.saveHolidayState.collectAsState()

    LaunchedEffect(saveState){
        when(saveState){
            is SaveHolidayState.Error -> {
                showToast((saveState as SaveHolidayState.Error).message)
                messClosureVM.clearSaveState()
            }
            is SaveHolidayState.Success -> {
                showToast("saved holiday success")
                messClosureVM.clearSaveState()
            }

            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomColors.OffWhite)
    ) {
        Box(
            modifier = Modifier
                .offset(x = 200.dp, y = (-100).dp)
                .size(250.dp)
                .background(CustomColors.BlackberryCream.copy(alpha = 0.15f), CircleShape)
        )

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val (backBtnRef,headingRef, form,indicator,statusBarBox,subheading) = createRefs()

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                    .background(CustomColors.LightGrayBlue)
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
                Icon(Icons.Default.ChevronLeft, contentDescription = "back", modifier = Modifier.size(28.dp)
                )
            }


            Text(
                text = "Add Holiday ",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                modifier = Modifier.constrainAs(headingRef) {
                    start.linkTo(backBtnRef.end, 5.dp)
                    centerVerticallyTo(backBtnRef)
                }
            )

            Text(
                text = "Fill out details to add",
                fontSize = 14.sp,
                color = Color.DarkGray,
                fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                modifier = Modifier.constrainAs(subheading) {
                    top.linkTo(backBtnRef.bottom, margin = 8.dp)
                    start.linkTo(backBtnRef.start, 4.dp)
                }
            )

            LazyColumn(
                modifier = Modifier.constrainAs(form) {
                    top.linkTo(subheading.bottom, margin = 32.dp)
                    bottom.linkTo(parent.bottom, margin = 7.dp)
                    start.linkTo(parent.start, margin = 12.dp)
                    end.linkTo(parent.end, margin = 12.dp)
                    width = Dimension.fillToConstraints
                    height= Dimension.fillToConstraints
                }.imePadding(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item{
                    CustomInputField(
                        value = reason,
                        onValueChange = { reason= it },
                        label = "Reason for holiday",
                        icon = Icons.Default.Home
                    )
                }

                item{
                    TimeRow(
                        label = "Opening",
                        time = formatDate(startDate),
                        icon = Icons.Default.CalendarToday,
                        iconColor = CustomColors.HoneyBronze,
                        onClick = {
                            showDateTimePicker = true
                            isClosingSelected = false
                        }
                    )
                }

                item{
                    TimeRow(
                        label = "Closing",
                        time = formatDate( endDate),
                        icon = Icons.Default.Schedule,
                        iconColor = CustomColors.RichCerulean,
                        onClick =
                            {
                                showDateTimePicker = true
                                isClosingSelected = true

                            }
                    )
                }


                item{
                    Spacer(
                        modifier = Modifier.size(10.dp)
                    )
                }

                item{
                    Button(
                        enabled = saveState !is SaveHolidayState.Loading,
                        onClick = {
                            messClosureVM.addHoliday(
                                holiday = Holiday(
                                    startDate = startDate,
                                    endDate = endDate,
                                    reason = reason
                                ),
                                messId = selectedMess?.id ?: ""
                            )
                        },
                        modifier = Modifier.padding(5.dp)
                            .fillMaxWidth()
                            .height(55.dp)
                            .shadow(12.dp, RoundedCornerShape(24.dp)),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CustomColors.RichCerulean, disabledContainerColor = CustomColors.RichCerulean)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                "Add Holiday",
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(Color.White.copy(alpha = 0.2f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
                            }
                        }
                    }
                }
            }

            if(saveState is SaveHolidayState.Loading){
                CircularProgressIndicator(
                    modifier = Modifier.constrainAs(indicator){
                        centerTo(parent)
                    }
                )
            }
        }

    }
    WheelDatePickerView(
        modifier = Modifier.safeContentPadding(),
        title =if(isClosingSelected) "Set Closing Time" else "Set Opening Time",
        titleStyle = TextStyle(
            fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
            fontSize = 16.sp
        ),
        doneLabelStyle =TextStyle(
            fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
            fontSize = 16.sp
        ),
        showDatePicker =showDateTimePicker,
        height = 220.dp,
        yearsRange = IntRange(2026,2122),
        shape = RoundedCornerShape(30.dp),
        defaultDateTextStyle = TextStyle(
            fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
            fontSize = 12.sp
        ),
        selectedDateTextStyle = TextStyle(
            fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
            fontSize = 14.sp
        ),
        selectorProperties = WheelPickerDefaults.selectorProperties(
            enabled=true,
            borderColor = CustomColors.RichCerulean
        ),
        onDoneClick = {
            showDateTimePicker=false
            if(isClosingSelected) endDate=it.toString() else startDate=it.toString()
        },
        onDismiss = {showDateTimePicker=false},
        dateTimePickerView= DateTimePickerView.BOTTOM_SHEET_VIEW,
    )
}


