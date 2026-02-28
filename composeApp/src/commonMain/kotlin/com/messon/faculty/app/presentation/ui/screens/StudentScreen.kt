package com.messon.faculty.app.presentation.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
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
import messon_faculty.composeapp.generated.resources.publicsanssemibold
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.Font

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentScreen(
    modifier: Modifier = Modifier,
    onBackBtnClick: () -> Unit,
    studentsList: List<Student>,
    filePickerViewModel: FilePickerViewModel,
    navController: NavController,
) {
    val filePickerState by filePickerViewModel.state.collectAsState(initial = FilePickerState.Idle)
    var isLoading by remember { mutableStateOf(false) }
    var isSheetOpen by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf(StudentListFilterType.ID) }
    var query by remember { mutableStateOf("") }

    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
        confirmValueChange = { newValue ->
            newValue != SheetValue.Expanded
        }
    )
    LaunchedEffect(Unit) {
        filePickerViewModel.navigationEvent.collect { event ->
            if (event is FilePickerViewModel.NavigationEvent.NavigateToCsvPreview) {
                navController.navigate(Screen.CsvPreviewScreen.route)
            }
        }
    }

    LaunchedEffect(filePickerState) {
        isLoading = filePickerState is FilePickerState.Loading
        if (filePickerState is FilePickerState.Error) {
            showToast((filePickerState as FilePickerState.Error).message)
            filePickerViewModel.clear()
        }
    }

    val filteredStudents = remember(query, selectedFilter, studentsList) {
        studentsList.filter {
            when (selectedFilter) {
                StudentListFilterType.NAME -> it.name.contains(query, ignoreCase = true)
                StudentListFilterType.ID -> it.studentId.contains(query, ignoreCase = true)
            }
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.White
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (
                    statusBarBox, headingRef, listRef,
                    backBtnRef, searchBarRef,
                    addStudentBtnRef
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


                IconButton(
                    onClick = {
                        filePickerViewModel.clear()
                        onBackBtnClick()
                    },
                    modifier = Modifier.constrainAs(backBtnRef) {
                        start.linkTo(parent.start, 15.dp)
                        top.linkTo(statusBarBox.bottom, 10.dp)
                    }
                ) {
                    Icon(Icons.Default.ChevronLeft, contentDescription = "back", modifier = Modifier.size(28.dp)
                    )
                }


                Text(
                    text = "Students List",
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                    modifier = Modifier.constrainAs(headingRef) {
                        start.linkTo(backBtnRef.end, 5.dp)
                        centerVerticallyTo(backBtnRef)
                    }
                )


                Button(
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomColors.OffWhite
                    ),
                    border = BorderStroke(width = 1.dp, color = CustomColors.RichCerulean),
                    onClick = { isSheetOpen = true },
                    modifier = Modifier.constrainAs(addStudentBtnRef) {
                        end.linkTo(parent.end, 16.dp)
                        centerVerticallyTo(backBtnRef)
                    }
                        .height(40.dp)
                ) {

                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Student",
                        tint = CustomColors.RichCerulean,
                        modifier = Modifier.size(20.dp)
                    )

                    Text(
                        text = "Add Students ",
                        fontFamily = FontFamily(Font((Res.font.publicsanssemibold))),
                        fontSize = 10.sp,
                        color = CustomColors.RichCerulean,
                    )                }

                SearchBar(
                    query = query,
                    onQueryChange = { query = it },
                    selectedFilter = selectedFilter,
                    onFilterSelected = { selectedFilter = it },
                    modifier = Modifier.constrainAs(searchBarRef) {
                        top.linkTo(backBtnRef.bottom, 20.dp)
                        start.linkTo(parent.start, 10.dp)
                        end.linkTo(parent.end, 10.dp)
                        width = Dimension.fillToConstraints
                    }
                )

                LazyColumn(
                    modifier = Modifier.constrainAs(listRef) {
                        top.linkTo(searchBarRef.bottom, 16.dp)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                ) {
                    items(filteredStudents, key = { it.studentId }) { student ->
                        StudentListItem(
                            modifier = Modifier.fillMaxWidth(),
                            profileImageUrl = student.profileImg,
                            name = student.name,
                            studentId = student.studentId,
                            isActive = student.isActive,
                            onMoreClick = {},
                            onClick = {}
                        )
                    }
                }
            }


            if (isLoading) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = CustomColors.RichCerulean)
                }
            }
        }
    }

    if (isSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { isSheetOpen = false },
            sheetState = bottomSheetState,
            shape = RoundedCornerShape(topStart = 45.dp, topEnd = 45.dp),
            containerColor = Color.White,
            dragHandle = null
        ) {
            BottomSheetContent(
                modifier = Modifier.padding(top = 15.dp, bottom = 20.dp),
                onUploadCsvClick = {
                    filePickerViewModel.pickCsvFile()
                    isSheetOpen = false
                },
                onAddManuallyClick = {
                    navController.navigate(Screen.AddStudentManuallyScreen.route)
                    isSheetOpen = false
                }
            )
        }
    }
}