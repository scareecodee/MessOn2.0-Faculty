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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
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
import com.messon.faculty.app.presentation.ui.components.SearchBar
import com.messon.faculty.app.theme.CustomColors
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansbold
import messon_faculty.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessBillScreen(
    modifier: Modifier = Modifier,
    onBackBtnClick: () -> Unit,
    navController: NavController,
) {
    val pullToRefreshState = rememberPullToRefreshState()
    var isLoading by remember { mutableStateOf(false) }
    var isSheetOpen by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.White
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            ConstraintLayout(modifier = Modifier.fillMaxSize()
                .background(CustomColors.OffWhite)
            ) {
                val (
                    statusBarBox, headingRef, listRef,
                    backBtnRef, searchBarRef,
                    addStudentBtnRef,
                    filterChipsRow
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
                    onQueryChange = { query = it
                    },
                    modifier = Modifier.constrainAs(searchBarRef) {
                        top.linkTo(backBtnRef.bottom, 20.dp)
                        start.linkTo(parent.start, 10.dp)
                        end.linkTo(parent.end, 10.dp)
                        width = Dimension.fillToConstraints
                    }
                )

                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(7.dp),
                        state = lazyListState,
                        modifier =Modifier
                            .fillMaxSize()
                            .constrainAs(listRef) {
                                top.linkTo(searchBarRef.bottom, 16.dp)
                                bottom.linkTo(parent.bottom, margin = 5.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                                height = Dimension.fillToConstraints
                            }
                    ) {
                        items(fetchStudentsState.students,
                            key = {it.studentId}
                        ) { student ->
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