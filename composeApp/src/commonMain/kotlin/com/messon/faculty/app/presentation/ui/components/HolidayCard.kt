package com.messon.faculty.app.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.EventBusy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.faculty.app.domain.dataModel.Holiday
import com.messon.faculty.app.theme.CustomColors
import com.messon.faculty.app.utils.formatDate
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansmedium
import messon_faculty.composeapp.generated.resources.publicsansregular
import messon_faculty.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun HolidayCard(holiday: Holiday) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .border(1.dp, CustomColors.RichCerulean, RoundedCornerShape(50))
                        .background(CustomColors.RichCerulean.copy(alpha = 0.1f), RoundedCornerShape(50))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text ="Holiday",
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                        color = Color.Black,
                        letterSpacing = 1.sp
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Coffee,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Mess Closed",
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = holiday.reason,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val (startBox, endBox, icon) = createRefs()

                DateBox(
                    label = "Start",
                    date = formatDate(holiday.startDate),
                    color = CustomColors.HoneyBronze,
                    bgColor = CustomColors.HoneyBronze.copy(0.1f),
                    modifier = Modifier.constrainAs(startBox) {
                        start.linkTo(parent.start)
                        end.linkTo(icon.start, margin = 8.dp)
                        width = Dimension.fillToConstraints
                    }
                )

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(1.dp, Color(0xFFF3F4F6), CircleShape)
                        .constrainAs(icon) {
                            centerTo(parent)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.EventBusy,
                        contentDescription = null,
                        tint = CustomColors.RichCerulean,
                        modifier = Modifier.size(16.dp)
                    )
                }

                DateBox(
                    label = "End",
                    date = formatDate(holiday.endDate),
                    color = CustomColors.HoneyBronze,
                    bgColor = CustomColors.HoneyBronze.copy(0.1f),
                    modifier = Modifier.constrainAs(endBox) {
                        start.linkTo(icon.end, margin = 8.dp)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                )
            }
        }
    }
}


@Composable
fun DateBox(label: String, date: String, color: Color, bgColor: Color, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bgColor)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansregular)),
            color = Color.Gray.copy(alpha = 0.6f)
        )
        Text(
            text = date,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
            color = color
        )
    }
}

