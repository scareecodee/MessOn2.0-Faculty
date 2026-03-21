package com.messon.faculty.app.presentation.ui.screens.mess
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.faculty.app.domain.dataModel.MessUnit
import com.messon.faculty.app.presentation.ui.components.MessClosureCard
import com.messon.faculty.app.presentation.ui.components.SearchBar
import com.messon.faculty.app.theme.CustomColors
import com.messon.faculty.app.theme.CustomColors.LightGrayBlue
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansbold
import org.jetbrains.compose.resources.Font


@Composable
fun MessClosureScreen(
    onCardClick:()->Unit,
    onBackBtnClick:()-> Unit
) {
    var query by remember { mutableStateOf("") }
    var messes by remember {
        mutableStateOf(
            listOf(
                MessUnit(1, "North Wing Mess", "Open", "Block A"),
                MessUnit(2, "South Block Dining", "Closed", "Hostel Ground"),
                MessUnit(3, "Annapurna Hall", "Open", "Main Campus"),
                MessUnit(4, "Girls Hostel Canteen", "Open", "Sector 4"),
                MessUnit(5, "PG Block Mess", "Closed", "Research Wing")
            )
        )
    }

    val open = messes.count { it.status == "Open" }
    val closed = messes.count { it.status == "Closed" }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
            .background(CustomColors.OffWhite)
    ) {
        val (statusBarBox,backBtnRef,headingRef,searchBar,listRef)=createRefs()
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
            text = "Mess Closure",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            modifier = Modifier.constrainAs(headingRef) {
                start.linkTo(backBtnRef.end, 5.dp)
                centerVerticallyTo(backBtnRef)
            }
        )


        SearchBar(
            placeholderText = "Search for mess by name or location",
            query = query,
            onQueryChange = {query=it},
            modifier = Modifier.constrainAs(searchBar){
                top.linkTo(backBtnRef.bottom, 10.dp)
                start.linkTo(parent.start, 5.dp)
                end.linkTo(parent.end, 5.dp)
                width = Dimension.fillToConstraints
            }
        )

        LazyColumn(
            modifier = Modifier.constrainAs(listRef){
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(searchBar.bottom, margin = 15.dp)
                bottom.linkTo(parent.bottom)
                height= Dimension.fillToConstraints
            },
            contentPadding = PaddingValues(bottom = 20.dp)
        ) {

            val filteredMesses = messes.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.location.contains(query, ignoreCase = true)
            }

            items(filteredMesses) { mess ->
                MessClosureCard(
                    mess = mess,
                    onCardClick ={

                    }
                )
            }
        }


    }
}

