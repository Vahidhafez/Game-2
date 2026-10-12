package com.bazikhaneh.game.core

import androidx.compose.runtime.Composable

/** Every game is described by one GameInfo. To add a game: create it and list it in GameRegistry. */
data class GameInfo(
    val id: String,
    val title: String,
    val emoji: String,
    val subtitle: String,
    val players: String,
    val content: @Composable (Records) -> Unit,
)
