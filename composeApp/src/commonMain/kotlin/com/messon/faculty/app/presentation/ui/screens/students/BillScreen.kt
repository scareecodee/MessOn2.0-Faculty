package com.messon.faculty.app.presentation.ui.screens.students
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.faculty.app.theme.CustomColors

/**
 * Main Composable for the Student Bill Screen
 */
@Composable
fun StudentBillScreen(
    studentName: String,
    studentId: String,
    branch: String,
    billAmount: String,
    fromDate: String,
    toDate: String,
    onBackClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        val (header, contentCard, footerIndicator) = createRefs()

        // 1. Light Blue Header with Amount
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(CustomColors.LightBlue)
                .padding(top = 48.dp, bottom = 64.dp)
                .constrainAs(header) {
                    top.linkTo(parent.top)
                }
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.background(Color.White.copy(alpha = 0.5f), CircleShape)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = CustomColors.RichCerulean)
                    }
                    Text(
                        "BILL DETAILS",
                        style = MaterialTheme.typography.labelLarge,
                        color = CustomColors.RichCerulean,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                    Spacer(modifier = Modifier.size(48.dp)) // Symmetry spacer
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    "TOTAL AMOUNT",
                    style = MaterialTheme.typography.labelSmall,
                    color = CustomColors.BlackberryCream.copy(alpha = 0.5f),
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.5.sp
                )
                Text(
                    billAmount,
                    style = MaterialTheme.typography.displayMedium,
                    color = CustomColors.RichCerulean,
                    fontWeight = FontWeight.Black
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .background(Color.White)
                .padding(24.dp)
                .constrainAs(contentCard) {
                    top.linkTo(header.bottom, margin = (-30).dp)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            SectionTitle("STUDENT INFORMATION")
            StudentInfoCard(
                name = studentName,
                id = studentId,
                branch = branch
            )

            Spacer(modifier = Modifier.height(32.dp))

            SectionTitle("BILLING PERIOD")
            DurationCard(
                from = fromDate,
                to = toDate
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Footer Note
            InfoNote(
                text = "This bill represents the consolidated fees for the selected academic duration. Please contact the accounts department for further queries."
            )

            Spacer(modifier = Modifier.height(40.dp))
        }

        // 3. Bottom Indicator
        Box(
            modifier = Modifier
                .width(60.dp)
                .height(4.dp)
                .background(CustomColors.LightGrayBlue.copy(alpha = 0.3f), CircleShape)
                .constrainAs(footerIndicator) {
                    bottom.linkTo(parent.bottom, 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}

/**
 * Reusable Section Title
 */
@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelSmall,
        color = CustomColors.BlackberryCream.copy(alpha = 0.4f),
        fontWeight = FontWeight.Black,
        letterSpacing = 1.sp,
        modifier = Modifier.padding(start = 8.dp, bottom = 12.dp)
    )
}

/**
 * Reusable Card for Student Info
 */
@Composable
fun StudentInfoCard(name: String, id: String, branch: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(CustomColors.OffWhite, RoundedCornerShape(24.dp))
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.White, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = null, tint = CustomColors.RichCerulean)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("FULL NAME", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                Text(name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp), color = Color.LightGray.copy(alpha = 0.3f))

        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                Text("STUDENT ID", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                Text(id, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text("BRANCH", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                Text(branch, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, maxLines = 1)
            }
        }
    }
}

/**
 * Reusable Card for Duration/Billing Period
 */
@Composable
fun DurationCard(from: String, to: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(CustomColors.Cream, RoundedCornerShape(24.dp))
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.White, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.DateRange, contentDescription = null, tint = CustomColors.HoneyBronze)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("DURATION", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                Text("Academic Cycle", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White.copy(alpha = 0.7f), RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("FROM", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = Color.Gray)
                Text(from, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
            }
            Box(modifier = Modifier.width(1.dp).height(24.dp).background(Color.LightGray))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("TO", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = Color.Gray)
                Text(to, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
            }
        }
    }
}

/**
 * Reusable Info/Disclaimer Note
 */
@Composable
fun InfoNote(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(CustomColors.LightBlue.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            Icons.Default.Info,
            contentDescription = null,
            tint = CustomColors.RichCerulean,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = CustomColors.RichCerulean,
            lineHeight = 18.sp
        )
    }
}