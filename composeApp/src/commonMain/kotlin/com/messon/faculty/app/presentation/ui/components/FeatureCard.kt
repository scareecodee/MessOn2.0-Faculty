package com.messon.faculty.app.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.faculty.app.theme.CustomColors
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansmedium
import messon_faculty.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun FeatureCard(
    modifier: Modifier = Modifier,
    title: String,
    desc: String,
    icon: ImageVector,
    bg: Color,
    iconColor: Color,
    onCardClick:()->Unit
) {

    Column(
        modifier = modifier
            .size(190.dp)
            .background(bg, RoundedCornerShape(28.dp))
            .clip(RoundedCornerShape(28.dp))
            .clickable{
                onCardClick()
            }
            .padding(20.dp)
    ) {

        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color.White, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = iconColor)
        }

        Spacer(Modifier.height(12.dp))

        Text(
            title,
            fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
            fontSize = 14.sp,
            color = CustomColors.BlackberryCream
        )

        Spacer(Modifier.height(4.dp))

        Text(
            desc,
            fontSize = 10.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
            color = CustomColors.BlackberryCream.copy(.6f)
        )
    }
}


@Composable
fun FeatureGrid(
    onRatingCardClick:()-> Unit,
    onBillCardClick:()-> Unit,
    onNoticeCardClick:()-> Unit,
    onMessCardClick:()-> Unit,

    ) {

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {

            FeatureCard(
                modifier = Modifier.weight(1f),
                title = "Meal Rating",
                desc = "Share your daily food feedback",
                icon = Icons.Default.Star,
                bg = CustomColors.HoneyBronze.copy(0.1f),
                iconColor = CustomColors.HoneyBronze,
                onCardClick = {
                    onRatingCardClick()
                }
            )

            FeatureCard(
                modifier = Modifier.weight(1f),
                title = "Mess Bill",
                desc = "Check dues & payment history",
                icon = Icons.Default.Receipt,
                bg = CustomColors.LightBlue,
                iconColor = CustomColors.RichCerulean,
                onCardClick = {
                    onBillCardClick()
                }
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {

            FeatureCard(
                modifier = Modifier.weight(1f),
                title = "Notice Board",
                desc = "Important updates",
                icon = Icons.Default.Notifications,
                bg = CustomColors.BlackberryCream.copy(0.1f),
                iconColor = CustomColors.BlackberryCream,
                onCardClick = {
                    onNoticeCardClick()
                }
            )

            FeatureCard(
                modifier = Modifier.weight(1f),
                title = "Manage Mess",
                desc = "Leaves & menu settings",
                icon = Icons.Default.Settings,
                bg = CustomColors.Mint,
                iconColor = CustomColors.Teal,
                onCardClick = {
                    onMessCardClick()
                }
            )
        }
    }
}