package com.messon.faculty.app.presentation.ui.screens.students
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.messon.faculty.app.presentation.ui.components.CustomInputField
import com.messon.faculty.app.presentation.ui.components.ProfilePicWithBadge
import com.messon.faculty.app.presentation.ui.uiState.AddStudentsManuallyState
import com.messon.faculty.app.presentation.ui.uiState.EditStudentState
import com.messon.faculty.app.presentation.viewmodel.EditStudentVM
import com.messon.faculty.app.presentation.viewmodel.StudentDetailScreenVM
import com.messon.faculty.app.theme.CustomColors
import kotlinx.coroutines.delay
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansbold
import messon_faculty.composeapp.generated.resources.publicsanssemibold
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.Font

@Composable
fun EditStudentScreen(
    onBackClick: () -> Unit = {},
    studentDetailScreenVM: StudentDetailScreenVM,
    onConflict:()-> Unit,
    editStudentVM: EditStudentVM
) {


    val student by studentDetailScreenVM
        .selectedStudent.collectAsState()

    val updateState by editStudentVM.updateState.collectAsState()


    LaunchedEffect(updateState){
        if(updateState is EditStudentState.Error){
            showToast(
                message =(updateState as EditStudentState.Error).message
            )
            editStudentVM.clearUpdateState()
        }
        if(updateState is EditStudentState.Success){
            if((updateState as EditStudentState.Success).editStudentRes.status){
                showToast(
                    message ="Students updated successfully"
                )
                editStudentVM.clearUpdateState()
            }else{
                delay(300)
                onConflict()
            }
        }
    }


    student?.let {student->
        var name by remember { mutableStateOf(student.name ) }
        var rollNumber by remember { mutableStateOf(student.studentId) }
        var email by remember { mutableStateOf(student.email) }
        var branch by remember { mutableStateOf(student.branch) }
        var batch by remember { mutableStateOf(student.batch) }




        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
        ) {
            val (contentRef, buttonRef,statusBarBox,backBtnRef,headingRef) = createRefs()

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                    .background(CustomColors.LightGrayBlue)
                    .constrainAs(statusBarBox) {
                        top.linkTo(parent.top)
                    }
            )


            IconButton(
                onClick = {
                    onBackClick()
                },
                modifier = Modifier.constrainAs(backBtnRef) {
                    start.linkTo(parent.start, 15.dp)
                    top.linkTo(statusBarBox.bottom, 10.dp)
                }
            ) {
                Icon(
                    Icons.Default.ChevronLeft,
                    contentDescription = "back",
                    modifier = Modifier.size(28.dp)
                )
            }


            Text(
                text = "Update Student",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                modifier = Modifier.constrainAs(headingRef) {
                    start.linkTo(backBtnRef.end, 5.dp)
                    centerVerticallyTo(backBtnRef)
                }
            )
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .constrainAs(contentRef) {
                        top.linkTo(backBtnRef.bottom, margin = 15.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(buttonRef.top)
                        height = Dimension.fillToConstraints
                    }
                    .padding(horizontal = 20.dp)
            ) {
                item{
                    ProfilePicWithBadge(
                        imageUrl = student.profileImg,
                        isActive = student.isActive,
                    )
                }

                item{
                    Spacer(modifier = Modifier.height(32.dp))

                }

                item{
                    CustomInputField(
                        label = "Full Name",
                        value = name,
                        onValueChange = { name = it },
                        icon = Icons.Default.Edit
                    )
                }


                item{
                    Spacer(modifier = Modifier.height(16.dp))
                }


                item {
                    CustomInputField(
                        label = "Student Id",
                        value = rollNumber,
                        onValueChange = { rollNumber = it },
                        icon = Icons.Default.Info
                    )
                }


                item{
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item{
                    CustomInputField(
                        label = "Email Address",
                        value = email,
                        onValueChange = { email = it },
                        icon = Icons.Default.Email,
                        keyboardType = KeyboardType.Email
                    )
                }


                item {
                    Spacer(modifier = Modifier.height(16.dp))

                }


                item{
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box(modifier = Modifier.weight(1.5f)) {
                            CustomInputField(
                                label = "Branch",
                                value = branch,
                                onValueChange = { branch=it },
                                icon = Icons.Default.Place
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Box(modifier = Modifier.weight(1f)) {
                            CustomInputField(
                                label = "Batch",
                                value = batch.toString(),
                                onValueChange = { batch = it.toInt() },
                                icon = Icons.Default.DateRange,
                                keyboardType = KeyboardType.Number
                            )
                        }
                    }
                }


                item{
                    Spacer(modifier = Modifier.height(40.dp))

                }
            }

            Button(
                enabled = updateState != EditStudentState.Loading,
                onClick = {

                    val updatedStudent = student.copy(
                        name = name,
                        studentId = rollNumber,
                        email = email,
                        branch = branch,
                        batch = batch
                    )

                    editStudentVM.setUpdateStudent(updatedStudent)

                    editStudentVM.editStudent(student.uuid)
                },
                modifier = Modifier
                    .constrainAs(buttonRef) {
                        top.linkTo(contentRef.bottom, margin = 10.dp)
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
                Text(text = "Save Changes",
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = FontFamily(Font(Res.font.publicsanssemibold))
                )
            }
        }
    }


}


