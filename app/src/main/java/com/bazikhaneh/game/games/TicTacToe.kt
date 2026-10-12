package com.bazikhaneh.game.games

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bazikhaneh.game.core.GameInfo
import com.bazikhaneh.game.core.fa

val TicTacToeGame = GameInfo(
    id = "tictactoe",
    title = "دوز",
    emoji = "❌",
    subtitle = "دونفره روی یک گوشی",
    players = "۲ نفره",
) { TicTacToeScreen() }

private val lines = listOf(
    listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8),
    listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8),
    listOf(0, 4, 8), listOf(2, 4, 6),
)

@Composable
fun TicTacToeScreen() {
    val board = remember { mutableStateListOf("", "", "", "", "", "", "", "", "") }
    var turn by remember { mutableStateOf("X") }
    var winner by remember { mutableStateOf<String?>(null) }
    var xWins by remember { mutableIntStateOf(0) }
    var oWins by remember { mutableIntStateOf(0) }
    val full = board.none { it.isEmpty() }

    fun reset() {
        for (i in 0..8) board[i] = ""
        turn = "X"
        winner = null
    }

    fun play(i: Int) {
        if (winner != null || board[i].isNotEmpty()) return
        board[i] = turn
        if (lines.any { l -> l.all { board[it] == turn } }) {
            winner = turn
            if (turn == "X") xWins++ else oWins++
        } else {
            turn = if (turn == "X") "O" else "X"
        }
    }

    val status = when {
        winner != null -> "برنده: بازیکن ${if (winner == "X") "X" else "O"} 🎉"
        full -> "مساوی شد"
        else -> "نوبت بازیکن $turn"
    }

    Column(
        Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("X: ${xWins.fa()}      O: ${oWins.fa()}", fontSize = 18.sp)
        Spacer(Modifier.height(10.dp))
        Text(status, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(20.dp))
        for (r in 0..2) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                for (c in 0..2) {
                    val i = r * 3 + c
                    Box(
                        Modifier
                            .size(92.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .clickable { play(i) },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(board[i], fontSize = 44.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
        }
        Spacer(Modifier.height(12.dp))
        Button(onClick = { reset() }) { Text("دور جدید") }
    }
}
