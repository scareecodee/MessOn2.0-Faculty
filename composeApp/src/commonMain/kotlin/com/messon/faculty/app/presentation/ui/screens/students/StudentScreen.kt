package com.messon.faculty.app.presentation.ui.screens.students

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.messon.faculty.app.presentation.ui.components.BottomSheetContent
import com.messon.faculty.app.presentation.ui.components.SearchBar
import com.messon.faculty.app.presentation.ui.components.StudentCard
import com.messon.faculty.app.presentation.viewmodel.FilePickerViewModel
import com.messon.faculty.app.presentation.viewmodel.MessListVM
import com.messon.faculty.app.presentation.viewmodel.StudentDetailScreenVM
import com.messon.faculty.app.presentation.viewmodel.StudentScreenVM
import com.messon.faculty.app.theme.CustomColors
import com.messon.faculty.app.utils.FilePickerState
import com.messon.faculty.app.utils.Screen
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.name
import kotlinx.coroutines.flow.distinctUntilChanged
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansbold
import messon_faculty.composeapp.generated.resources.publicsansregular
import messon_faculty.composeapp.generated.resources.publicsanssemibold
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.Font

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentScreen(
    modifier: Modifier = Modifier,
    onBackBtnClick: () -> Unit,
    filePickerViewModel: FilePickerViewModel,
    navController: NavController,
    studentDetailScreenVM: StudentDetailScreenVM,
    onStudentClick: () -> Unit,
    studentScreenVM: StudentScreenVM,
    messListVM: MessListVM
) {
    val pullToRefreshState = rememberPullToRefreshState()
    val fetchStudentsState by studentScreenVM.state.collectAsState()
    val isRefreshing: Boolean = fetchStudentsState.isRefreshing
    val filePickerState by filePickerViewModel.state.collectAsState(initial = FilePickerState.Idle)
    var isLoading by remember { mutableStateOf(false) }
    var isSheetOpen by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }
    val selectedStudents by studentScreenVM.selectedStudents.collectAsState()
    val selectionMode = selectedStudents.isNotEmpty()

    val selectedMess by messListVM.selectedMess.collectAsState()

    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
        confirmValueChange = { newValue ->
            newValue != SheetValue.Expanded
        }
    )

    LaunchedEffect(Unit) {
        selectedMess?.id?.let {
            studentScreenVM.setMessId(it)
            studentScreenVM.loadFirstPage()
        }
    }

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

    var selectedFile by remember { mutableStateOf<PlatformFile?>(null) }
    val lazyListState = rememberLazyListState()

    val filePickerLauncher = rememberFilePickerLauncher(
        type = FileKitType.File(listOf("csv"))
    ) { file ->
        file?.let {
            selectedFile = it
            showToast(it.name)
            filePickerViewModel.onFilePick(it)
        }
    }

    LaunchedEffect(lazyListState, fetchStudentsState) {
        snapshotFlow { lazyListState.layoutInfo }
            .distinctUntilChanged()
            .collect { layoutInfo ->
                val totalItems = layoutInfo.totalItemsCount
                val lastVisibleIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1

                if (lastVisibleIndex >= totalItems - 3 &&
                    totalItems > 0 &&
                    !fetchStudentsState.isLoadingMore &&
                    !fetchStudentsState.endReached &&
                    fetchStudentsState.error == null
                ) {
                    studentScreenVM.loadNextItems()
                }
            }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.White
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(CustomColors.OffWhite)
            ) {
                val (
                    statusBarBox, headingRef, listRef,
                    backBtnRef, searchBarRef, selectionButtonsRef,
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
                    Icon(
                        Icons.Default.ChevronLeft,
                        contentDescription = "back",
                        modifier = Modifier.size(28.dp)
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
                    )
                }

                SearchBar(
                    query = query,
                    onQueryChange = { query = it
                        studentScreenVM.setSearch(search = query)
                    },
                    modifier = Modifier.constrainAs(searchBarRef) {
                        top.linkTo(backBtnRef.bottom, 20.dp)
                        start.linkTo(parent.start, 10.dp)
                        end.linkTo(parent.end, 10.dp)
                        width = Dimension.fillToConstraints
                    }
                )

                if (selectionMode) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .constrainAs(selectionButtonsRef) {
                                top.linkTo(searchBarRef.bottom, 12.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            },
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Cancel Button
                        Button(
                            onClick = {
                                studentScreenVM.clearSelection()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = CustomColors.RichCerulean
                            ),
                            border = BorderStroke(
                                width = 1.dp,
                                color = CustomColors.LightGrayBlue
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Cancel Selection",
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Cancel (${selectedStudents.size})",
                                fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                                fontSize = 13.sp,
                                color = CustomColors.RichCerulean
                            )
                        }

                        Button(
                            onClick = {
                                studentScreenVM.deleteSelectedStudents()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CustomColors.RichCerulean,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Selected",
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Delete (${selectedStudents.size})",
                                fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                                fontSize = 13.sp,
                                color = Color.White
                            )
                        }
                    }
                }

                PullToRefreshBox(
                    state = pullToRefreshState,
                    indicator = {
                        PullToRefreshDefaults.Indicator(
                            containerColor = CustomColors.RichCerulean,
                            color = Color.White,
                            isRefreshing = isRefreshing,
                            state = pullToRefreshState
                        )
                    },
                    isRefreshing = isRefreshing,
                    onRefresh = {
                        studentScreenVM.manualRefresh()
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .constrainAs(listRef) {
                            top.linkTo(
                                if (selectionMode) selectionButtonsRef.bottom else searchBarRef.bottom,
                                16.dp
                            )
                            bottom.linkTo(parent.bottom, margin = 5.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                            height = Dimension.fillToConstraints
                        },
                    contentAlignment = Alignment.TopCenter
                ) {
                    Spacer(modifier = Modifier.height(6.dp))

                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(7.dp),
                        state = lazyListState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            fetchStudentsState.students,
                            key = { it.studentId }
                        ) { student ->
                            StudentCard(
                                modifier = Modifier.fillMaxWidth(),
                                imageUrl = student.profileImg,
                                name = student.name,
                                studentId = student.studentId,
                                isActive = student.isActive,
                                onMoreClick = {},
                                onClick = {
                                    if (selectionMode) {
                                        studentScreenVM.toggleSelection(student.uuid)
                                    } else {
                                        studentDetailScreenVM.selectStudent(student)
                                        onStudentClick()
                                    }
                                },
                                isSelected = student.uuid in selectedStudents,
                                selectionMode = selectionMode,
                                onLongClick = {
                                    studentScreenVM.toggleSelection(student.uuid)
                                },
                            )
                        }
                        if (fetchStudentsState.isLoadingMore || fetchStudentsState.error != null) {
                            item {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    when {
                                        fetchStudentsState.isLoadingMore -> {
                                            CircularProgressIndicator(
                                                strokeCap = StrokeCap.Round,
                                                color = CustomColors.RichCerulean
                                            )
                                        }
                                        fetchStudentsState.error != null -> {
                                            Text(
                                                text = "Unable to load more",
                                                fontSize = 14.sp,
                                                fontFamily = FontFamily(Font(Res.font.publicsansregular)),
                                                color = Color.Black,
                                            )
                                        }
                                    }
                                }
                            }
                        }
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
                    filePickerLauncher.launch()
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