package com.messon.faculty.app.presentation.ui.screens.college


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.faculty.app.domain.dataModel.CollegeAdmin
import com.messon.faculty.app.presentation.ui.components.CollegeAdminCard
import com.messon.faculty.app.presentation.ui.components.SearchBar
import com.messon.faculty.app.presentation.viewmodel.MessListVM
import com.messon.faculty.app.theme.CustomColors
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansbold
import org.jetbrains.compose.resources.Font


@Composable
fun CollegeAdminScreen(
    onBackClick:()-> Unit,
    onFabClick:()-> Unit,
) {
    val dummyCollegeAdmins = listOf(
        CollegeAdmin(
            id = 1,
            name = "Rahul Sharma",
            email = "rahul.sharma@college.edu",
            isActive = true
        ),
        CollegeAdmin(
            id = 2,
            name = "Ananya Das",
            email = "ananya.das@college.edu",
            isActive = true
        ),
        CollegeAdmin(
            id = 3,
            name = "Amit Patel",
            email = "amit.patel@college.edu",
            isActive = false
        ),
        CollegeAdmin(
            id = 4,
            name = "Sneha Verma",
            email = "sneha.verma@college.edu",
            isActive = true
        ),
        CollegeAdmin(
            id = 5,
            name = "Rohit Singh",
            email = "rohit.singh@college.edu",
            isActive = true
        ),
        CollegeAdmin(
            id = 6,
            name = "Priya Nair",
            email = "priya.nair@college.edu",
            isActive = false
        )
    )
    var searchQuery by remember { mutableStateOf("") }
    var query by remember { mutableStateOf("") }
    val pullToRefreshState = rememberPullToRefreshState()


    ConstraintLayout(modifier = Modifier.fillMaxSize()
        .background(CustomColors.OffWhite)
    ) {
        val (
            statusBarBox, headingRef, listRef,
            backBtnRef, searchBarRef,
            fabRef
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
                onBackClick()
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
            text = "College Admin",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            modifier = Modifier.constrainAs(headingRef) {
                start.linkTo(backBtnRef.end, 5.dp)
                centerVerticallyTo(backBtnRef)
            }
        )

        SearchBar(
            query = query,
            onQueryChange = {
                query = it
            },
            modifier = Modifier.constrainAs(searchBarRef) {
                top.linkTo(backBtnRef.bottom, 10.dp)
                start.linkTo(parent.start, 8.dp)
                end.linkTo(parent.end, 8.dp)
                width = Dimension.fillToConstraints
            }
        )


        PullToRefreshBox(
            state = pullToRefreshState,
            indicator = {
                PullToRefreshDefaults.Indicator(
                    containerColor = CustomColors.RichCerulean,
                    color = Color.White,
                    isRefreshing =false,
                    state = pullToRefreshState
                )
            },
            isRefreshing = false,
            onRefresh = {
            },
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(listRef) {
                    top.linkTo(searchBarRef.bottom, 16.dp)
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            contentAlignment = Alignment.TopCenter
        ){
            Spacer(modifier= Modifier.height(6.dp))
            LazyColumn(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(dummyCollegeAdmins) { admin ->
                    CollegeAdminCard(
                        admin = admin,
                        onMoreClick = {

                        },
                        modifier = Modifier
                    )
                }
            }
        }

        FloatingActionButton(
            shape = CircleShape,
            onClick = onFabClick,
            containerColor = CustomColors.RichCerulean,
            contentColor = Color.White,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 8.dp,
                pressedElevation = 12.dp
            ),
            modifier = Modifier.constrainAs(fabRef) {
                end.linkTo(parent.end, margin = 20.dp)
                bottom.linkTo(parent.bottom, margin = 20.dp)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "send notification",
                modifier = Modifier.size(30.dp)
            )
        }
    }
}





