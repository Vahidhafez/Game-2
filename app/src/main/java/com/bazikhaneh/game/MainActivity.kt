package com.bazikhaneh.game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bazikhaneh.game.core.GameInfo
import com.bazikhaneh.game.core.GameRegistry
import com.bazikhaneh.game.core.Records
import com.bazikhaneh.game.ui.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val records = Records(this)
        setContent {
            MaterialTheme {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    App(records)
                }
            }
        }
    }
}

@Composable
fun App(records: Records) {
    var current by remember { mutableStateOf<GameInfo?>(null) }
    BackHandler(enabled = current != null) { current = null }
    Surface(Modifier.fillMaxSize().systemBarsPadding()) {
        val game = current
        if (game == null) {
            HomeScreen(GameRegistry.games) { current = it }
        } else {
            Column(Modifier.fillMaxSize()) {
                Row(
                    Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                ) {
                    TextButton(onClick = { current = null }) { Text("بازگشت") }
                    Spacer(Modifier.width(8.dp))
                    Text("${game.emoji} ${game.title}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                Box(Modifier.weight(1f).fillMaxWidth()) { game.content(records) }
            }
        }
    }
}
