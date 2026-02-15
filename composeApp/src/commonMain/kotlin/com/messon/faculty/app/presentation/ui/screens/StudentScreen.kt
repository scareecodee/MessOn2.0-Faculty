package com.messon.faculty.app.presentation.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.messon.faculty.app.domain.dataModel.Student
import com.messon.faculty.app.presentation.ui.components.BottomSheetContent
import com.messon.faculty.app.presentation.ui.components.SearchBar
import com.messon.faculty.app.presentation.ui.components.StudentListFilterType
import com.messon.faculty.app.presentation.ui.components.StudentListItem
import com.messon.faculty.app.presentation.viewmodel.FilePickerViewModel
import com.messon.faculty.app.theme.CustomColors
import com.messon.faculty.app.utils.FilePickerState
import com.messon.faculty.app.utils.Screen
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansbold
import messon_faculty.composeapp.generated.resources.publicsansregular
import messon_faculty.composeapp.generated.resources.publicsanssemibold
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.Font


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentScreen(
    modifier: Modifier,
    onBackBtnClick: () -> Unit,
    studentsList: List<Student>,
    filePickerViewModel: FilePickerViewModel,
    navController: NavController
) {
    val filePickerState by filePickerViewModel.state.collectAsState(initial = FilePickerState.Idle)
    LaunchedEffect(Unit) {
        filePickerViewModel.navigationEvent.collect { event ->
            when (event) {
                is FilePickerViewModel.NavigationEvent.NavigateToCsvPreview -> {
                    navController.navigate(Screen.CsvPreviewScreen.route)
                }
            }
        }
    }

    LaunchedEffect(filePickerState) {

        when(filePickerState){

            is FilePickerState.Error -> {
                showToast((filePickerState as FilePickerState.Error).message)
            }

            else -> Unit
        }
    }


    var selectedFilter by remember { mutableStateOf(StudentListFilterType.ID) }
    var query by remember { mutableStateOf("") }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
        confirmValueChange = { newValue ->
            newValue != SheetValue.Expanded
        }
    )

    var isSheetOpen by remember{mutableStateOf(false)}
    val filteredStudents = remember(query, selectedFilter, studentsList) {
        studentsList.filter { student ->
            when (selectedFilter) {
                StudentListFilterType.NAME -> student.name.contains(query, ignoreCase = true)
                StudentListFilterType.ID -> student.studentId.contains(query, ignoreCase = true)
            }
        }
    }

    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (statusBarBox, headingRef, listRef, backBtnRef, searchBarRef, addStudentBtnRef, totalStudentRef) = createRefs()

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
                filePickerViewModel.clear()
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
            text = "Students List",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            modifier = Modifier
                .constrainAs(headingRef) {
                    start.linkTo(backBtnRef.end, margin = 5.dp)
                    top.linkTo(backBtnRef.top)
                    bottom.linkTo(backBtnRef.bottom)
                }
        )

        Box(
            modifier = Modifier
                .constrainAs(totalStudentRef) {
                    top.linkTo(backBtnRef.bottom, margin = 8.dp)
                    start.linkTo(backBtnRef.start)
                }
                .height(80.dp)
                .width(180.dp)
                .background(
                    color = CustomColors.HoneyBronze.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(20.dp)
                )
                .border(
                    width = 0.4.dp,
                    color = CustomColors.HoneyBronze.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Text(
                    text = "Total Enrolled Student",
                    fontFamily = FontFamily(Font(Res.font.publicsansregular)),
                    fontSize = 12.sp
                )
                Text(
                    text = studentsList.size.toString(),
                    fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                    fontSize = 25.sp,
                    color = CustomColors.HoneyBronze
                )
            }
        }


        Button(
            shape = RoundedCornerShape(30.dp),
            onClick = {
                isSheetOpen=true
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = CustomColors.OffWhite
            ),
            border = BorderStroke(width = 1.dp, color = CustomColors.RichCerulean),
            modifier = Modifier.constrainAs(addStudentBtnRef) {
                start.linkTo(totalStudentRef.end, margin = 14.dp)
                end.linkTo(parent.end, margin = 16.dp)
                top.linkTo(totalStudentRef.top)
                bottom.linkTo(totalStudentRef.bottom)
            }
                .height(50.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Student",
                tint = CustomColors.RichCerulean,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.size(5.dp))
            Text(
                text = "Add Students ",
                fontFamily = FontFamily(Font((Res.font.publicsanssemibold))),
                fontSize = 10.sp,
                color = CustomColors.RichCerulean,
            )
        }

        SearchBar(
            query = query,
            onQueryChange = {
                query = it
            },
            selectedFilter = selectedFilter,
            onFilterSelected = {
                selectedFilter = it
            },
            modifier = Modifier.constrainAs(searchBarRef) {
                top.linkTo(totalStudentRef.bottom, margin = 20.dp)
                start.linkTo(parent.start, margin = 20.dp)
                end.linkTo(parent.end, margin = 20.dp)
            }
        )

        LazyColumn(
            modifier = Modifier.constrainAs(listRef) {
                top.linkTo(searchBarRef.bottom, margin = 16.dp)
                start.linkTo(parent.start, margin = 10.dp)
                end.linkTo(parent.end, margin = 10.dp)
                bottom.linkTo(parent.bottom, margin = 8.dp)
                height = Dimension.fillToConstraints
            },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(filteredStudents){student->
                StudentListItem(
                    modifier = Modifier,
                    profileImageUrl =student.profileImg,
                    name =student.name,
                    studentId =student.studentId,
                    isActive = student.isActive,
                    onMoreClick ={},
                    onClick ={},
                )
            }

        }

    }

    if(isSheetOpen){
        ModalBottomSheet(
            dragHandle = { BottomSheetDefaults.DragHandle(
                color=Color.Black
            ) },
            shape = RoundedCornerShape(45.dp),
            sheetState =bottomSheetState ,
            onDismissRequest = {
                isSheetOpen=false
            },
            containerColor = Color.White,
        ){
            BottomSheetContent(
                modifier = Modifier,
                onUploadCsvClick = {
                    filePickerViewModel.pickCsvFile()
                },
                onAddManuallyClick = {

                }
            )
        }
    }

    when(filePickerState){
        is FilePickerState.Loading -> {
            CircularProgressIndicator()
        }
        else -> Unit
    }

}
