package com.messon.faculty.app.presentation.ui.screens.college

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.faculty.app.presentation.ui.components.CustomInputField
import com.messon.faculty.app.presentation.ui.components.StatusSwitcher
import com.messon.faculty.composeapp.generated.resources.Res
import com.messon.faculty.composeapp.generated.resources.publicsansbold
import com.messon.faculty.composeapp.generated.resources.publicsansmedium
import org.jetbrains.compose.resources.Font

@Composable
fun AddCollegeAdminScreen(
    onBackBtnClick: () -> Unit
) {
    var collegeAdminName by remember { mutableStateOf("") }
    var mail by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Decorative circle
        Box(
            modifier = Modifier
                .offset(x = 200.dp, y = (-100).dp)
                .size(250.dp)
                .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f), CircleShape)
        )

        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (backBtnRef, headingRef, form, statusBarBox, subheading) = createRefs()

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .constrainAs(statusBarBox) {
                        top.linkTo(parent.top)
                    }
            )

            IconButton(
                onClick = { onBackBtnClick() },
                modifier = Modifier.constrainAs(backBtnRef) {
                    start.linkTo(parent.start, 15.dp)
                    top.linkTo(statusBarBox.bottom, 10.dp)
                }
            ) {
                Icon(
                    Icons.Default.ChevronLeft,
                    contentDescription = "back",
                    modifier = Modifier.size(28.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            Text(
                text = "Register Admin",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.constrainAs(headingRef) {
                    start.linkTo(backBtnRef.end, 5.dp)
                    centerVerticallyTo(backBtnRef)
                }
            )

            Text(
                text = "Fill out details to register",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                modifier = Modifier.constrainAs(subheading) {
                    top.linkTo(backBtnRef.bottom, margin = 8.dp)
                    start.linkTo(backBtnRef.start, 4.dp)
                }
            )

            LazyColumn(
                modifier = Modifier
                    .constrainAs(form) {
                        top.linkTo(subheading.bottom, margin = 32.dp)
                        bottom.linkTo(parent.bottom, margin = 7.dp)
                        start.linkTo(parent.start, margin = 12.dp)
                        end.linkTo(parent.end, margin = 12.dp)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                    .imePadding(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item {
                    CustomInputField(
                        value = collegeAdminName,
                        onValueChange = { collegeAdminName = it },
                        label = "Manager Name",
                        icon = Icons.Default.Home
                    )
                }

                item {
                    CustomInputField(
                        value = mail,
                        onValueChange = { mail = it },
                        label = "Email Id",
                        icon = Icons.Default.Mail
                    )
                }

                item {
                    StatusSwitcher(
                        isActive = isActive,
                        onToggle = { isActive = !isActive },
                        enabled = true,
                        isLoading = false
                    )
                }

                item {
                    Spacer(modifier = Modifier.size(10.dp))
                }

                item {
                    Button(
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .shadow(12.dp, RoundedCornerShape(24.dp)),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                "Add Admin",
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(Color.White.copy(alpha = 0.2f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}