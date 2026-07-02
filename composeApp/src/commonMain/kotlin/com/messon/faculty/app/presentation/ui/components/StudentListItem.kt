package com.messon.faculty.app.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil3.compose.AsyncImage
import com.messon.faculty.app.theme.CustomColors
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.constraintlayout.compose.ChainStyle
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansmedium
import messon_faculty.composeapp.generated.resources.publicsansregular
import messon_faculty.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font


@Composable
fun StudentCard(
    name: String,
    studentId: String,
    isActive: Boolean,
    imageUrl: String? = null,
    isSelected: Boolean,
    selectionMode: Boolean,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onMoreClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(20.dp))
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = { onLongClick() }
            ),
        colors = CardDefaults.cardColors(
            containerColor = if(!isSelected) Color.White else CustomColors.LightBlue
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 4.dp else 2.dp
        )
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val (profileImage, nameText, idText, statusBadge, optionsIcon) = createRefs()

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        if (isSelected)
                            CustomColors.RichCerulean
                        else
                            CustomColors.LightBlue
                    )
                    .constrainAs(profileImage) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                } else {
                    if (imageUrl.isNullOrEmpty()) {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = null,
                            tint = CustomColors.RichCerulean,
                            modifier = Modifier.size(28.dp)
                        )
                    } else {
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            val statusBgColor = if (isActive) CustomColors.Cream else Color.LightGray.copy(alpha = 0.2f)
            val statusTextColor = if (isActive) CustomColors.HoneyBronze else Color.LightGray
            val statusText = if (isActive) "Active" else "Inactive"

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(statusBgColor)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .constrainAs(statusBadge) {
                        end.linkTo(
                            if (selectionMode) parent.end else optionsIcon.start,
                            margin = if (selectionMode) 0.dp else 12.dp
                        )
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = statusText,
                    fontFamily = FontFamily(Font(Res.font.publicsansregular)),
                    fontSize = 10.sp,
                    letterSpacing = 0.5.sp,
                    color = statusTextColor
                )
            }

            if (!selectionMode) {
                IconButton(
                    onClick = onMoreClick,
                    modifier = Modifier
                        .size(24.dp)
                        .constrainAs(optionsIcon) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Options",
                        tint = CustomColors.LightGrayBlue
                    )
                }
            }

            createVerticalChain(nameText, idText, chainStyle = ChainStyle.Packed)

            Text(
                text = name,
                fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                fontSize = 12.sp,
                color = CustomColors.BlackberryCream,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.constrainAs(nameText) {
                    start.linkTo(profileImage.end, margin = 16.dp)
                    end.linkTo(statusBadge.start, margin = 12.dp)
                    width = Dimension.fillToConstraints
                }
            )

            Text(
                text = "ID: $studentId",
                fontSize = 10.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                color = CustomColors.LightGrayBlue,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.constrainAs(idText) {
                    start.linkTo(profileImage.end, margin = 16.dp)
                    end.linkTo(statusBadge.start, margin = 12.dp)
                    top.linkTo(nameText.bottom, margin = 4.dp)
                    width = Dimension.fillToConstraints
                }
            )
        }
    }
}