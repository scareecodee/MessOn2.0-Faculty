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
import androidx.compose.material3.*
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
import com.messon.faculty.app.theme.MessonColors
import com.messon.faculty.app.theme.MessonPalette

@Composable
fun BroadcastNotificationScreen(
    onViewHistoryClick: () -> Unit,
    messonColors: MessonColors
) {
    var subject by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val materialColors = MaterialTheme.colorScheme
    val isDarkTheme = materialColors.background == MessonPalette.DarkOffWhite

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(materialColors.background)
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
                            color = materialColors.onBackground, // Material text color
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = (-1).sp
                        )
                        Text(
                            text = "Notify your student community",
                            color = materialColors.onSurfaceVariant,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    IconButton(
                        onClick = onViewHistoryClick,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(messonColors.infoContainer)
                            .padding(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "History",
                            tint = materialColors.primary
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = if (isDarkTheme) 8.dp else 20.dp,
                            shape = RoundedCornerShape(32.dp),
                            ambientColor = if (isDarkTheme) Color.Black.copy(alpha = 0.3f) else Color.Black.copy(alpha = 0.1f)
                        )
                        .background(
                            color = materialColors.surface,
                            shape = RoundedCornerShape(32.dp)
                        )
                        .padding(24.dp)
                ) {
                    ModernInputField(
                        value = subject,
                        onValueChange = { subject = it },
                        label = "SUBJECT LINE",
                        placeholder = "e.g., Exam Schedule Update",
                        icon = Icons.Default.Subject,
                        singleLine = false,
                        materialColors = materialColors,
                        messonColors = messonColors
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    ModernInputField(
                        value = description,
                        onValueChange = { description = it },
                        label = "MESSAGE DETAILS",
                        placeholder = "Enter the full message...",
                        icon = Icons.Default.Description,
                        singleLine = false,
                        minLines = 6,
                        materialColors = materialColors,
                        messonColors = messonColors
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = { /* Handle Send Logic */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = materialColors.primary,
                            contentColor = materialColors.onPrimary,
                            disabledContainerColor = materialColors.primary.copy(alpha = 0.3f),
                            disabledContentColor = materialColors.onPrimary.copy(alpha = 0.5f)
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = if (isDarkTheme) 2.dp else 4.dp
                        ),
                        enabled = subject.isNotBlank() && description.isNotBlank()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "SEND NOTIFICATION",
                                color = materialColors.onPrimary,
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp,
                                letterSpacing = 1.sp
                            )
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = null,
                                tint = materialColors.onPrimary,
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
    minLines: Int = 1,
    materialColors: ColorScheme,
    messonColors: MessonColors
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = materialColors.onSurfaceVariant,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                color = materialColors.onSurfaceVariant,
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
                    color = materialColors.onSurfaceVariant.copy(alpha = 0.5f),
                    fontSize = 16.sp
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = materialColors.surfaceVariant,
                unfocusedContainerColor = materialColors.surfaceVariant,
                disabledContainerColor = materialColors.surfaceVariant,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = materialColors.onBackground,
                unfocusedTextColor = materialColors.onBackground,
                disabledTextColor = materialColors.onBackground.copy(alpha = 0.5f)
            ),
            singleLine = singleLine,
            maxLines = if (singleLine) 1 else 10,
            keyboardOptions = KeyboardOptions(imeAction = if (singleLine) ImeAction.Next else ImeAction.Done)
        )
    }
}