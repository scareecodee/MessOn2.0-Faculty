package com.messon.faculty.app.presentation.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.messon.faculty.app.utils.GaugeSegment
import kotlin.math.cos
import kotlin.math.sin


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp
import androidx.compose.ui.geometry.Offset
import kotlin.math.PI

@Composable
fun ExactCouponGauge(
    segments: List<GaugeSegment>,
    modifier: Modifier = Modifier,
    totalLabel: String = "coupons",
    improvementText: String = "Daily improvement",
    improvementValue: String = "+6%"
) {
    val totalValue = segments.sumOf { it.value.toDouble() }.toFloat()
    val sweepAngleTotal = 180f
    val gap = 4f
    val drawableAngle = sweepAngleTotal - (gap * (segments.size - 1))

    // Multiplatform text measurer for drawing text inside Canvas
    val textMeasurer = rememberTextMeasurer()

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(300.dp, 180.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val strokeWidth = 50f
                val radius = (size.width / 2) - strokeWidth
                var currentStartAngle = 180f

                segments.forEach { segment ->
                    val segmentSweep = (segment.value / totalValue) * drawableAngle

                    // 1. Draw the Arc
                    drawArc(
                        color = segment.color,
                        startAngle = currentStartAngle,
                        sweepAngle = segmentSweep,
                        useCenter = false,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )

                    // 2. Calculate coordinates for the number
                    val midAngle = currentStartAngle + (segmentSweep / 2)
                    val angleRad = midAngle * (PI.toFloat() / 180f)
                    // We calculate the center of the arc's stroke
                    val x = center.x + radius * cos(angleRad)
                    val y = center.y + radius * sin(angleRad)

                    // 3. Multiplatform Text Drawing
                    val textLayoutResult = textMeasurer.measure(
                        text = segment.value.toInt().toString(),
                        style = TextStyle(
                            color = segment.textColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    // Offset text to center it on the calculated (x,y)
                    drawText(
                        textLayoutResult = textLayoutResult,
                        topLeft = Offset(
                            x - (textLayoutResult.size.width / 2),
                            y - (textLayoutResult.size.height / 2)
                        )
                    )

                    currentStartAngle += segmentSweep + gap
                }
            }

            // Center Text
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.offset(y = 25.dp) // Adjusted for center alignment
            ) {
                Text(
                    text = totalValue.toInt().toString(),
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF222222)
                )
                Text(
                    text = totalLabel,
                    fontSize = 16.sp,
                    color = Color(0xFF4A90E2),
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // Improvement Pill
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color(0xFFF9F9F9),
            border = BorderStroke(1.dp, Color(0xFFEEEEEE)),
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(improvementText, color = Color.Gray, fontSize = 13.sp)
                Spacer(Modifier.width(4.dp))
                Text(improvementValue, color = Color(0xFF4CAF50), fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}