package com.messon.faculty.app.presentation.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.faculty.app.theme.CustomColors
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansmedium
import messon_faculty.composeapp.generated.resources.publicsansregular
import messon_faculty.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun BrandingCard() {

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(32.dp))
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            CustomColors.RichCerulean,
                            RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Restaurant,
                        null,
                        tint = Color.White
                    )
                }

                Spacer(Modifier.width(10.dp))

                Column {
                    Text(
                        "MessOn",
                        fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                        fontSize = 16.sp,
                    )

                    Text(
                        "Smart System",
                        fontFamily = FontFamily(Font(Res.font.publicsansregular)),
                        fontSize = 12.sp,
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {

                Text(
                    "Made with",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                    color = CustomColors.LightGrayBlue
                )

                Icon(
                    Icons.Default.Favorite,
                    null,
                    tint = CustomColors.HoneyBronze,
                    modifier = Modifier
                        .size(14.dp)
                        .padding(horizontal = 4.dp)
                )

                Text(
                    "for Bharat",
                    fontSize = 10.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                    color = CustomColors.LightGrayBlue
                )
            }
        }

        Icon(
            Icons.Default.TrendingUp,
            null,
            tint = CustomColors.Teal,
            modifier = Modifier.size(36.dp)
        )
    }
}