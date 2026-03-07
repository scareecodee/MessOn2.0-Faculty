package com.messon.faculty.app.presentation.ui.screens.mess

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
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
import com.messon.faculty.app.domain.dataModel.Manager
import com.messon.faculty.app.domain.dataModel.Mess
import com.messon.faculty.app.presentation.ui.components.MessCard
import com.messon.faculty.app.presentation.ui.components.SearchBar
import com.messon.faculty.app.presentation.ui.uiState.FetchCollegesState
import com.messon.faculty.app.presentation.ui.uiState.FetchMessState
import com.messon.faculty.app.presentation.viewmodel.MessListVM
import com.messon.faculty.app.theme.CustomColors
import com.messon.faculty.app.theme.CustomColors.LightGrayBlue
import com.messon.faculty.app.theme.CustomColors.OffWhite
import com.messon.faculty.app.theme.CustomColors.RichCerulean
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansbold
import org.jetbrains.compose.resources.Font

@Composable
fun MessListScreen(
    onCardClick:()-> Unit,
    onBackBtnClick:()-> Unit,
    onFabClick:()-> Unit,
    messListVM: MessListVM
) {

    var query by remember { mutableStateOf("") }
    val pullToRefreshState = rememberPullToRefreshState()
    val uiState by messListVM.uiState.collectAsState()

    LaunchedEffect(Unit){
        messListVM.fetchAllMess()
    }
    val messes = (uiState as? FetchMessState.Success)?.messes ?: emptyList()

    ConstraintLayout(
        modifier = Modifier.fillMaxSize().background(OffWhite)
    ) {
        val ( list, nav,statusBarBox,backBtnRef,headingRef,fabRef,searchBarRef) = createRefs()

        Box(
            Modifier
                .fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                .background(LightGrayBlue)
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
            text = "Mess List",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            modifier = Modifier.constrainAs(headingRef) {
                start.linkTo(backBtnRef.end, 5.dp)
                centerVerticallyTo(backBtnRef)
            }
        )

        SearchBar(
            query = query,
            onQueryChange = { query = it
            },
            modifier = Modifier.constrainAs(searchBarRef) {
                top.linkTo(backBtnRef.bottom, 10.dp)
                start.linkTo(parent.start, 10.dp)
                end.linkTo(parent.end, 10.dp)
                width = Dimension.fillToConstraints
            }
        )


        PullToRefreshBox(
            state = pullToRefreshState,
            indicator = {
                PullToRefreshDefaults.Indicator(
                    containerColor = CustomColors.RichCerulean,
                    color = Color.White,
                    isRefreshing = false,
                    state = pullToRefreshState
                )
            },
            isRefreshing =false,
            onRefresh = {
            },
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(list) {
                    top.linkTo(searchBarRef.bottom, 16.dp)
                    bottom.linkTo(parent.bottom, margin = 5.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            contentAlignment = Alignment.TopCenter
        ){
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                items(messes) { mess ->
                    MessCard(
                        mess = mess,
                        onCardClick = {
                            messListVM.selectMess(mess = mess)
                            onCardClick()
                        }
                    )
                }
            }
        }


        FloatingActionButton(
            shape = CircleShape,
            onClick = onFabClick,
            containerColor = RichCerulean,
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