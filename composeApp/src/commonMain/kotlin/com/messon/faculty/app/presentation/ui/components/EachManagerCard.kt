package com.messon.faculty.app.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.faculty.app.domain.dataModel.ManagerCreateRes
import com.messon.faculty.app.utils.DialogVariant
import com.messon.faculty.composeapp.generated.resources.Res
import com.messon.faculty.composeapp.generated.resources.publicsansmedium
import com.messon.faculty.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun ManagerCard(
    manager: ManagerCreateRes,
    onCardClick: () -> Unit,
    onDeleteConfirm: (ManagerCreateRes) -> Unit
) {

    var showDeleteIcon by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clip(RoundedCornerShape(24.dp))
            .combinedClickable(
                onClick = {
                    if (!showDeleteIcon) {
                        onCardClick()
                    } else {
                        showDeleteIcon = false
                    }
                },
                onLongClick = {
                    showDeleteIcon = true
                }
            ),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 1.dp
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            val (avatar, info, action) = createRefs()

            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(16.dp))
                    .constrainAs(avatar) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = manager.name.take(1),
                    fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .constrainAs(info) {
                        start.linkTo(avatar.end, margin = 10.dp)
                        top.linkTo(avatar.top)
                        bottom.linkTo(avatar.bottom)
                        end.linkTo(action.start, margin = 8.dp)
                        width = Dimension.fillToConstraints
                    }
                    .padding(horizontal = 7.dp)
            ) {

                Text(
                    text = manager.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = manager.mailId,
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))
                StatusBadge(
                    isActive = manager.isActive,
                )
            }

            Icon(
                imageVector = if (showDeleteIcon) Icons.Default.Delete else Icons.Default.ChevronRight,
                contentDescription = "action",
                modifier = Modifier
                    .size(24.dp)
                    .constrainAs(action) {
                        end.linkTo(parent.end, margin = 10.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .clip(CircleShape)
                    .combinedClickable(
                        onClick = {
                            if (showDeleteIcon) {
                                showDeleteDialog = true
                            } else {
                                onCardClick()
                            }
                        }
                    ),
                tint = if (showDeleteIcon) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )
        }
    }

    ConfirmDeleteDialog(
        isOpen = showDeleteDialog,
        onDismiss = { showDeleteDialog = false },
        onConfirm = {
            showDeleteDialog = false
            onDeleteConfirm(manager)
        },
        variant = DialogVariant.DANGER
    )
}