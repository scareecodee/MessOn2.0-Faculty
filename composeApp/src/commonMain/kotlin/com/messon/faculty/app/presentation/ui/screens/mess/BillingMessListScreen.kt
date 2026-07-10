package com.messon.faculty.app.presentation.ui.screens.mess

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.messon.faculty.app.domain.dataModel.MessCreateRes
import com.messon.faculty.app.presentation.ui.components.BillingBottomSheet
import com.messon.faculty.app.presentation.ui.components.MessSleekCard
import com.messon.faculty.app.presentation.ui.components.SearchBar
import com.messon.faculty.app.presentation.ui.uiState.FetchMessState
import com.messon.faculty.app.presentation.viewmodel.MessBillVM
import com.messon.faculty.app.presentation.viewmodel.MessListVM
import com.messon.faculty.app.utils.PredefinedOptions
import com.messon.faculty.app.utils.Screen
import com.messon.faculty.app.utils.toIsoDate
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansbold
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.Font

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillingMessListScreen(
    navController: NavController,
    onBackBtnClick: () -> Unit,
    messListVM: MessListVM,
    onPriceClick: () -> Unit,
    messBillVM: MessBillVM
) {

    val sheetState = rememberModalBottomSheetState()
    val fromDate by messBillVM.fromDate.collectAsState()
    val toDate by messBillVM.endDate.collectAsState()
    val predefinedOption by messBillVM.predefinedOption.collectAsState()
    val isValidSelection =
        (predefinedOption != null) ||
                (!fromDate.isNullOrEmpty() && !toDate.isNullOrEmpty())
    var showBtmSheet by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }
    val pullToRefreshState = rememberPullToRefreshState()
    val uiState by messListVM.uiState.collectAsState()
    var messes by remember { mutableStateOf(emptyList<MessCreateRes>()) }

    LaunchedEffect(Unit) {
        messListVM.fetchAllMess()
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is FetchMessState.Error -> {
                showToast((uiState as FetchMessState.Error).message)
                messListVM.clearError()
            }
            is FetchMessState.Success -> {
                messes = (uiState as FetchMessState.Success).messes
            }
            else -> {}
        }
    }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        val (list, _, statusBarBox, backBtnRef, headingRef, _, searchBarRef) = createRefs()

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
            onClick = { onBackBtnClick() },
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
            text = "Mess List",
            fontSize = 20.sp,
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
                start.linkTo(parent.start, 10.dp)
                end.linkTo(parent.end, 10.dp)
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
            onRefresh = { },
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
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 10.dp),
                contentPadding = PaddingValues(bottom = 8.dp)
            ) {
                items(messes) { mess ->
                    MessSleekCard(
                        messName = mess.name,
                        location = mess.location,
                        onPriceClick = onPriceClick,
                        onBillingClick = {
                            messBillVM.selectMess(mess)
                            showBtmSheet = !showBtmSheet
                        },
                        modifier = Modifier
                    )
                }
            }
        }
    }

    if (showBtmSheet) {
        BillingBottomSheet(
            bottomSheetState = sheetState,
            predefinedOptions = listOf(
                PredefinedOptions.LAST_7_DAYS.value,
                PredefinedOptions.LAST_30_DAYS.value,
                PredefinedOptions.LAST_60_DAYS.value,
            ),
            selectedOption = predefinedOption?.value,
            onOptionSelected = {
                val selectedOption = when (it) {
                    PredefinedOptions.LAST_7_DAYS.value -> PredefinedOptions.LAST_7_DAYS
                    PredefinedOptions.LAST_30_DAYS.value -> PredefinedOptions.LAST_30_DAYS
                    PredefinedOptions.LAST_60_DAYS.value -> PredefinedOptions.LAST_60_DAYS
                    else -> return@BillingBottomSheet
                }
                messBillVM.selectPredefinedOption(selectedOption)
            },
            fromDate = fromDate,
            toDate = toDate,
            onFromDateChange = { messBillVM.setFromDate(it.toIsoDate()) },
            onToDateChange = { messBillVM.setEndDate(it.toIsoDate()) },
            onDismiss = {
                messBillVM.setFromDate(null)
                messBillVM.setEndDate(null)
                showBtmSheet = false
            },
            isSubmitEnabled = isValidSelection,
            onSubmit = {
                messBillVM.fetchMessBill()
                navController.navigate(Screen.MessBillScreen.route)
                showBtmSheet = false
            }
        )
    }
}