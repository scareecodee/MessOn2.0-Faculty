package com.messon.faculty.app.presentation.ui.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.faculty.app.data.remote.dto.UserInteraction
import com.messon.faculty.app.domain.dataModel.Complaint
import com.messon.faculty.app.domain.dataModel.ComplaintStatus
import com.messon.faculty.app.presentation.viewmodel.FetchComplaintsVM
import com.messon.faculty.app.utils.backgroundColor
import com.messon.faculty.app.utils.textColor
import com.messon.faculty.app.utils.toFilter
import com.messon.faculty.composeapp.generated.resources.Res
import com.messon.faculty.composeapp.generated.resources.publicsansbold
import com.messon.faculty.composeapp.generated.resources.publicsansregular
import com.messon.faculty.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun ComplaintCard(
    complaint: Complaint,
    complainVM: FetchComplaintsVM,
    modifier: Modifier = Modifier,
) {
    var showUpdateStatusButtons by remember { mutableStateOf(false) }
    val availableStatuses = ComplaintStatus.entries.filter { it != complaint.status }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(24.dp))
            .combinedClickable(
                enabled =true,
                onLongClick ={
                    showUpdateStatusButtons=!showUpdateStatusButtons
                },
                onClick = {
                    showUpdateStatusButtons=false
                }
            )
        ,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            val (avatar, name, studId, status, title, desc, divider, date, actions) = createRefs()

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .constrainAs(avatar) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = complaint.studentName.take(1).uppercase(),
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                )
            }

            Text(
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = complaint.studentName,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.constrainAs(name) {
                    top.linkTo(avatar.top)
                    start.linkTo(avatar.end, margin = 12.dp)
                    end.linkTo(status.start, margin = 8.dp)
                    width = Dimension.fillToConstraints
                }
            )

            Text(
                text = "ID: ${complaint.studentId}",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    fontSize = 10.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                ),
                modifier = Modifier.constrainAs(studId) {
                    top.linkTo(name.bottom, margin = 2.dp)
                    start.linkTo(name.start)
                }
            )

            Surface(
                color = complaint.status.toFilter().backgroundColor(),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.constrainAs(status) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
            ) {
                Text(
                    text = complaint.status.name.uppercase(),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = complaint.status.toFilter().textColor(),
                        fontSize = 10.sp,
                        fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                    )
                )
            }

            Text(
                text = complaint.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                ),
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(avatar.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            )

            Text(
                text = complaint.description,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsansregular)),
                ),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.constrainAs(desc) {
                    top.linkTo(title.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            )

            HorizontalDivider(
                modifier = Modifier.constrainAs(divider) {
                    top.linkTo(desc.bottom, margin = 20.dp)
                },
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.constrainAs(date) {
                    top.linkTo(divider.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = complaint.date,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                        fontSize = 9.sp,
                        fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                    )
                )
            }

            Surface(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.constrainAs(actions) {
                    top.linkTo(divider.bottom, margin = 12.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                ) {
                    InteractionButton(
                        count = complaint.likes,
                        icon = if (complaint.userInteraction == UserInteraction.LIKE)
                            Icons.Filled.ThumbUp
                        else
                            Icons.Outlined.ThumbUp,
                        isSelected = complaint.userInteraction == UserInteraction.LIKE,
                        activeColor = MaterialTheme.colorScheme.primary,
                    )

                    Spacer(modifier = Modifier.width(4.dp))
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(16.dp)
                            .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                    InteractionButton(
                        count = complaint.dislikes,
                        icon = if (complaint.userInteraction == UserInteraction.DISLIKE)
                            Icons.Filled.ThumbDown
                        else
                            Icons.Outlined.ThumbDown,
                        isSelected = complaint.userInteraction == UserInteraction.DISLIKE,
                        activeColor = MaterialTheme.colorScheme.secondary,
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = showUpdateStatusButtons,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                availableStatuses.forEach { status ->
                    StatusChip(
                        status = status,
                        onClick = {
                            complainVM.updateComplaintStatus(complaintId = complaint.id,status= status)
                            showUpdateStatusButtons = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun InteractionButton(
    count: Int,
    icon: ImageVector,
    isSelected: Boolean,
    activeColor: Color,
) {
    Button(
        enabled = false,
        onClick = {},
        modifier = Modifier.height(32.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) {
                activeColor.copy(alpha = 0.15f)
            } else {
                Color.Transparent
            },
            contentColor = if (isSelected) {
                activeColor
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            }
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        ),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = if (isSelected) "Selected" else "Not selected",
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = count.toString(),
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
            color = if (isSelected) activeColor else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun StatusChip(
    status: ComplaintStatus,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick),
        color = status.toFilter().backgroundColor(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = status.name.replace("_", " "),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            color = status.toFilter().textColor(),
            fontSize = 10.sp,
            fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
        )
    }
}