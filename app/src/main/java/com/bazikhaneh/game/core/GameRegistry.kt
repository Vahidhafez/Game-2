package com.bazikhaneh.game.core

import com.bazikhaneh.game.games.SnakeGame
import com.bazikhaneh.game.games.TicTacToeGame

object GameRegistry {
    val games: List<GameInfo> = listOf(
        SnakeGame,
        TicTacToeGame,
    )
}
