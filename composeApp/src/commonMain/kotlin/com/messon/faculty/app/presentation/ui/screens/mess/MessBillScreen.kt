package com.messon.faculty.app.presentation.ui.screens.mess
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.messon.faculty.app.presentation.ui.components.SearchBar
import com.messon.faculty.app.presentation.ui.components.StudentBillCard
import com.messon.faculty.app.presentation.ui.uiState.CsvDownloadState
import com.messon.faculty.app.presentation.ui.uiState.FetchMessBillState
import com.messon.faculty.app.presentation.viewmodel.MessBillVM
import com.messon.faculty.app.theme.CustomColors
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansbold
import messon_faculty.composeapp.generated.resources.publicsanssemibold
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.Font
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessBillScreen(
    modifier: Modifier = Modifier,
    onBackBtnClick: () -> Unit,
    messBillVM: MessBillVM
) {

    var query by remember { mutableStateOf("") }

    val uiState by messBillVM.fetchMessBillState.collectAsState()
    val downloadState by messBillVM.downloadingState.collectAsState()
    val isDownloading = downloadState is CsvDownloadState.Loading

    val students = remember(uiState) {
        when (uiState) {
            is FetchMessBillState.Success -> {
                (uiState as FetchMessBillState.Success).bill ?: emptyList()
            }
            else -> emptyList()
        }
    }

    val isLoading = uiState is FetchMessBillState.Loading

    val filteredStudents = remember(students, query) {
        if (query.isBlank()) students
        else students.filter {
            it.name?.contains(query, ignoreCase = true) == true
        }
    }

    LaunchedEffect(uiState) {
        if (uiState is FetchMessBillState.Error) {
            showToast((uiState as FetchMessBillState.Error).message)
        }
    }

    LaunchedEffect(downloadState) {
        when (downloadState) {
            is CsvDownloadState.Success -> {
                val path = (downloadState as CsvDownloadState.Success).path
                showToast("Saved to: $path")
            }

            is CsvDownloadState.Error -> {
                showToast((downloadState as CsvDownloadState.Error).message)
            }

            else -> Unit
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
                    backBtnRef, searchBarRef,
                    downloadBtnRef
                ) = createRefs()

                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(
                            WindowInsets.safeDrawing
                                .asPaddingValues()
                                .calculateTopPadding() + 5.dp
                        )
                        .background(CustomColors.LightGrayBlue)
                        .constrainAs(statusBarBox) {
                            top.linkTo(parent.top)
                        }
                )

                IconButton(
                    onClick = onBackBtnClick,
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
                    text = "Mess Bill",
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                    modifier = Modifier.constrainAs(headingRef) {
                        start.linkTo(backBtnRef.end, 5.dp)
                        centerVerticallyTo(backBtnRef)
                    }
                )

                Button(
                    onClick = {
                        if (students.isEmpty()) {
                            showToast("No data to download")
                        } else {
                            messBillVM.downloadMessBillCsv()
                        }
                    },
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomColors.OffWhite
                    ),
                    border = BorderStroke(1.dp, CustomColors.RichCerulean),
                    modifier = Modifier
                        .height(40.dp)
                        .constrainAs(downloadBtnRef) {
                            end.linkTo(parent.end, 16.dp)
                            centerVerticallyTo(backBtnRef)
                        }
                ) {
                    Icon(
                        Icons.Default.Download,
                        contentDescription = null,
                        tint = CustomColors.RichCerulean,
                        modifier = Modifier.size(20.dp)
                    )

                    Text(
                        text = "Download Bill",
                        fontSize = 10.sp,
                        fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                        color = CustomColors.RichCerulean
                    )
                }

                SearchBar(
                    query = query,
                    onQueryChange = { query = it },
                    modifier = Modifier.constrainAs(searchBarRef) {
                        top.linkTo(backBtnRef.bottom, 20.dp)
                        start.linkTo(parent.start, 10.dp)
                        end.linkTo(parent.end, 10.dp)
                        width = Dimension.fillToConstraints
                    }
                )

                if (filteredStudents.isEmpty() && !isLoading) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .constrainAs(listRef) {
                                top.linkTo(searchBarRef.bottom)
                                bottom.linkTo(parent.bottom)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No students found")
                    }

                } else {
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .constrainAs(listRef) {
                                top.linkTo(searchBarRef.bottom, 16.dp)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                                height = Dimension.fillToConstraints
                            }
                    ) {
                        items(
                            items = filteredStudents
                        ) { student ->
                            StudentBillCard(
                                student = student,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            if (isLoading||isDownloading) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = CustomColors.RichCerulean
                    )
                }
            }
        }
    }
}