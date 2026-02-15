package com.messon.faculty.app.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansregular
import messon_faculty.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun TableCell(
    text: String,
    isHeader: Boolean
) {
    Text(
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        text = text,
        fontSize = 12.sp,
        modifier = Modifier
            .border(0.5.dp, Color.LightGray)
            .padding(horizontal = 12.dp, vertical = 10.dp)
            .width(120.dp),
        fontFamily = if(isHeader) FontFamily(Font(Res.font.publicsanssemibold)) else FontFamily(Font(Res.font.publicsansregular))
    )
}
