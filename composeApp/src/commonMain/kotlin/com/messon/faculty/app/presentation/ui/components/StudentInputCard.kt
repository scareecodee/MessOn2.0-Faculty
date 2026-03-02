package com.messon.faculty.app.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.faculty.app.domain.dataModel.StudentInput
import com.messon.faculty.app.theme.CustomColors
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansbold
import messon_faculty.composeapp.generated.resources.publicsansmedium
import messon_faculty.composeapp.generated.resources.publicsansregular
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
        border = BorderStroke(1.dp, Color(0xFFF0F0F0)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
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
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansbold))
            )

            InputField(placeholder = "Full Name", value = name, onValueChange = {name=it})
            InputField(placeholder = "Roll no.", value = roll, onValueChange = { roll = it })
            InputField(placeholder = "Email ID", value = email, onValueChange =  { email = it })
            InputField(placeholder = "Branch", value =branch , onValueChange = { branch = it })
            InputField(placeholder = "Year", value = batch, onValueChange =  { batch = it}, isYear = true)


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
                        color = CustomColors.RichCerulean,
                        fontFamily = FontFamily(Font(Res.font.publicsansmedium))
                    )
                }
                Button(
                    enabled = name.isNotBlank() &&
                            roll.isNotBlank() &&
                            email.isNotBlank() &&
                            branch.isNotBlank() &&
                            batch.isNotBlank() && batch.toInt()>0 && batch.toInt()<5,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomColors.RichCerulean
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

@Composable
fun InputField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    isYear: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            if (isYear) {

                if (it.all { char -> char.isDigit() }) {
                    onValueChange(it)
                }
            } else {
                onValueChange(it)
            }
        },

        placeholder = {
            Text(
                text = placeholder,
                color = Color.LightGray,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = FontFamily(Font(Res.font.publicsansregular))
            )
        },

        keyboardOptions = KeyboardOptions(
            keyboardType = if (isYear) KeyboardType.Number else KeyboardType.Text
        ),
        textStyle = TextStyle(
            fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
            fontSize = 12.sp
        ),

        colors = OutlinedTextFieldDefaults.colors(
            focusedLabelColor = Color.LightGray,
            unfocusedLabelColor = Color.LightGray,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            cursorColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedTextColor = Color.Black
        ),

        shape = RoundedCornerShape(20.dp),
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

