package com.messon.faculty.app.presentation.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansmedium
import messon_faculty.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun GridCard(
    onClick:()-> Unit,
    title: String,
    description: String,
    icon: ImageVector,
    cardColor: Color,
    primaryColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(210.dp)
            .clip(RoundedCornerShape(24.dp))
            .clickable {
                onClick()
            },
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 2.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            val (backgroundArt, iconBox, titleText, descText) = createRefs()

            Canvas(
                modifier = Modifier.constrainAs(backgroundArt) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
            ) {
                drawCircle(
                    color = cardColor,
                    radius = size.width * 0.6f,
                    center = Offset(size.width + 20f, -20f)
                )
            }

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(cardColor, RoundedCornerShape(16.dp))
                    .constrainAs(iconBox) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = primaryColor, modifier = Modifier.size(24.dp))
            }

            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = FontFamily(Font((Res.font.publicsanssemibold))),
                fontSize = 14.sp,
                modifier = Modifier.constrainAs(titleText) {
                    top.linkTo(iconBox.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            )

            Text(
                text = description,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontFamily = FontFamily(Font((Res.font.publicsansmedium))),
                fontSize = 12.sp,
                modifier = Modifier.constrainAs(descText) {
                    top.linkTo(titleText.bottom, margin = 4.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            )
        }
    }
}