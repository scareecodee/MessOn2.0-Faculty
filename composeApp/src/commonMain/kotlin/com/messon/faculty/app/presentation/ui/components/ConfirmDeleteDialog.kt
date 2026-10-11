package com.messon.faculty.app.presentation.ui.components
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.faculty.app.utils.DialogVariant
import com.messon.faculty.composeapp.generated.resources.Res
import com.messon.faculty.composeapp.generated.resources.publicsansregular
import com.messon.faculty.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font


@Composable
fun ConfirmDeleteDialog(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: String = "Confirm Deletion",
    message: String = "Are you sure you want to delete this item? This action cannot be undone.",
    confirmText: String = "Delete",
    cancelText: String = "Cancel",
    variant: DialogVariant = DialogVariant.DANGER,
    isLoading: Boolean = false
) {
    if (!isOpen) return


    val iconColor = when (variant) {
        DialogVariant.DANGER -> MaterialTheme.colorScheme.error
        DialogVariant.WARNING -> MaterialTheme.colorScheme.secondary
        DialogVariant.INFO -> MaterialTheme.colorScheme.primary
    }

    AlertDialog(
        shape = RoundedCornerShape(30.dp),
        onDismissRequest = { if (!isLoading) onDismiss() },
        icon = {
            Icon(
                imageVector = when (variant) {
                    DialogVariant.DANGER -> Icons.Default.Delete
                    DialogVariant.WARNING -> Icons.Default.Warning
                    DialogVariant.INFO -> Icons.Default.Info
                },
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(30.dp)
            )
        },
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                fontSize = 14.sp,
            )
        },
        text = {
            Text(
                text = message,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontFamily = FontFamily(Font(Res.font.publicsansregular)),
                fontSize = 12.sp,
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                enabled = !isLoading,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = iconColor
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = iconColor
                    )
                } else {
                    Text(
                        confirmText,
                        fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                        fontSize = 12.sp,
                    )
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                enabled = !isLoading,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.outline
                )
            ) {
                Text(
                    cancelText,
                    fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                    fontSize = 12.sp,
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
    )
}
