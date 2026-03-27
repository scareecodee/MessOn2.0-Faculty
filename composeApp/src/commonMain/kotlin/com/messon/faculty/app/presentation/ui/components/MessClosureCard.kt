package com.messon.faculty.app.presentation.ui.components
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.faculty.app.domain.dataModel.MessCreateRes
import com.messon.faculty.app.theme.CustomColors
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansmedium
import messon_faculty.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun MessClosureCard(mess: MessCreateRes,onCardClick:()-> Unit) {
    val isOpen = mess.isActive

    Card(
        onClick = { onCardClick()},
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 8.dp),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (icon, info, footer) = createRefs()
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (isOpen) CustomColors.HoneyBronze.copy(alpha =0.1f) else Color.LightGray.copy(0.2f))
                    .constrainAs(icon) {
                        top.linkTo(parent.top, 24.dp)
                        start.linkTo(parent.start, 24.dp)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isOpen) Icons.Default.LockOpen else Icons.Default.Lock,
                    contentDescription = null,
                    tint = if (isOpen) CustomColors.HoneyBronze else Color.LightGray,
                    modifier = Modifier.size(24.dp)
                )
            }
            Column(
                modifier = Modifier.constrainAs(info) {
                    top.linkTo(icon.top)
                    start.linkTo(icon.end, 16.dp)
                    end.linkTo(parent.end, 24.dp)
                    width = Dimension.fillToConstraints
                }
            ) {
                Text(
                    text = mess.name,
                    fontFamily = FontFamily(Font((Res.font.publicsanssemibold))),
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                    Icon(Icons.Default.Place, contentDescription = null, tint = Color.DarkGray, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = mess.location,
                        fontFamily = FontFamily(Font((Res.font.publicsansmedium))),
                        fontSize = 12.sp,
                        color = Color.DarkGray
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical =10.dp)
                    .constrainAs(footer) {
                        top.linkTo(icon.bottom, 24.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.background(if(isOpen) CustomColors.HoneyBronze.copy(0.1f) else Color.LightGray.copy(0.2f),RoundedCornerShape(20.dp))
                        .height(35.dp)
                        .width(65.dp)
                ){
                    Text(
                        text = if (isOpen) "Open" else "Closed",
                        fontFamily = FontFamily(Font((Res.font.publicsanssemibold))),
                        fontSize = 10.sp,
                        color = if(isOpen) CustomColors.HoneyBronze else Color.LightGray
                    )
                }
                }
            }
        }
    }
