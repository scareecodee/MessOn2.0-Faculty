package com.messon.faculty.app.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.faculty.app.domain.dataModel.Manager
import com.messon.faculty.app.theme.CustomColors
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansmedium
import messon_faculty.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun ManagerCard(
    manager: Manager,
    onDetailClick:()-> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(24.dp))
            .clickable{
                onDetailClick()
            },
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            val (avatar, info, action) = createRefs()

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(CustomColors.LightLavender, RoundedCornerShape(16.dp))
                    .constrainAs(avatar) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = manager.name.take(1),
                    fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                    fontSize = 12.sp,
                    color = CustomColors.BlackberryCream
                )
            }

            Column(
                modifier = Modifier.constrainAs(info) {
                    start.linkTo(avatar.end, margin = 10.dp)
                    top.linkTo(avatar.top)
                    bottom.linkTo(avatar.bottom)
                    end.linkTo(action.start, margin = 8.dp)
                    width = Dimension.fillToConstraints
                }.padding(horizontal = 7.dp)
            ) {
                Text(
                    text = manager.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                    color = CustomColors.BlackberryCream
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Email, contentDescription = null, tint = CustomColors.LightGrayBlue)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = manager.email,
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Gray,
                        fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                    )
                }
            }
                Icon(
                    Icons.Default.ChevronRight,
                    contentDescription = "detail",
                    modifier = Modifier.size(24.dp)
                        .constrainAs(action) {
                            end.linkTo(parent.end, margin = 10.dp)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                    ,
                    tint = CustomColors.RichCerulean
                )
            }
        }
    }

