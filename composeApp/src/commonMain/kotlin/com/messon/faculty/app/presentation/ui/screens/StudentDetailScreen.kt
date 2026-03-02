package com.messon.faculty.app.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.faculty.app.presentation.ui.components.ProfilePicWithBadge
import com.messon.faculty.app.presentation.viewmodel.StudentDetailScreenVM
import com.messon.faculty.app.theme.CustomColors
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansbold
import messon_faculty.composeapp.generated.resources.publicsansmedium
import messon_faculty.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun StudentDetailScreen(
    onStatusChange: (Boolean) -> Unit,
    onViewBillClick: () -> Unit,
    onBackClick: () -> Unit,
    studentDetailScreenVM: StudentDetailScreenVM
) {
    val scrollState = rememberScrollState()
    val student by studentDetailScreenVM.selectedStudent.collectAsState()

    if(student==null) return

    DisposableEffect(Unit) {
        onDispose {
            studentDetailScreenVM.clearSelectedStudent()
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        val (statusBarBox,headerBg, backBtn, title, profileHeader, name, email, infoCards, branchCard, statusSwitcher, billBtn) = createRefs()

        Box(
            Modifier
                .fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                .background(CustomColors.LightGrayBlue)
                .constrainAs(statusBarBox) {
                    top.linkTo(parent.top)
                }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .background(
                    CustomColors.LightBlue,
                    shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)
                )
                .constrainAs(headerBg) {
                    top.linkTo(statusBarBox.bottom)
                }
        )


        IconButton(
            onClick = onBackClick,
            modifier = Modifier.constrainAs(backBtn) {
                top.linkTo(statusBarBox.bottom, 5.dp)
                start.linkTo(parent.start, 16.dp)
            }
        ) {
            Icon(Icons.Default.ChevronLeft, contentDescription = "back", modifier = Modifier.size(28.dp))
        }

        Text(
            text = "Profile",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            color =Color.Black,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(backBtn.top)
                bottom.linkTo(backBtn.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )


        ProfilePicWithBadge(
            imageUrl = student!!.profileImg,
            isActive = student!!.isActive,
            modifier = Modifier.constrainAs(profileHeader) {
                top.linkTo(headerBg.bottom)
                bottom.linkTo(headerBg.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = student!!.name,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
            color = CustomColors.BlackberryCream,
            modifier = Modifier.constrainAs(name) {
                top.linkTo(profileHeader.bottom, 12.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = student!!.email,
            fontSize = 10.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
            color = CustomColors.BlackberryCream.copy(alpha = 0.6f),
            modifier = Modifier.constrainAs(email) {
                top.linkTo(name.bottom, 4.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .constrainAs(infoCards) {
                    top.linkTo(email.bottom, 32.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InfoCard(
                label = "Student Id",
                value = student!!.studentId,
                icon = Icons.Default.Tag,
                modifier = Modifier.weight(1f)
            )
            InfoCard(
                label = "Batch",
                value = "Class of ${student!!.batch}",
                icon = Icons.Default.DateRange,
                modifier = Modifier.weight(1f)
            )
        }


        Box(modifier = Modifier
            .padding(horizontal = 24.dp)
            .constrainAs(branchCard) {
                top.linkTo(infoCards.bottom, 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        ) {
            FullWidthInfoCard(
                label = "Branch",
                value = student!!.branch,
                icon = Icons.Default.Info
            )
        }

        Box(modifier = Modifier
            .padding(horizontal = 24.dp)
            .constrainAs(statusSwitcher) {
                top.linkTo(branchCard.bottom, 24.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        ) {
            StatusSwitcher(
                isActive = student!!.isActive,
                onToggle = onStatusChange
            )
        }

        Button(
            onClick = onViewBillClick,
            modifier = Modifier
                .padding(24.dp)
                .height(55.dp)
                .constrainAs(billBtn) {
                    top.linkTo(statusSwitcher.bottom, 32.dp)
                    bottom.linkTo(parent.bottom, 32.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            colors = ButtonDefaults.buttonColors(containerColor = CustomColors.LightBlue),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, CustomColors.RichCerulean)
        ) {
            Icon(Icons.Default.Receipt, contentDescription = null, tint = CustomColors.RichCerulean)
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                "View Billing",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                color = CustomColors.RichCerulean,
                fontWeight = FontWeight.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = CustomColors.RichCerulean.copy(alpha = 0.4f))
        }
    }
}



@Composable
fun InfoCard(
    label: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(CustomColors.OffWhite, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier.size(18.dp),
            tint = CustomColors.BlackberryCream.copy(alpha = 0.4f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
            color = CustomColors.BlackberryCream.copy(alpha = 0.4f)
        )
        Text(
            text = value,
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
            color = CustomColors.RichCerulean
        )
    }
}

@Composable
fun FullWidthInfoCard(
    label: String,
    value: String,
    icon: ImageVector
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(CustomColors.OffWhite, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.White, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = CustomColors.RichCerulean)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = label,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                color = CustomColors.BlackberryCream.copy(alpha = 0.4f)
            )
            Text(
                text = value,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                color = CustomColors.BlackberryCream
            )
        }
    }
}

@Composable
fun StatusSwitcher(
    isActive: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(if (isActive) CustomColors.Cream else CustomColors.OffWhite)
            .border(
                1.dp,
                if (isActive) CustomColors.HoneyBronze.copy(alpha = 0.2f) else Color.Transparent,
                RoundedCornerShape(24.dp)
            )
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                "Status",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                color = CustomColors.BlackberryCream.copy(alpha = 0.6f),
                letterSpacing = 1.sp
            )
            Text(
                if (isActive) "Active" else "Inactive",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                color = if (isActive) CustomColors.HoneyBronze else CustomColors.LightGrayBlue
            )
        }

        Switch(
            checked = isActive,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = CustomColors.HoneyBronze,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = CustomColors.LightGrayBlue
            )
        )
    }
}