package com.messon.faculty.app.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.faculty.app.domain.dataModel.MealDetail
import com.messon.faculty.app.domain.dataModel.StudentMealDay
import com.messon.faculty.app.utils.MealType
import com.messon.faculty.app.utils.toIsoDate
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansbold
import messon_faculty.composeapp.generated.resources.publicsansmedium
import messon_faculty.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun StudentMealTable(
    meals: List<StudentMealDay>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TableHeaderRow()

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            contentPadding = PaddingValues(bottom = 10.dp),
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(meals) { mealDay ->
                MealRow(mealDay)
            }
        }
    }
}

@Composable
private fun TableHeaderRow() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        val (dateRef, bfastRef, lunchRef, dinnerRef) = createRefs()

        Text(
            text = "DATE",
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 10.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            modifier = Modifier.constrainAs(dateRef) {
                start.linkTo(parent.start)
                width = Dimension.percent(0.25f)
            }
        )
        Text(
            text = "BREAKFAST",
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 10.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            modifier = Modifier.constrainAs(bfastRef) {
                start.linkTo(dateRef.end)
                width = Dimension.percent(0.25f)
            }
        )
        Text(
            text = "LUNCH",
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 10.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            modifier = Modifier.constrainAs(lunchRef) {
                start.linkTo(bfastRef.end)
                width = Dimension.percent(0.25f)
            }
        )
        Text(
            text = "DINNER",
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 10.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            modifier = Modifier.constrainAs(dinnerRef) {
                start.linkTo(lunchRef.end)
                end.linkTo(parent.end)
                width = Dimension.percent(0.25f)
            }
        )
    }
}

@Composable
private fun MealRow(mealDay: StudentMealDay) {
    Card(
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp)
        ) {
            val (dateRef, bfastRef, lunchRef, dinnerRef) = createRefs()

            Column(
                modifier = Modifier.constrainAs(dateRef) {
                    start.linkTo(parent.start)
                    width = Dimension.percent(0.25f)
                }
            ) {
                Text(
                    text = mealDay.date,
                    fontSize = 10.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Box(modifier = Modifier.constrainAs(bfastRef) {
                start.linkTo(dateRef.end)
                width = Dimension.percent(0.25f)
            }) {
                MealStatusIndicator(mealDay.breakfast)
            }

            Box(modifier = Modifier.constrainAs(lunchRef) {
                start.linkTo(bfastRef.end)
                width = Dimension.percent(0.25f)
            }) {
                MealStatusIndicator(mealDay.lunch)
            }

            Box(modifier = Modifier.constrainAs(dinnerRef) {
                start.linkTo(lunchRef.end)
                end.linkTo(parent.end)
                width = Dimension.percent(0.25f)
            }) {
                MealStatusIndicator(mealDay.dinner)
            }
        }
    }
    Divider(color = MaterialTheme.colorScheme.surfaceVariant, thickness = 1.dp)
}

@Composable
private fun MealStatusIndicator(detail: MealDetail) {
    if (!detail.selected) {
        Text(
            text = "—",
            color = MaterialTheme.colorScheme.outline,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 4.dp)
        )
    } else {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxHeight()
        ) {
            Icon(
                imageVector = if (detail.consumed) Icons.Default.CheckCircle else Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = if (detail.consumed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
            val mealType = MealType.from(detail.meaType)

            Text(
                text = when (mealType) {
                    MealType.VEG -> "Veg"
                    MealType.NON_VEG -> "Non-Veg"
                    MealType.NONE -> ""
                },
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                maxLines = 1
            )
        }
    }
}