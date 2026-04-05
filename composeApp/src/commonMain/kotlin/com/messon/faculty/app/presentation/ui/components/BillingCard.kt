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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.faculty.app.domain.dataModel.StudentBillingData
import com.messon.faculty.app.theme.CustomColors

@Composable
fun StudentBillCard(
    student: StudentBillingData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
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
                    .background(CustomColors.LightBlue)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Profile Avatar Circle
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(CustomColors.RichCerulean),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = student.name.take(1),
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = student.name,
                    color = Color(0xFF0F172A), // Slate 900
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Batch Tag
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White.copy(alpha = 0.8f))
                        .border(0.5.dp, CustomColors.RichCerulean.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "BATCH ${student.batch}",
                        color = CustomColors.RichCerulean,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            // --- RIGHT DATA SECTION (Constraint Layout) ---
            ConstraintLayout(
                modifier = Modifier
                    .weight(0.65f)
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                // Create References
                val (rollNoRef, branchRef, cycleRef, dividerRef, billRef, periodsRef) = createRefs()

                // Roll No Block
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

                // Branch Block
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

                // Billing Cycle Block
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

                // Bottom Divider
                Box(
                    modifier = Modifier
                        .height(1.dp)
                        .background(Color(0xFFF1F5F9))
                        .constrainAs(dividerRef) {
                            top.linkTo(cycleRef.bottom, margin = 16.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                )

                // Bill Amount
                Column(
                    modifier = Modifier.constrainAs(billRef) {
                        top.linkTo(dividerRef.bottom, margin = 12.dp)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
                ) {
                    Text(
                        text = "TOTAL BILL AMOUNT",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "₹${student.totalBill}",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        color = CustomColors.HoneyBronze
                    )
                }

                // Billing Periods
                Text(
                    text = "${student.totalBillingPeriods} Periods",
                    fontSize = 10.sp,
                    color = Color.LightGray,
                    modifier = Modifier.constrainAs(periodsRef) {
                        bottom.linkTo(billRef.bottom, margin = 4.dp)
                        end.linkTo(parent.end)
                    }
                )
            }
        }
    }
}

/**
 * Reusable Row for Roll No, Branch, and Cycle info
 */
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
            modifier = Modifier.size(14.dp).padding(top = 2.dp),
            tint = Color.LightGray
        )
        Spacer(modifier = Modifier.width(6.dp))
        Column {
            Text(
                text = label,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                letterSpacing = 0.5.sp
            )
            Text(
                text = value,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF334155),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}