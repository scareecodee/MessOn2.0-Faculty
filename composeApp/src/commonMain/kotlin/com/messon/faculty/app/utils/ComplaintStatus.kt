package com.messon.faculty.app.utils


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.messon.faculty.app.domain.dataModel.ComplaintStatus
import com.messon.faculty.app.theme.MessonTheme

enum class ComplaintFilter(
    val label: String
) {
    ALL("All"),
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    RESOLVED("Resolved")
}

@Composable
fun ComplaintFilter.backgroundColor(): Color {
    return when (this) {
        ComplaintFilter.ALL ->
            MaterialTheme.colorScheme.surface

        ComplaintFilter.PENDING ->
            MessonTheme.colors.warningContainer

        ComplaintFilter.IN_PROGRESS ->
            MaterialTheme.colorScheme.primary.copy(0.1f)

        ComplaintFilter.RESOLVED ->
            MessonTheme.colors.accentContainer
    }
}

@Composable
fun ComplaintFilter.textColor(): Color {
    return when (this) {
        ComplaintFilter.ALL ->
            MaterialTheme.colorScheme.onSurface

        ComplaintFilter.PENDING ->
            MaterialTheme.colorScheme.secondary

        ComplaintFilter.IN_PROGRESS ->
            MaterialTheme.colorScheme.primary

        ComplaintFilter.RESOLVED ->
            MaterialTheme.colorScheme.onBackground
    }
}


fun ComplaintStatus.toFilter(): ComplaintFilter {
    return when (this) {
        ComplaintStatus.PENDING -> ComplaintFilter.PENDING
        ComplaintStatus.IN_PROGRESS -> ComplaintFilter.IN_PROGRESS
        ComplaintStatus.RESOLVED -> ComplaintFilter.RESOLVED
    }
}