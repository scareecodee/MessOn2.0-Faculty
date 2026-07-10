package com.messon.faculty.app.presentation.ui.screens.mess

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.faculty.app.domain.dataModel.ManageItem
import com.messon.faculty.app.presentation.ui.components.MyCard
import com.messon.faculty.app.presentation.viewmodel.MessListVM
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansbold
import messon_faculty.composeapp.generated.resources.publicsansmedium
import messon_faculty.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun DashboardScreen(
    messVM: MessListVM,
    onBackClick: () -> Unit = {},
    onStudentsClick: () -> Unit = {},
    onManagersClick: () -> Unit = {},
) {

    val items = listOf(
        ManageItem(
            "Mess Managers",
            "Manage mess staff",
            Icons.Default.Person,
            listOf(MaterialTheme.colorScheme.tertiary, MaterialTheme.colorScheme.tertiaryContainer)
        ),
        ManageItem(
            "Students",
            "Add or remove mess students",
            Icons.Default.Groups,
            listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.primaryContainer)
        )
    )

    val mess by messVM.selectedMess.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {

            val (title, list, statusBarBox, backBtnRef, headingRef) = createRefs()

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .constrainAs(statusBarBox) { top.linkTo(parent.top) }
            )

            IconButton(
                onClick = { onBackClick() },
                modifier = Modifier.constrainAs(backBtnRef) {
                    start.linkTo(parent.start, 15.dp)
                    top.linkTo(statusBarBox.bottom, 10.dp)
                }
            ) {
                Icon(Icons.Default.ChevronLeft, contentDescription = "back", modifier = Modifier.size(28.dp))
            }

            Text(
                text = "Dashboard",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.constrainAs(headingRef) {
                    start.linkTo(backBtnRef.end, 5.dp)
                    centerVerticallyTo(backBtnRef)
                }
            )

            Text(
                text = mess?.name ?: "N/A",
                fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(backBtnRef.bottom, margin = 12.dp)
                    start.linkTo(parent.start, margin = 20.dp)
                }
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(17.dp),
                modifier = Modifier.constrainAs(list) {
                    top.linkTo(title.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }
            ) {
                items(items) { item ->
                    MyCard(
                        gradientColors = item.gradient.map { it.copy(alpha = 0.85f) },
                        modifier = Modifier.fillMaxWidth(),
                        onCardClick = {
                            when (item.title) {
                                "Students" -> onStudentsClick()
                                "Mess Managers" -> onManagersClick()
                            }
                        }
                    ) {
                        ConstraintLayout(
                            modifier = Modifier.fillMaxSize().padding(20.dp)
                        ) {
                            val (iconSurface, textColumn, arrow) = createRefs()

                            Surface(
                                shape = MaterialTheme.shapes.medium,
                                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.2f),
                                modifier = Modifier.constrainAs(iconSurface) {
                                    start.linkTo(parent.start)
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                }
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = null,
                                    modifier = Modifier.padding(12.dp).size(28.dp),
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            ConstraintLayout(
                                modifier = Modifier.constrainAs(textColumn) {
                                    start.linkTo(iconSurface.end, margin = 16.dp)
                                    end.linkTo(arrow.start)
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    width = Dimension.fillToConstraints
                                }
                            ) {
                                val (titleText, subtitleText) = createRefs()
                                Text(
                                    text = item.title,
                                    maxLines = 2,
                                    fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.constrainAs(titleText) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start)
                                    }
                                )
                                Text(
                                    maxLines = 1,
                                    text = item.subtitle,
                                    fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                                    modifier = Modifier.constrainAs(subtitleText) {
                                        top.linkTo(titleText.bottom, margin = 4.dp)
                                        start.linkTo(parent.start)
                                    }
                                )
                            }

                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(25.dp).constrainAs(arrow) {
                                    end.linkTo(parent.end)
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}