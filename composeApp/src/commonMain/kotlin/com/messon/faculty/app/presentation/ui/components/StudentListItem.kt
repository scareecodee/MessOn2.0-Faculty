package com.messon.faculty.app.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil3.compose.AsyncImage
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansregular
import messon_faculty.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun StudentListItem(
    modifier: Modifier = Modifier,
    profileImageUrl: String?,
    name: String,
    studentId: String,
    isActive: Boolean,
    onMoreClick: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {

        val (image, nameRef, studentIdRef, statusDot1, statusDot2, statusText, moreIcon) = createRefs()

        AsyncImage(
            model = profileImageUrl,
            contentDescription = "Profile",
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(
                    if (isActive) Color(0xFF2ECC71) else Color.Red
                )
                .border(2.dp,Color.White, shape = CircleShape)
                .padding(2.dp)
                .constrainAs(statusDot1) {
                    start.linkTo(image.end, margin = (-9).dp)
                    bottom.linkTo(image.bottom, margin =4.dp)
                }
        )

        Text(
            text = name,
            fontFamily = FontFamily(Font((Res.font.publicsanssemibold))),
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.constrainAs(nameRef) {
                start.linkTo(image.end, margin = 12.dp)
                top.linkTo(image.top)
                end.linkTo(moreIcon.start, margin = 8.dp)
                width = Dimension.fillToConstraints
            }
        )


        Text(
            text = "$studentId ",
            fontFamily = FontFamily(Font((Res.font.publicsansregular))),
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.constrainAs(studentIdRef) {
                start.linkTo(nameRef.start)
                top.linkTo(nameRef.bottom, margin = 1.dp)

            }
        )

        Box(
            modifier = Modifier
                .size(4.dp)
                .clip(CircleShape)
                .background(
                    if (isActive) Color(0xFF2ECC71) else Color.Red
                )
                .constrainAs(statusDot2) {
                    start.linkTo(studentIdRef.end, margin = 10.dp)
                    top.linkTo(studentIdRef.top)
                    bottom.linkTo(studentIdRef.bottom)
                }
        )

        Text(
            text = if (isActive) "Active" else "Inactive",
            color = if (isActive) Color(0xFF2ECC71) else Color.Red,
            fontFamily = FontFamily(Font((Res.font.publicsansregular))),
            fontSize = 13.sp,
            modifier = Modifier.constrainAs(statusText) {
                start.linkTo(statusDot2.end, margin = 6.dp)
                top.linkTo(studentIdRef.top)
                bottom.linkTo(studentIdRef.bottom)
            }
        )


        IconButton(
            onClick = { onMoreClick?.invoke() },
            modifier = Modifier.constrainAs(moreIcon) {
                end.linkTo(parent.end)
                top.linkTo(nameRef.top)
            }
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More"
            )
        }
    }
}
