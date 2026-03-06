package com.messon.faculty.app.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.messon.faculty.app.domain.dataModel.Mess
import com.messon.faculty.app.theme.CustomColors
import com.messon.faculty.app.theme.CustomColors.BlackberryCream
import com.messon.faculty.app.theme.CustomColors.LightBlue
import com.messon.faculty.app.theme.CustomColors.LightGrayBlue
import com.messon.faculty.app.theme.CustomColors.LightLavender
import com.messon.faculty.app.theme.CustomColors.RichCerulean
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansmedium
import messon_faculty.composeapp.generated.resources.publicsansregular
import messon_faculty.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun MessCard(
    onCardClick:()->Unit,
    mess: Mess
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clip(RoundedCornerShape(25.dp))
            .clickable { onCardClick() },
        shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier.padding(18.dp).fillMaxWidth()
        ) {
            val (icon, title, location, chevron, footer) = createRefs()

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(LightBlue, RoundedCornerShape(12.dp))
                    .constrainAs(icon) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Restaurant, null, tint = RichCerulean, modifier = Modifier.size(20.dp))
            }

            Text(
                text = mess.name,
                fontFamily = FontFamily(Font((Res.font.publicsanssemibold))),
                fontSize = 12.sp,
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(icon.top)
                    start.linkTo(icon.end, 16.dp)
                }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.constrainAs(location) {
                    top.linkTo(title.bottom, 4.dp)
                    start.linkTo(title.start)
                }
            ) {
                Icon(Icons.Default.LocationOn,
                    null, tint = LightGrayBlue, modifier = Modifier.size(12.dp))
                Spacer(Modifier.width(4.dp))
                Text(mess.location, color = LightGrayBlue,
                    fontSize = 11.sp,
                    fontFamily = FontFamily(Font((Res.font.publicsansregular))),
                )
            }

            Icon(
                Icons.Default.ChevronRight, null, tint = CustomColors.RichCerulean,
                modifier = Modifier.constrainAs(chevron) {
                    top.linkTo(icon.top)
                    bottom.linkTo(footer.bottom)
                    end.linkTo(parent.end)
                }.size(25.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.constrainAs(footer) {
                    top.linkTo(location.bottom, 10.dp)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                Surface(color = LightLavender, shape = RoundedCornerShape(8.dp)) {
                    Row(Modifier.padding(horizontal = 12.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.People, null, tint = BlackberryCream, modifier = Modifier.size(12.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("${mess.managers.size}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = BlackberryCream,
                            fontFamily = FontFamily(Font((Res.font.publicsansmedium))),

                        )
                    }
                }
                Spacer(Modifier.width(12.dp))
                Box(Modifier.size(6.dp).background(if (mess.status) CustomColors.HoneyBronze else Color.LightGray, CircleShape))
                Spacer(Modifier.width(6.dp))
                Text(
                  text = if( mess.status) "Active" else "Inactive",
                    fontFamily = FontFamily(Font((Res.font.publicsansregular))),
                    fontSize = 12.sp,
                )

            }
        }
    }
}