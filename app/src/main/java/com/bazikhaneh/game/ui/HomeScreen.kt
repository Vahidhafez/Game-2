package com.bazikhaneh.game.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bazikhaneh.game.core.GameInfo

@Composable
fun HomeScreen(games: List<GameInfo>, onPick: (GameInfo) -> Unit) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("بازی‌خانه", fontSize = 34.sp, fontWeight = FontWeight.Bold)
        Text("بازی‌های کوچک، بدون اینترنت", fontSize = 15.sp)
        Spacer(Modifier.height(14.dp))
        Button(onClick = { onPick(games.random()) }, modifier = Modifier.fillMaxWidth()) {
            Text("🎲 انتخاب تصادفی")
        }
        Spacer(Modifier.height(14.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(games) { game ->
                Card(Modifier.fillMaxWidth().clickable { onPick(game) }) {
                    Column(Modifier.padding(16.dp)) {
                        Text(game.emoji, fontSize = 40.sp)
                        Text(game.title, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                        Text(game.subtitle, fontSize = 13.sp)
                        Text(game.players, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
