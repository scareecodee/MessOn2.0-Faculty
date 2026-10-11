package com.messon.faculty.app.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.faculty.composeapp.generated.resources.Res
import com.messon.faculty.composeapp.generated.resources.publicsansmedium
import org.jetbrains.compose.resources.Font

@Composable
fun FilterChipsRow(
    chips: List<String>,
    selectedChips: Set<Int>,
    onSelectionChange: (Set<Int>) -> Unit,
    modifier: Modifier = Modifier,
    multiSelection: Boolean = false,
    shape: Shape = RoundedCornerShape(20.dp),
    border: BorderStroke? = null,
    showLeadingCheckIcon: Boolean = true
) {

    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        itemsIndexed(chips) { index, text ->

            val isSelected = selectedChips.contains(index)

            FilterChip(
                modifier = Modifier.height(38.dp),
                selected = isSelected,
                onClick = {
                    val newSelection = if (multiSelection) {
                        if (isSelected)
                            selectedChips - index
                        else
                            selectedChips + index
                    } else {
                        setOf(index)
                    }
                    onSelectionChange(newSelection)
                },
                label = {
                    Text(
                        fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        text = text,
                    )
                },
                leadingIcon = if (showLeadingCheckIcon && isSelected) {
                    {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                } else null,
                shape = shape,
                border = border,
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = Color.White,
                    labelColor = Color.Black,
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = Color.White
                )
            )
        }
    }
}