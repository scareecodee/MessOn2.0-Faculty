package com.messon.faculty.app.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.faculty.app.domain.dataModel.StudentBillingData
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansbold
import messon_faculty.composeapp.generated.resources.publicsansmedium
import messon_faculty.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun StudentBillCard(
    student: StudentBillingData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Column(
                modifier = Modifier
                    .weight(0.35f)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.primary.copy(0.1f))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = student.name.take(1),
                        color = Color.White,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = student.name,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp,
                    maxLines =2,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
                        .border(0.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "BATCH ${student.batch}",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 10.sp,
                        fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                    )
                }
            }

            ConstraintLayout(
                modifier = Modifier
                    .weight(0.65f)
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                val (rollNoRef, branchRef, cycleRef, dividerRef, billRef, periodsRef) = createRefs()

                InfoBlock(
                    icon = Icons.Default.Person,
                    label = "ROLL NO",
                    value = student.rollNo,
                    modifier = Modifier.constrainAs(rollNoRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(branchRef.start, margin = 8.dp)
                        width = Dimension.fillToConstraints
                    }
                )

                InfoBlock(
                    icon = Icons.Default.Info,
                    label = "BRANCH",
                    value = student.branch,
                    modifier = Modifier.constrainAs(branchRef) {
                        top.linkTo(parent.top)
                        start.linkTo(rollNoRef.end)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                )

                InfoBlock(
                    icon = Icons.Default.DateRange,
                    label = "BILLING CYCLE",
                    value = "${student.startDate} — ${student.endDate}",
                    modifier = Modifier.constrainAs(cycleRef) {
                        top.linkTo(rollNoRef.bottom, margin = 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                )

                Box(
                    modifier = Modifier
                        .height(1.dp)
                        .background(MaterialTheme.colorScheme.outlineVariant)
                        .constrainAs(dividerRef) {
                            top.linkTo(cycleRef.bottom, margin = 16.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                )

                Column(
                    modifier = Modifier.constrainAs(billRef) {
                        top.linkTo(dividerRef.bottom, margin = 12.dp)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
                ) {
                    Text(
                        text = "TOTAL BILL AMOUNT",
                        fontSize = 10.sp,
                        fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = "₹${student.totalBill}",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                Text(
                    text = "${student.totalBillingPeriods} Periods",
                    fontSize = 10.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.constrainAs(periodsRef) {
                        bottom.linkTo(billRef.bottom, margin = 4.dp)
                        end.linkTo(parent.end)
                    }
                )
            }
        }
    }
}


@Composable
fun InfoBlock(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.Top) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp).padding(top = 2.dp),
            tint = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.width(6.dp))
        Column {
            Text(
                text = label,
                fontSize = 10.sp,
                fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 0.5.sp
            )
            Text(
                text = value,
                fontSize = 10.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}