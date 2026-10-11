package com.messon.faculty.app.presentation.ui.screens.college

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.faculty.app.presentation.ui.components.CollegeAdminCard
import com.messon.faculty.app.presentation.ui.components.SearchBar
import com.messon.faculty.app.presentation.ui.uiState.FetchAdminState
import com.messon.faculty.app.presentation.viewmodel.AdminVM
import com.messon.faculty.composeapp.generated.resources.Res
import com.messon.faculty.composeapp.generated.resources.publicsansbold
import com.messon.faculty.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollegeAdminScreen(
    onBackClick: () -> Unit,
    onFabClick: () -> Unit,
    adminVM: AdminVM
) {

    val fetchAdminState by adminVM.fetchState.collectAsState()
    val selectedAdmins by adminVM.selectedAdmins.collectAsState()
    val selectionMode = selectedAdmins.isNotEmpty()


    val themeColors = MaterialTheme.colorScheme
    val dynamicPrimary = themeColors.primary
    val dynamicSurface = themeColors.surface
    val dynamicOutline = themeColors.outline

    LaunchedEffect(Unit){
        adminVM.fetchAdmin()
    }

    var query by remember { mutableStateOf("") }
    val pullToRefreshState = rememberPullToRefreshState()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (statusBarBox, headingRef, listRef, backBtnRef, searchBarRef, fabRef,selectionButtonsRef) = createRefs()

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
            Icon(
                Icons.Default.ChevronLeft,
                contentDescription = "back",
                modifier = Modifier.size(28.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        Text(
            text = "College Admin",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            color = MaterialTheme.colorScheme.onSurface,
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
                Button(
                    onClick = {
                        adminVM.clearSelection()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = dynamicSurface,
                        contentColor = dynamicPrimary
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = dynamicOutline
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cancel Selection",
                        tint = dynamicPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Cancel (${selectedAdmins.size})",
                        fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                        fontSize = 13.sp,
                        color = dynamicPrimary
                    )
                }

                Button(
                    onClick = {
                        adminVM.deleteSelectedAdmins()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = dynamicPrimary,
                        contentColor = dynamicSurface
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Selected",
                        tint = dynamicSurface,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Delete (${selectedAdmins.size})",
                        fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                        fontSize = 13.sp,
                        color = dynamicSurface
                    )
                }
            }
        }

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
            onRefresh = { },
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
            if(fetchAdminState is FetchAdminState.Success){
                LazyColumn(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items((fetchAdminState as FetchAdminState.Success).admins) { admin ->
                        CollegeAdminCard(
                            admin = admin,
                            onMoreClick = { },
                            modifier = Modifier,
                            isSelected = admin.id in selectedAdmins,
                            selectionMode = selectionMode,
                            onLongClick = {
                                adminVM.toggleSelection(admin.id)
                            },
                            onClick ={
                                if(selectionMode){
                                    adminVM.toggleSelection(admin.id)
                                }
                                else{
//                                    adminVM.selectAdmin(student)
//                                    onAdminClick()
                                }
                            }
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            shape = CircleShape,
            onClick = onFabClick,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
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
                contentDescription = "Add Admin",
                modifier = Modifier.size(30.dp)
            )
        }
    }
}