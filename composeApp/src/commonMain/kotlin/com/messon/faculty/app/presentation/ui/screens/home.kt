package com.messon.faculty.app.presentation.ui.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.faculty.app.presentation.ui.components.BrandingCard
import com.messon.faculty.app.presentation.ui.components.FeatureGrid
import com.messon.faculty.app.theme.CustomColors
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansregular
import messon_faculty.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font


@Composable
fun HomeScreen(
    onRatingCardClick:()->Unit,
    onBillCardClick:()->Unit,
    onNoticeCardClick :()->Unit,
    onMessCardClick :()->Unit
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomColors.OffWhite)
    ) {

        val (header, content) = createRefs()

        DashboardHeader(
            modifier = Modifier.constrainAs(header) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )

        LazyColumn (
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .constrainAs(content) {
                    top.linkTo(header.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(bottom = 40.dp)
        ) {

            item { Spacer(Modifier.height(10.dp)) }

            item { FeatureGrid(
                onRatingCardClick = onRatingCardClick,
                onBillCardClick = onBillCardClick,
                onNoticeCardClick = onNoticeCardClick,
                onMessCardClick = onMessCardClick
            ) }

            item { Spacer(Modifier.height(20.dp)) }

            item { BrandingCard() }
        }
    }
}

@Composable
fun DashboardHeader(modifier: Modifier = Modifier) {

    Box(
        modifier = modifier.height(170.dp)
            .background(
                CustomColors.BlackberryCream.copy(0.85f),
                RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)
            )
            .padding(top=10.dp, bottom = 2.dp, end=20.dp,start = 20.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
                .systemBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    "Welcome back",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                    color = Color.White.copy(alpha = 0.8f)
                )

                Text(
                    "Bipin Chourasia",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = CustomColors.HoneyBronze,
                        modifier = Modifier.size(14.dp)
                    )

                    Spacer(Modifier.width(4.dp))

                    Text(
                        "IIIT Bhubaneswar",
                        fontFamily = FontFamily(Font(Res.font.publicsansregular)),
                        fontSize = 14.sp,
                        color = Color.White.copy(.9f)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(Color.White.copy(.2f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}