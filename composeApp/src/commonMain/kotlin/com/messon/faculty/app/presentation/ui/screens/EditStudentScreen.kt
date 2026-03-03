package com.messon.faculty.app.presentation.ui.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.faculty.app.presentation.ui.components.CustomInputField
import com.messon.faculty.app.theme.CustomColors

@Composable
fun EditStudentScreen(
    onBackClick: () -> Unit = {},
    onSaveClick: ()-> Unit
) {
    // State holders for the form
    var name by remember { mutableStateOf("Jane Doe") }
    var rollNumber by remember { mutableStateOf("CS2023042") }
    var email by remember { mutableStateOf("jane.doe@university.edu") }
    var branch by remember { mutableStateOf("CSE") }
    var batch by remember { mutableStateOf("2024") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomColors.OffWhite)
    ) {
        // --- Custom Top Bar ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = CustomColors.BlackberryCream
                )
            }
            Text(
                text = "Edit Student",
                style = MaterialTheme.typography.titleLarge,
                color = CustomColors.BlackberryCream,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = { /* More options */ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Options",
                    tint = CustomColors.BlackberryCream
                )
            }
        }

        // --- Main Content using ConstraintLayout ---
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            val (contentRef, buttonRef) = createRefs()

            // Scrollable Form Area
            Column(
                modifier = Modifier
                    .constrainAs(contentRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(buttonRef.top)
                        height = Dimension.fillToConstraints
                    }
                    .verticalScroll(scrollState)
                    .padding(horizontal = 20.dp)
            ) {
                // Avatar Section
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(CustomColors.LightBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(50.dp),
                            tint = CustomColors.RichCerulean
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Surface(
                        color = CustomColors.Cream,
                        shape = RoundedCornerShape(16.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CustomColors.HoneyBronze.copy(alpha = 0.2f))
                    ) {
                        Text(
                            text = "ENROLLED",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = CustomColors.HoneyBronze,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Input Fields
                CustomInputField(
                    label = "Full Name",
                    value = name,
                    onValueChange = { name = it },
                    icon = Icons.Default.Edit
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomInputField(
                    label = "Roll Number",
                    value = rollNumber,
                    onValueChange = { rollNumber = it },
                    icon = Icons.Default.Info
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomInputField(
                    label = "Email Address",
                    value = email,
                    onValueChange = { email = it },
                    icon = Icons.Default.Email,
                    keyboardType = KeyboardType.Email
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Branch and Batch in a Row
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.weight(1.5f)) {
                        CustomInputField(
                            label = "Branch",
                            value = branch,
                            onValueChange = { branch = it },
                            icon = Icons.Default.Place
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Box(modifier = Modifier.weight(1f)) {
                        CustomInputField(
                            label = "Batch",
                            value = batch,
                            onValueChange = { batch = it },
                            icon = Icons.Default.DateRange,
                            keyboardType = KeyboardType.Number
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))
            }

            // Save Button (Fixed at bottom)
            Button(
                onClick = {
                    onSaveClick()
                },
                modifier = Modifier
                    .constrainAs(buttonRef) {
                        bottom.linkTo(parent.bottom, margin = 24.dp)
                        start.linkTo(parent.start, margin = 20.dp)
                        end.linkTo(parent.end, margin = 20.dp)
                        width = Dimension.fillToConstraints
                    }
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CustomColors.RichCerulean,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Save Changes", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

