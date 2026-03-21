package com.messon.faculty.app.presentation.ui.screens
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Campaign
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Receipt
import androidx.compose.material.icons.rounded.Restaurant
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.faculty.app.presentation.ui.components.BrandingCard
import com.messon.faculty.app.presentation.ui.components.GridCard
import com.messon.faculty.app.presentation.ui.components.PrimaryHeroCard
import com.messon.faculty.app.presentation.viewmodel.HomeScreenVM
import com.messon.faculty.app.theme.CustomColors.BlackberryCream
import com.messon.faculty.app.theme.CustomColors.Cream
import com.messon.faculty.app.theme.CustomColors.HoneyBronze
import com.messon.faculty.app.theme.CustomColors.LightBlue
import com.messon.faculty.app.theme.CustomColors.LightLavender
import com.messon.faculty.app.theme.CustomColors.Mint
import com.messon.faculty.app.theme.CustomColors.OffWhite
import com.messon.faculty.app.theme.CustomColors.RichCerulean
import com.messon.faculty.app.theme.CustomColors.Teal
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansbold
import messon_faculty.composeapp.generated.resources.publicsansregular
import messon_faculty.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun HomeScreen(
    onAdminCardClick:()->Unit,
    onMessCardClick:()->Unit,
    onClosureCardClick:()->Unit,
    onNoticeCardClick:()->Unit,
    onFeedbackCardClick:()->Unit,
    onBillCardClick:()-> Unit,
    homeScreenVM: HomeScreenVM
    ) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite)
    ) {
        val (header, scrollContent,statusBarBox) = createRefs()

        Box(
            Modifier
                .fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                .background(HoneyBronze.copy(alpha=0.6f))
                .constrainAs(statusBarBox) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width= Dimension.fillToConstraints
                }
        )
        HeaderSection(
            modifier = Modifier.constrainAs(header) {
                top.linkTo(statusBarBox.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )

        Column(
            modifier = Modifier
                .constrainAs(scrollContent) {
                    top.linkTo(header.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Text(
                text = "Administration",
                color = Color.Gray,
                fontFamily = FontFamily(Font((Res.font.publicsanssemibold))),
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp, bottom = 16.dp)
            )

            PrimaryHeroCard(
                title = "College Admins",
                description = "Manage admin access & roles",
                icon = Icons.Rounded.Shield,
                cardColor = LightBlue,
                primaryColor = RichCerulean,
                onClick = onAdminCardClick,
            )

            Spacer(modifier = Modifier.size(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                GridCard(
                    title = "Mess Config",
                    description = "Manage Mess",
                    icon = Icons.Rounded.Restaurant,
                    cardColor = Mint,
                    primaryColor = Teal,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        homeScreenVM.updateIsMessClosure(false)
                        onMessCardClick()
                    }
                )

                GridCard(
                    title = "Mess Closures",
                    description = "Manage off-days & holidays",
                    icon = Icons.Rounded.DateRange,
                    cardColor = LightLavender,
                    primaryColor = BlackberryCream,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        homeScreenVM.updateIsMessClosure(true)
                        onClosureCardClick()
                    }

                )
            }

            Spacer(modifier = Modifier.size(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                GridCard(
                    title = "Notice Board",
                    description = "Broadcast updates",
                    icon = Icons.Rounded.Campaign,
                    cardColor = LightBlue,
                    primaryColor = RichCerulean,
                    modifier = Modifier.weight(1f),
                    onClick = onNoticeCardClick
                )
                GridCard(
                    title = "Student Feedback",
                    description = "Review daily meal ratings",
                    icon = Icons.Rounded.Star,
                    cardColor = Cream,
                    primaryColor = HoneyBronze,
                    modifier = Modifier.weight(1f),
                    onClick = onFeedbackCardClick
                )
            }

            Spacer(modifier = Modifier.size(16.dp))

            PrimaryHeroCard(
                title = "Financials & Billing",
                description = "View Student Bills",
                icon = Icons.Rounded.Receipt,
                cardColor = Mint,
                primaryColor = Teal,
                onClick = onBillCardClick
            )

            Spacer(modifier = Modifier.size(32.dp))

            BrandingCard()

            Spacer(modifier = Modifier.size(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "MADE WITH ",
                    color = Color.Gray.copy(alpha = 0.8f),
                    fontFamily = FontFamily(Font((Res.font.publicsansregular))),
                    fontSize = 8.sp,
                )
                Icon(
                    imageVector = Icons.Rounded.Favorite,
                    contentDescription = "Love",
                    tint = Color(0xFFEF4444),
                    modifier = Modifier
                        .size(14.dp)
                        .padding(horizontal = 2.dp)
                )
                Text(
                    text = " FOR BHARAT",
                    color = Color.Gray.copy(alpha = 0.8f),
                    fontFamily = FontFamily(Font((Res.font.publicsansregular))),
                    fontSize = 8.sp,
                )
            }

            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
fun HeaderSection(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Cream,
        shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp),
        border = BorderStroke(1.dp, HoneyBronze.copy(alpha = 0.1f))
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 48.dp, bottom = 32.dp)
        ) {
            val (backgroundArt, welcomeText, nameText, locationBadge, profilePic) = createRefs()

            Canvas(
                modifier = Modifier.constrainAs(backgroundArt) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
            ) {
                drawCircle(
                    color = HoneyBronze.copy(alpha = 0.05f),
                    radius = size.width * 0.3f,
                    center = Offset(size.width, 0f)
                )
                drawCircle(
                    color = HoneyBronze.copy(alpha = 0.05f),
                    radius = size.width * 0.2f,
                    center = Offset(0f, size.height)
                )
            }

            Text(
                text = "Welcome Back",
                color = HoneyBronze.copy(alpha = 0.8f),
                fontFamily = FontFamily(Font((Res.font.publicsanssemibold))),
                fontSize = 12.sp,
                modifier = Modifier.constrainAs(welcomeText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
            )

            Text(
                text = "Bipin Chourasia",
                color = BlackberryCream,
                fontFamily = FontFamily(Font((Res.font.publicsansbold))),
                fontSize = 14.sp,
                modifier = Modifier.constrainAs(nameText) {
                    top.linkTo(welcomeText.bottom, margin = 2.dp)
                    start.linkTo(parent.start)
                }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .constrainAs(locationBadge) {
                        top.linkTo(nameText.bottom, margin = 12.dp)
                        start.linkTo(parent.start)
                    }
                    .background(Color.White.copy(alpha = 0.7f), RoundedCornerShape(8.dp))
                    .border(1.dp, HoneyBronze.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.LocationOn,
                    contentDescription = null,
                    tint = HoneyBronze,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "IIIT Bhubaneswar",
                    color = HoneyBronze,
                    fontFamily = FontFamily(Font((Res.font.publicsansregular))),
                    fontSize = 12.sp,
                )
            }

            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(Color.White, CircleShape)
                    .border(1.dp, HoneyBronze.copy(alpha = 0.2f), CircleShape)
                    .constrainAs(profilePic) {
                        end.linkTo(parent.end)
                        top.linkTo(welcomeText.bottom)
                        bottom.linkTo(locationBadge.bottom)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "Profile",
                    tint = HoneyBronze,
                    modifier = Modifier.size(32.dp)
                )

                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .align(Alignment.TopEnd)
                        .background(Color(0xFF34D399), CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                )
            }
        }
    }
}




