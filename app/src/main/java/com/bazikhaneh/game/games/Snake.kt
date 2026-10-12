package com.bazikhaneh.game.games

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bazikhaneh.game.core.GameInfo
import com.bazikhaneh.game.core.Records
import com.bazikhaneh.game.core.fa
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.random.Random

val SnakeGame = GameInfo(
    id = "snake",
    title = "مار",
    emoji = "🐍",
    subtitle = "غذا بخور و بزرگ شو",
    players = "۱ نفره، رکوردی",
) { SnakeScreen(it) }

private const val N = 16
private data class P(val x: Int, val y: Int)

private fun randomFood(s: List<P>): P {
    while (true) {
        val p = P(Random.nextInt(N), Random.nextInt(N))
        if (p !in s) return p
    }
}

@Composable
fun SnakeScreen(records: Records) {
    var snake by remember { mutableStateOf(listOf(P(8, 8), P(7, 8), P(6, 8))) }
    var dir by remember { mutableStateOf(P(1, 0)) }
    var nextDir by remember { mutableStateOf(P(1, 0)) }
    var food by remember { mutableStateOf(randomFood(snake)) }
    var score by remember { mutableIntStateOf(0) }
    var alive by remember { mutableStateOf(true) }
    var newRecord by remember { mutableStateOf(false) }
    var round by remember { mutableIntStateOf(0) }

    fun turn(d: P) {
        if (d.x != -dir.x || d.y != -dir.y) nextDir = d
    }

    fun restart() {
        snake = listOf(P(8, 8), P(7, 8), P(6, 8))
        dir = P(1, 0); nextDir = P(1, 0)
        food = randomFood(snake)
        score = 0; alive = true; newRecord = false
        round++
    }

    LaunchedEffect(round) {
        while (alive) {
            delay((190L - score * 4L).coerceAtLeast(80L))
            val d = nextDir
            dir = d
            val head = snake.first()
            val nh = P(head.x + d.x, head.y + d.y)
            if (nh.x !in 0 until N || nh.y !in 0 until N || nh in snake.dropLast(1)) {
                alive = false
                newRecord = records.submit("snake", score)
            } else {
                val eat = nh == food
                snake = listOf(nh) + if (eat) snake else snake.dropLast(1)
                if (eat) {
                    score++
                    food = randomFood(snake)
                }
            }
        }
    }

    Column(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            "امتیاز: ${score.fa()}      رکورد: ${records.best("snake").fa()}",
            fontSize = 18.sp,
        )
        Spacer(Modifier.height(10.dp))
        Canvas(
            Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .pointerInput(Unit) {
                    detectDragGestures { _, drag ->
                        if (abs(drag.x) > abs(drag.y)) turn(P(if (drag.x > 0) 1 else -1, 0))
                        else turn(P(0, if (drag.y > 0) 1 else -1))
                    }
                },
        ) {
            val cell = size.width / N
            drawRoundRect(Color(0xFF1E2B2F), size = size, cornerRadius = CornerRadius(24f))
            drawCircle(Color(0xFFFF6B5E), cell * 0.38f, Offset((food.x + .5f) * cell, (food.y + .5f) * cell))
            snake.forEachIndexed { i, p ->
                drawRoundRect(
                    if (i == 0) Color(0xFFFFD166) else Color(0xFF6BCB77),
                    topLeft = Offset(p.x * cell + 1.5f, p.y * cell + 1.5f),
                    size = Size(cell - 3f, cell - 3f),
                    cornerRadius = CornerRadius(cell * 0.3f),
                )
            }
        }
        Spacer(Modifier.height(14.dp))
        if (alive) {
            Text("برای تغییر جهت روی صفحه بکش یا از دکمه‌ها استفاده کن", fontSize = 13.sp)
            Spacer(Modifier.height(8.dp))
            Button(onClick = { turn(P(0, -1)) }) { Text("▲") }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = { turn(P(-1, 0)) }) { Text("◀") }
                Button(onClick = { turn(P(0, 1)) }) { Text("▼") }
                Button(onClick = { turn(P(1, 0)) }) { Text("▶") }
            }
        } else {
            Text(
                if (newRecord) "رکورد جدید! 🎉" else "باختی",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.height(8.dp))
            Button(onClick = { restart() }) { Text("بازی دوباره") }
        }
    }
}
