package com.messon.faculty.app.presentation.ui.screens.students

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Subject
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.faculty.app.theme.CustomColors.BlackberryCream
import com.messon.faculty.app.theme.CustomColors.LightBlue
import com.messon.faculty.app.theme.CustomColors.LightGrayBlue
import com.messon.faculty.app.theme.CustomColors.OffWhite
import com.messon.faculty.app.theme.CustomColors.RichCerulean

@Composable
fun BroadcastNotificationScreen(
    onViewHistoryClick: () -> Unit
) {
    var subject by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite)
    ) {
        val (lazyColumn) = createRefs()

        LazyColumn(
            modifier = Modifier
                .constrainAs(lazyColumn) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .padding(horizontal = 12.dp),
            contentPadding = PaddingValues(top = 48.dp, bottom = 48.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Broadcast",
                            color = BlackberryCream,
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = (-1).sp
                        )
                        Text(
                            text = "Notify your student community",
                            color = LightGrayBlue,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    IconButton(
                        onClick = onViewHistoryClick,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(LightBlue)
                            .padding(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "History",
                            tint = RichCerulean
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(20.dp, RoundedCornerShape(32.dp), ambientColor = Color.Black.copy(alpha = 0.1f))
                        .background(Color.White, RoundedCornerShape(32.dp))
                        .padding(24.dp)
                ) {
                    ModernInputField(
                        value = subject,
                        onValueChange = { subject = it },
                        label = "SUBJECT LINE",
                        placeholder = "e.g., Exam Schedule Update",
                        icon = Icons.Default.Subject,
                        singleLine = false
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    ModernInputField(
                        value = description,
                        onValueChange = { description = it },
                        label = "MESSAGE DETAILS",
                        placeholder = "Enter the full message...",
                        icon = Icons.Default.Description,
                        singleLine = false,
                        minLines = 6
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = { /* Handle Send Logic */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                        ),
                        elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp),
                        enabled = subject.isNotBlank() && description.isNotBlank()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "SEND NOTIFICATION",
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp,
                                letterSpacing = 1.sp
                            )
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ModernInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    icon: ImageVector,
    singleLine: Boolean = true,
    minLines: Int = 1
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = LightGrayBlue,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                color = LightGrayBlue,
                fontSize = 10.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
            placeholder = {
                Text(
                    text = placeholder,
                    color = LightGrayBlue.copy(alpha = 0.5f),
                    fontSize = 16.sp
                )
            },
            colors = TextFieldDefaults.colors(

            ),
            singleLine = singleLine,
            maxLines = if (singleLine) 1 else 10,
            keyboardOptions = KeyboardOptions(imeAction = if (singleLine) ImeAction.Next else ImeAction.Done)
        )
    }
}