package com.messon.faculty.app.domain.dataModel

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class ManageItem(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val gradient: List<Color>
)
