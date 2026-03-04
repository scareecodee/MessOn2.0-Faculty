package com.messon.faculty.app.presentation.ui.screens.students

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.messon.faculty.app.presentation.ui.components.BoxedIcon
import com.messon.faculty.app.presentation.ui.components.ProfilePicWithBadge
import com.messon.faculty.app.presentation.ui.components.StatusSwitcher
import com.messon.faculty.app.presentation.viewmodel.StudentDetailScreenVM
import com.messon.faculty.app.theme.CustomColors
import com.messon.faculty.app.presentation.ui.uiState.ToggleStatusState
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansbold
import messon_faculty.composeapp.generated.resources.publicsansmedium
import messon_faculty.composeapp.generated.resources.publicsanssemibold
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.Font

@Composable
fun StudentDetailScreen(
    onViewBillClick: () -> Unit,
    onBackClick: () -> Unit,
    onEditClick:()-> Unit,
    studentDetailScreenVM: StudentDetailScreenVM
) {
    val scrollState = rememberScrollState()
    val student by studentDetailScreenVM
        .selectedStudent
        .collectAsStateWithLifecycle()

    val toggleStatusState by studentDetailScreenVM.toggleStatusState.collectAsStateWithLifecycle()


    LaunchedEffect(toggleStatusState) {

        when (toggleStatusState) {

            is ToggleStatusState.Success -> {
                showToast(
                    message = "Updated detail of student"
                )
                studentDetailScreenVM.clearToggleStatusState()

            }

            is ToggleStatusState.Error -> {
                showToast(
                    message =(toggleStatusState as ToggleStatusState.Error).message
                )
                studentDetailScreenVM.clearToggleStatusState()
            }

            else -> Unit
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        val (statusBarBox,headerBg,backBtn, title, profileHeader,
            name, email, infoCards, branchCard, statusSwitcher, billBtn,
            editBtn
        ) = createRefs()

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


        BoxedIcon(
            modifier = Modifier.constrainAs(editBtn){
                end.linkTo(parent.end, margin = 16.dp)
                centerVerticallyTo(backBtn)
            }
                .padding(5.dp)
                .clip(CircleShape)
                .clickable{
                    onEditClick()
                }
            ,
            boxColor = CustomColors.RichCerulean.copy(0.2f),
            iconColor = CustomColors.RichCerulean,
            iconsSize = 17.dp,
            painter = rememberVectorPainter(image = Icons.Outlined.Edit),
            contentDescription = "edit btn",
            shape = CircleShape
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
                enabled = toggleStatusState !is ToggleStatusState.Loading,
                isActive = student!!.isActive,
                isLoading = toggleStatusState is ToggleStatusState.Loading,
                onToggle = {
                    studentDetailScreenVM.toggleStatus(student!!.uuid)
                }
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
            border = BorderStroke(1.dp, CustomColors.RichCerulean)
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

