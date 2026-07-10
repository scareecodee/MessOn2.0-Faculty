package com.messon.faculty.app.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.AltRoute
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.faculty.app.domain.dataModel.StudentInput
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansbold
import messon_faculty.composeapp.generated.resources.publicsansmedium
import org.jetbrains.compose.resources.Font

@Composable
fun StudentInputCard(
    onAdd: (StudentInput) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var roll by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var branch by remember { mutableStateOf("") }
    var batch by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }



    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
        border = BorderStroke(1.dp,Color.Transparent),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(0.1f)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                maxLines = 1,
                text = "Add Student",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = FontFamily(Font(Res.font.publicsansbold))
            )

            CustomInputField(label = "Full Name", value = name, onValueChange = {name=it}, icon = Icons.Default.Edit)
            CustomInputField(label = "Roll no.", value = roll, onValueChange = { roll = it }, icon =Icons.Default.Info )
            CustomInputField(label = "Email ID", value = email, onValueChange =  { email = it },icon=Icons.Default.Mail)
            CustomInputField(label = "Branch", value =branch , onValueChange = { branch = it },icon= Icons.AutoMirrored.Filled.AltRoute)
            CustomInputField(
                label = "Year", value = batch, onValueChange = { batch = it },
                icon = Icons.Default.CalendarMonth,
                keyboardType = KeyboardType.Number,
            )
            StatusSwitcher(
                enabled =true,
                isActive = isActive,
                isLoading =false,
                onToggle = {
                    isActive=!isActive
                }
            )

            Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f)
                        .height(45.dp)
                    ,

                )
                {
                    Text(
                        text = "Cancel",
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.primary,
                        fontFamily = FontFamily(Font(Res.font.publicsansmedium))
                    )
                }
                Button(
                    enabled = name.isNotBlank() &&
                            roll.isNotBlank() &&
                            email.isNotBlank() &&
                            branch.isNotBlank() &&
                            batch.isNotBlank() ,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    onClick = { onAdd(StudentInput(name = name, studentId = roll, batch =batch.toInt(), branch = branch, isActive = isActive,email=email )) },
                    modifier = Modifier.weight(1f)
                        .height(45.dp)
                ) {
                    Text(
                        text = "Add Student",
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White,
                        fontFamily = FontFamily(Font(Res.font.publicsansmedium))
                    )
                }
            }
        }
    }
}


