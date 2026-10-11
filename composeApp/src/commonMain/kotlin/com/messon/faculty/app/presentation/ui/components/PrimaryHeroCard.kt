package com.messon.faculty.app.presentation.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.faculty.composeapp.generated.resources.Res
import com.messon.faculty.composeapp.generated.resources.publicsansmedium
import com.messon.faculty.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font


@Composable
fun PrimaryHeroCard(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit,
    cardColor: Color,
    primaryColor: Color
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable { onClick() },
        color = cardColor,
        shape = RoundedCornerShape(24.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, primaryColor.copy(alpha = 0.2f))
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
            val (backgroundArt, iconContainer, titleRow, subtitleText, chevronContainer) = createRefs()

            Canvas(
                modifier = Modifier.constrainAs(backgroundArt) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
            ) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(primaryColor.copy(alpha = 0.08f), Color.Transparent),
                        center = Offset(size.width, 0f),
                        radius = size.width * 0.4f
                    ),
                    center = Offset(size.width, 0f),
                    radius = size.width * 0.4f
                )

                val dotSpacing = 35f
                val dotRadius = 3f
                val startX = size.width - (dotSpacing * 6)
                val startY = -dotSpacing

                for (x in 0..8) {
                    for (y in 0..6) {
                        drawCircle(
                            color = primaryColor.copy(alpha = 0.2f),
                            radius = dotRadius,
                            center = Offset(startX + (x * dotSpacing), startY + (y * dotSpacing))
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
                    .border(1.dp, primaryColor.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                    .constrainAs(iconContainer) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = primaryColor, modifier = Modifier.size(28.dp))
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(MaterialTheme.colorScheme.surface, CircleShape)
                    .border(1.dp, primaryColor.copy(alpha = 0.1f), CircleShape)
                    .constrainAs(chevronContainer) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Rounded.ChevronRight, contentDescription = null, tint = primaryColor, modifier = Modifier.size(24.dp))
            }

            createVerticalChain(titleRow, subtitleText, chainStyle = ChainStyle.Packed)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.constrainAs(titleRow) {
                    start.linkTo(iconContainer.end, margin = 16.dp)
                    end.linkTo(chevronContainer.start, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
            ) {
                Text(text = title,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = FontFamily(Font((Res.font.publicsanssemibold))),
                    fontSize = 14.sp,
                )
            }

            Text(
                text = description,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                fontFamily = FontFamily(Font((Res.font.publicsansmedium))),
                fontSize = 12.sp,
                modifier = Modifier.constrainAs(subtitleText) {
                    start.linkTo(iconContainer.end, margin = 16.dp)
                    end.linkTo(chevronContainer.start, margin = 16.dp)
                    top.linkTo(titleRow.bottom, margin = 4.dp)
                    width = Dimension.fillToConstraints
                }
            )
        }
    }
}