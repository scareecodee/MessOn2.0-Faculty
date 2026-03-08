package com.messon.faculty.app.presentation.ui.screens.students
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.messon.faculty.app.presentation.ui.components.AddStudentListItem
import com.messon.faculty.app.presentation.ui.components.StudentInputCard
import com.messon.faculty.app.presentation.viewmodel.AddStudentManuallyVM
import com.messon.faculty.app.theme.CustomColors
import com.messon.faculty.app.presentation.ui.uiState.AddStudentsManuallyState
import com.messon.faculty.app.presentation.viewmodel.MessListVM
import com.messon.faculty.app.utils.Screen
import kotlinx.coroutines.delay
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansbold
import messon_faculty.composeapp.generated.resources.publicsansmedium
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.Font


@Composable
fun AddStudentManuallyScreen(
    onBackBtnClick:()->Unit,
    addStudentManuallyVM: AddStudentManuallyVM,
    navController: NavController,
    messListVM: MessListVM
) {
    DisposableEffect(Unit) {
        onDispose {
            addStudentManuallyVM.clearCurrentStudents()
        }
    }

    val uiState by addStudentManuallyVM.state.collectAsState()

    val students by addStudentManuallyVM.students.collectAsState()
    var showForm by remember { mutableStateOf(false) }
    val selectedMess by messListVM.selectedMess.collectAsState()

    LaunchedEffect(Unit){
        addStudentManuallyVM.clearUiState()
    }

    LaunchedEffect(uiState) {

        when (uiState) {

            is AddStudentsManuallyState.Success -> {
                if((uiState as AddStudentsManuallyState.Success).addStudentsManuallyRes.isActive){
                    showToast(
                        message ="Students added successfully"
                    )
                }
                else{
                    delay(300)
                    navController.navigate(Screen.DataValidityScreen.route)
                }
            }

            is AddStudentsManuallyState.Error -> {
                showToast(
                    message =(uiState as    AddStudentsManuallyState.Error).message
                )

                addStudentManuallyVM.clearUiState()
            }

            else -> Unit
        }
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize().background(Color.White).imePadding()) {
        val (content, bottomButton,statusBarBox,backBtnRef,headingRef) = createRefs()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                .background(CustomColors.LightGrayBlue)
                .constrainAs(statusBarBox) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        )

        IconButton(
            shape = CircleShape,
            onClick = {
                onBackBtnClick()
            },
            modifier = Modifier.constrainAs(backBtnRef) {
                start.linkTo(parent.start, margin = 15.dp)
                top.linkTo(statusBarBox.bottom, margin = 10.dp)
            }
        ) {
            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = "back btn",
                modifier = Modifier.size(28.dp)
            )
        }

        Text(
            maxLines = 1,
            text = "Add Students Manually",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            modifier = Modifier
                .constrainAs(headingRef) {
                    start.linkTo(backBtnRef.end, margin = 5.dp)
                    top.linkTo(backBtnRef.top)
                    bottom.linkTo(backBtnRef.bottom)
                }
        )


        LazyColumn(
            modifier = Modifier.constrainAs(content) {
                top.linkTo(backBtnRef.bottom, 24.dp)
                bottom.linkTo(bottomButton.top)
                height = Dimension.fillToConstraints
            }.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            items(students) { student ->
                AddStudentListItem(
                    student =student,
                ) {
                    addStudentManuallyVM.removeStudent(student)
                }
            }

            item {
                if (showForm) {
                    StudentInputCard(
                        onAdd = { addStudentManuallyVM.addStudent(it); showForm = false },
                        onCancel = { showForm = false }
                    )
                } else {
                    TextButton(
                        onClick = { showForm = true },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PersonAdd,
                            contentDescription = null,
                            tint = CustomColors.RichCerulean,
                            modifier = Modifier.size(25.dp)
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            text = "Add another student",
                            maxLines = 1,
                            fontSize = 16.sp,
                            overflow = TextOverflow.Ellipsis,
                            color = CustomColors.RichCerulean,
                            fontFamily = FontFamily(Font(Res.font.publicsansmedium))
                        )
                    }
                }
            }
        }

        Button(
            enabled = uiState !is AddStudentsManuallyState.Loading,
            colors = ButtonDefaults.buttonColors(
                containerColor = CustomColors.RichCerulean
            ),
            onClick = {
                if (students.isNotEmpty()) {
                    selectedMess?.id?.let {messId->
                        addStudentManuallyVM.addStudentsToDb(
                            messId =messId
                        )
                    }
                } else {
                    showToast("Add at least one student")
                }
            },
            modifier = Modifier.constrainAs(bottomButton) {
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                bottom.linkTo(parent.bottom, margin = 30.dp)
                width= Dimension.fillToConstraints
            }.height(50.dp)
            ,
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Add all Students",
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White,
                fontFamily = FontFamily(Font(Res.font.publicsansbold))
            )
        }
    }
}