package com.messon.faculty.app.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.messon.faculty.app.utils.toFormattedDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillingBottomSheet(
    bottomSheetState: SheetState,
    predefinedOptions: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit,
    fromDate: String?,
    toDate: String?,
    onFromDateChange: (String) -> Unit,
    onToDateChange: (String) -> Unit,
    isSubmitEnabled: Boolean,
    onDismiss: () -> Unit,
    onSubmit:()-> Unit
) {
    var showFromPicker by remember { mutableStateOf(false) }
    var showToPicker by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = bottomSheetState,
        shape = RoundedCornerShape(topStart = 45.dp, topEnd = 45.dp),
        containerColor = MaterialTheme.colorScheme.surface,
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal =16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Select Predefined Period",
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(Modifier.height(12.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                predefinedOptions.forEach { option ->
                    FilterChip(
                        modifier = Modifier.height(35.dp),
                        leadingIcon = {
                            if(selectedOption==option){
                                Icon(
                                    imageVector =  Icons.Default.Check ,
                                    contentDescription = "selected",
                                    modifier = Modifier.size(20.dp),
                                )
                            }
                        },
                        shape = RoundedCornerShape(25.dp),
                        selected = selectedOption == option,
                        onClick = { onOptionSelected(option) },
                        label = { Text(option) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            labelColor = MaterialTheme.colorScheme.onSurface,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                            selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        )
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Select Custom Date",
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DateField(
                    title = "From",
                    value = fromDate,
                    onClick = { showFromPicker = true },
                    modifier = Modifier.weight(1f)
                )

                DateField(
                    title = "To",
                    value = toDate,
                    onClick = { showToPicker = true },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = onSubmit,
                enabled = isSubmitEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Proceed")
            }
        }
    }

    if (showFromPicker) {
        DatePickerModal(
            onDateSelected = {
                onFromDateChange(it)
                showFromPicker = false
            },
            onDismiss = { showFromPicker = false }
        )
    }

    if (showToPicker) {
        DatePickerModal(
            onDateSelected = {
                onToDateChange(it)
                showToPicker = false
            },
            onDismiss = { showToPicker = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val state = rememberDatePickerState(
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier
                .width(340.dp)
                .background(Color.Transparent, RoundedCornerShape(28.dp))
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer(
                        scaleX = 0.9f,
                        scaleY=0.9f
                    )
            ) {
                DatePicker(
                    state = state,
                    showModeToggle = false,
                    title = null
                )
            }

            Row(
                modifier = Modifier.background(Color.Unspecified).fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = onDismiss) { Text("Cancel") }
                Button(onClick = {
                    state.selectedDateMillis?.let { onDateSelected(it.toFormattedDate()) }
                    onDismiss()
                }) { Text("OK") }
            }
        }
    }
}

@Composable
fun DateField(
    title: String,
    value: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {

        Text(
            text = title,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(Modifier.height(4.dp))

        Box {
            OutlinedTextField(
                value = value ?: "",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Select") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                shape = RoundedCornerShape(12.dp)
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable { onClick() }
            )
        }
    }
}