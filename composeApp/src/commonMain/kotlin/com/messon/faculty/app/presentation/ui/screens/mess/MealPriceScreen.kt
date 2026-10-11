package com.messon.faculty.app.presentation.ui.screens.mess

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.faculty.app.presentation.ui.components.TimeRow
import com.messon.faculty.app.utils.toReadableFormat
import com.messon.faculty.composeapp.generated.resources.Res
import com.messon.faculty.composeapp.generated.resources.publicsansbold
import com.messon.faculty.composeapp.generated.resources.publicsansmedium
import com.messon.faculty.composeapp.generated.resources.publicsanssemibold
import network.chaintech.kmp_date_time_picker.ui.datepicker.WheelDatePickerView
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.WheelPickerDefaults
import org.jetbrains.compose.resources.Font

@Composable
fun MealPriceScreen(
    onBackBtnClick: () -> Unit
) {
    var startingDate by remember { mutableStateOf("") }
    var showDateTimePicker by remember { mutableStateOf(false) }
    var dailyPrice by remember { mutableStateOf("250") }
    var isSaved by remember { mutableStateOf(false) }

    LaunchedEffect(isSaved) {
        if (isSaved) {
            kotlinx.coroutines.delay(3000)
            isSaved = false
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (statusBarBox, backBtnRef, headingRef, listRef) = createRefs()

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .constrainAs(statusBarBox) { top.linkTo(parent.top) }
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
                text = "Set Price",
                fontSize = 22.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.constrainAs(headingRef) {
                    start.linkTo(backBtnRef.end, 5.dp)
                    centerVerticallyTo(backBtnRef)
                }
            )

            LazyColumn(
                modifier = Modifier.constrainAs(listRef) {
                    top.linkTo(headingRef.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                item {
                    Surface(
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 16.dp)
                            .fillMaxWidth()
                            .shadow(20.dp, RoundedCornerShape(56.dp), spotColor = Color.Black.copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(56.dp),
                        color = MaterialTheme.colorScheme.surface,
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 48.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Surface(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(100),
                                modifier = Modifier.padding(bottom = 24.dp)
                            ) {
                                Text(
                                    text = "Each Meal Price",
                                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "₹",
                                    style = TextStyle(fontSize = 48.sp, fontWeight = FontWeight.Light, color = MaterialTheme.colorScheme.outline)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                BasicTextField(
                                    value = dailyPrice,
                                    onValueChange = { dailyPrice = it; isSaved = false },
                                    textStyle = TextStyle(
                                        fontSize = 65.sp,
                                        fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                                        color = MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center
                                    ),
                                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    singleLine = true,
                                    decorationBox = { innerTextField ->
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            innerTextField()
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Box(modifier = Modifier.width(160.dp).height(2.dp).background(MaterialTheme.colorScheme.outlineVariant))
                                        }
                                    }
                                )
                            }

                            Text(
                                text = "Flat rate for all-day meal access",
                                style = TextStyle(fontSize = 14.sp, fontFamily = FontFamily(Font(Res.font.publicsansmedium)), color = MaterialTheme.colorScheme.onSurfaceVariant),
                                modifier = Modifier.padding(top = 16.dp, bottom = 40.dp)
                            )

                            val buttonColor by animateColorAsState(
                                targetValue = if (isSaved) Color(0xFF4CAF50) else MaterialTheme.colorScheme.primary,
                                animationSpec = tween(500),
                                label = "btnColor"
                            )
                            val buttonScale by animateFloatAsState(targetValue = if (isSaved) 1.02f else 1f, label = "btnScale")

                            TimeRow(
                                label = "Starting",
                                time = startingDate,
                                icon = Icons.Default.CalendarToday,
                                iconColor = MaterialTheme.colorScheme.primary,
                                onClick = { showDateTimePicker = true }
                            )

                            Spacer(modifier = Modifier.height(25.dp))

                            Button(
                                onClick = { isSaved = true },
                                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                                shape = RoundedCornerShape(100),
                                modifier = Modifier.fillMaxWidth().height(55.dp).scale(buttonScale),
                                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(24.dp))
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = if (isSaved) "Price Updated Successfully" else "Confirm Global Price",
                                        style = TextStyle(fontSize = 15.sp, fontFamily = FontFamily(Font(Res.font.publicsanssemibold)))
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    Surface(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 16.dp).fillMaxWidth(),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Row(modifier = Modifier.padding(24.dp), verticalAlignment = Alignment.Top) {
                            Icon(Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                                Text("System Notice", style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary))
                                Text("Changes applied here are global for selected mess. This price will be used for billing.", style = TextStyle(fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)))
                            }
                        }
                    }
                }
            }
        }
    }

    WheelDatePickerView(
        modifier = Modifier.safeContentPadding(),
        title = "Set starting date",
        titleStyle = TextStyle(fontFamily = FontFamily(Font(Res.font.publicsanssemibold)), fontSize = 16.sp),
        doneLabelStyle = TextStyle(fontFamily = FontFamily(Font(Res.font.publicsanssemibold)), fontSize = 16.sp),
        showDatePicker = showDateTimePicker,
        height = 220.dp,
        yearsRange = IntRange(2026, 2122),
        shape = RoundedCornerShape(30.dp),
        defaultDateTextStyle = TextStyle(fontFamily = FontFamily(Font(Res.font.publicsansmedium)), fontSize = 12.sp),
        selectedDateTextStyle = TextStyle(fontFamily = FontFamily(Font(Res.font.publicsanssemibold)), fontSize = 14.sp),
        selectorProperties = WheelPickerDefaults.selectorProperties(enabled = true, borderColor = MaterialTheme.colorScheme.primary),
        onDoneClick = { showDateTimePicker = false; startingDate = it.toReadableFormat() },
        onDismiss = { showDateTimePicker = false },
        dateTimePickerView = DateTimePickerView.BOTTOM_SHEET_VIEW,
    )
}