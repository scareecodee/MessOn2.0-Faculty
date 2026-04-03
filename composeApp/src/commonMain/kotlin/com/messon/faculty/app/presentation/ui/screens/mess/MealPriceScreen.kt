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
import com.messon.faculty.app.theme.CustomColors
import com.messon.faculty.app.theme.CustomColors.LightGrayBlue
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansbold
import messon_faculty.composeapp.generated.resources.publicsansmedium
import messon_faculty.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun MealPriceScreen(
    onBackBtnClick: () -> Unit
) {
    var dailyPrice by remember { mutableStateOf("250") }
    var isSaved by remember { mutableStateOf(false) }

    LaunchedEffect(isSaved) {
        if (isSaved) {
            kotlinx.coroutines.delay(3000)
            isSaved = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomColors.OffWhite),
        contentAlignment = Alignment.Center
    ) {

        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {

            val (statusBarBox, backBtnRef, headingRef, listRef) = createRefs()

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(
                        WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp
                    )
                    .background(LightGrayBlue)
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
                    modifier = Modifier.size(28.dp)
                )
            }

            Text(
                text = "Set Price",
                fontSize = 22.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansbold)),
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
                            .shadow(
                                elevation = 20.dp,
                                shape = RoundedCornerShape(56.dp),
                                spotColor = Color.Black.copy(alpha = 0.1f)
                            ),
                        shape = RoundedCornerShape(56.dp),
                        color = Color.White,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            CustomColors.LightBlue
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 48.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Surface(
                                color = CustomColors.LightBlue,
                                shape = RoundedCornerShape(100),
                                modifier = Modifier.padding(bottom = 24.dp)
                            ) {
                                Text(
                                    text = "Each Meal Price",
                                    modifier = Modifier.padding(
                                        horizontal = 20.dp,
                                        vertical = 8.dp
                                    ),
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                                        color = CustomColors.RichCerulean,
                                    )
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "₹",
                                    style = TextStyle(
                                        fontSize = 48.sp,
                                        fontWeight = FontWeight.Light,
                                        color = CustomColors.LightGrayBlue
                                    )
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                BasicTextField(
                                    value = dailyPrice,
                                    onValueChange = {
                                        dailyPrice = it
                                        isSaved = false
                                    },
                                    textStyle = TextStyle(
                                        fontSize = 65.sp,
                                        fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                                        color = CustomColors.BlackberryCream,
                                        textAlign = TextAlign.Center
                                    ),
                                    cursorBrush = SolidColor(CustomColors.RichCerulean),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    singleLine = true,
                                    decorationBox = { innerTextField ->
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            innerTextField()
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Box(
                                                modifier = Modifier
                                                    .width(160.dp)
                                                    .height(2.dp)
                                                    .background(CustomColors.LightBlue)
                                            )
                                        }
                                    }
                                )
                            }

                            Text(
                                text = "Flat rate for all-day meal access",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                                    color = CustomColors.LightGrayBlue,
                                ),
                                modifier = Modifier.padding(top = 16.dp, bottom = 40.dp)
                            )

                            val buttonColor by animateColorAsState(
                                targetValue = if (isSaved) CustomColors.SuccessGreen else CustomColors.BlackberryCream,
                                animationSpec = tween(500),
                                label = "btnColor"
                            )

                            val buttonScale by animateFloatAsState(
                                targetValue = if (isSaved) 1.02f else 1f,
                                label = "btnScale"
                            )



                            Button(
                                onClick = { isSaved = true },
                                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                                shape = RoundedCornerShape(100),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(55.dp)
                                    .scale(buttonScale),
                                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                    Icon(
                                        imageVector =Icons.Default.CheckCircle ,
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = if (isSaved) "Price Updated Successfully" else "Confirm Global Price",
                                        style = TextStyle(
                                            fontSize = 15.sp,
                                            fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    Surface(
                        modifier = Modifier
                            .padding(horizontal = 14.dp, vertical = 16.dp)
                            .fillMaxWidth(),
                        color = CustomColors.LightBlue.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(24.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            CustomColors.RichCerulean.copy(alpha = 0.1f)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(24.dp),
                            verticalAlignment = Alignment.Top
                        ) {

                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = CustomColors.RichCerulean,
                                modifier = Modifier.size(20.dp)
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Text(
                                    text = "System Notice",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = CustomColors.RichCerulean
                                    )
                                )

                                Text(
                                    text = "Changes applied here are global for selected mess. This price will be used for billing.",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        color = CustomColors.RichCerulean.copy(alpha = 0.7f),
                                        fontWeight = FontWeight.Medium,
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}