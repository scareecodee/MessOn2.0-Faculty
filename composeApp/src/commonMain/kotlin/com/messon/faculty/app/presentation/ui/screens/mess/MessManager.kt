package com.messon.faculty.app.presentation.ui.screens.mess

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
import com.messon.faculty.app.presentation.ui.components.ManagerCard
import com.messon.faculty.app.presentation.ui.components.SearchBar
import com.messon.faculty.app.presentation.ui.uiState.FetchManagerState
import com.messon.faculty.app.presentation.viewmodel.ManagerListVM
import com.messon.faculty.app.presentation.viewmodel.MessListVM
import com.messon.faculty.composeapp.generated.resources.Res
import com.messon.faculty.composeapp.generated.resources.publicsansbold
import org.jetbrains.compose.resources.Font

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessManagerListScreen(
    onCardClick: () -> Unit,
    onBackClick: () -> Unit,
    onFabClick: () -> Unit,
    managerListVM: ManagerListVM,
    messListVM: MessListVM
) {
    var query by remember { mutableStateOf("") }
    val pullToRefreshState = rememberPullToRefreshState()

    val uiState by managerListVM.uiState.collectAsState()
    val selectedMess by messListVM.selectedMess.collectAsState()

    LaunchedEffect(Unit) {
        managerListVM.fetchAllManagers(messId = selectedMess?.id ?: "")
    }

    val managers = (uiState as? FetchManagerState.Success)?.managers ?: emptyList()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (
                statusBarBox, headingRef, listRef,
                backBtnRef, searchBarRef,
                fabRef
            ) = createRefs()

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .constrainAs(statusBarBox) {
                        top.linkTo(parent.top)
                    }
            )

            IconButton(
                onClick = { onBackClick() },
                modifier = Modifier.constrainAs(backBtnRef) {
                    start.linkTo(parent.start, 15.dp)
                    top.linkTo(statusBarBox.bottom, 10.dp)
                }
            ) {
                Icon(Icons.Default.ChevronLeft, contentDescription = "back", tint = MaterialTheme.colorScheme.onBackground)
            }

            Text(
                text = "Mess Manager",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                modifier = Modifier.constrainAs(headingRef) {
                    start.linkTo(backBtnRef.end, 5.dp)
                    centerVerticallyTo(backBtnRef)
                }
            )

            SearchBar(
                query = query,
                onQueryChange = { query = it },
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
                        containerColor = MaterialTheme.colorScheme.primary,
                        color = MaterialTheme.colorScheme.onPrimary,
                        isRefreshing = false,
                        state = pullToRefreshState
                    )
                },
                isRefreshing = false,
                onRefresh = { /* Refresh logic */ },
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
            ) {
                Spacer(modifier = Modifier.height(6.dp))
                LazyColumn(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(horizontal = 12.dp).fillMaxSize()
                ) {
                    selectedMess?.let { mess ->
                        item {
                            Text(
                                text = mess.name,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                                fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                            )
                        }
                    }
                    items(managers) { manager ->
                        ManagerCard(
                            manager = manager,
                            onCardClick = onCardClick,
                            onDeleteConfirm = { }
                        )
                    }
                }
            }

            FloatingActionButton(
                shape = CircleShape,
                onClick = onFabClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                elevation = FloatingActionButtonDefaults.elevation(8.dp, 12.dp),
                modifier = Modifier.constrainAs(fabRef) {
                    end.linkTo(parent.end, margin = 20.dp)
                    bottom.linkTo(parent.bottom, margin = 20.dp)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add manager",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}