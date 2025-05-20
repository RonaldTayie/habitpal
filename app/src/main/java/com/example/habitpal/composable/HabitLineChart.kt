package com.example.habitpal.composable

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.example.habitpal.domain.models.HabitLog
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HabitLineChart(logs: List<HabitLog>) {
    val dates = logs.map { it.date }.sorted()
    val minDate = dates.minOrNull() ?: return
    val maxDate = dates.maxOrNull() ?: return

    val points = mutableListOf<Offset>()
    val daysBetween = ChronoUnit.DAYS.between(minDate, maxDate).toInt().coerceAtLeast(1)

    for (i in 0..daysBetween) {
        val day = minDate.plusDays(i.toLong())
        val y = if (logs.any { it.date == day }) 1f else 0f
        points.add(Offset(i.toFloat(), y))
    }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 2.dp, vertical = 4.dp)
    ) {
        val xScale = size.width / (points.size - 1).coerceAtLeast(1)
        val yScale = size.height

        // Grid lines
        drawLine(
            color = Color.LightGray.copy(alpha = 0.4f),
            start = Offset(0f, yScale),
            end = Offset(size.width, yScale),
            strokeWidth = 2f
        )
        drawLine(
            color = Color.LightGray.copy(alpha = 0.4f),
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = 2f
        )

        // Line path
        for (i in 1 until points.size) {
            val p1 = Offset(points[i - 1].x * xScale, yScale - points[i - 1].y * yScale)
            val p2 = Offset(points[i].x * xScale, yScale - points[i].y * yScale)

            drawLine(
                color = Color(0xFF1976D2),
                start = p1,
                end = p2,
                strokeWidth = 6f,
                cap = StrokeCap.Round
            )
        }

        // Data points
        points.forEach {
            val x = it.x * xScale
            val y = yScale - it.y * yScale
            drawCircle(
                color = Color(0xFF2196F3),
                radius = 6f,
                center = Offset(x, y)
            )
        }

        // X-axis line
        drawLine(
            color = Color.DarkGray,
            start = Offset(0f, yScale),
            end = Offset(size.width, yScale),
            strokeWidth = 3f
        )
    }
}
