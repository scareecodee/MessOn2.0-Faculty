package com.messon.faculty.app.presentation.ui.screens.mess

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.faculty.app.presentation.ui.components.HolidayCard
import com.messon.faculty.app.theme.CustomColors
import com.messon.faculty.app.theme.CustomColors.LightGrayBlue
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansbold
import org.jetbrains.compose.resources.Font

data class Holiday(
    val id: Int,
    val title: String,
    val start: String,
    val end: String,
    val type: String,
    val color: Color,
    val bgColor: Color
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MessHolidaysScreen(
    onBackBtnClick:()-> Unit,
    onAddIconClick:()-> Unit
) {
    val holidays = listOf(
        Holiday(1, "Diwali Festivities", "31 Oct", "04 Nov", "Festival", CustomColors.HoneyBronze, CustomColors.Cream),
        Holiday(2, "Winter Semester Break", "24 Dec", "05 Jan", "Vacation", CustomColors.RichCerulean, CustomColors.LightBlue),
        Holiday(3, "Holi Celebration", "14 Mar", "16 Mar", "Holiday", CustomColors.BlackberryCream, CustomColors.LightLavender)
    )

    ConstraintLayout(
       modifier = Modifier
           .fillMaxSize()
           .background(CustomColors.OffWhite)
   ) {
        val ( list,addIcon,statusBarBox,backBtnRef,headingRef,fabRef,searchBarRef) = createRefs()

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
            text = "Holidays List",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            modifier = Modifier.constrainAs(headingRef) {
                start.linkTo(backBtnRef.end, 5.dp)
                centerVerticallyTo(backBtnRef)
            }
        )

        IconButton(
            onClick =onAddIconClick,
            shape = RoundedCornerShape(15.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = CustomColors.RichCerulean.copy(0.95f)
            ),
            modifier = Modifier.constrainAs(addIcon){
                end.linkTo(parent.end, margin = 16.dp)
                centerVerticallyTo(backBtnRef)
            }.size(45.dp)
        ){
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "add holidays",
                tint=Color.White,
                modifier = Modifier.size(25.dp)
            )
        }

       LazyColumn(
           modifier = Modifier.fillMaxWidth()
               .constrainAs(list){
                   top.linkTo(backBtnRef.bottom, margin = 10.dp)
                   bottom.linkTo(parent.bottom, margin = 5.dp)
                   start.linkTo(parent.start)
                   end.linkTo(parent.end)
                   width= Dimension.fillToConstraints
                   height= Dimension.fillToConstraints
               },
           contentPadding = PaddingValues( bottom = 120.dp)
       ) {
           items(holidays) { holiday ->
               HolidayCard(holiday)
           }
       }
   }
}

