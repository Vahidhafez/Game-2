package com.bazikhaneh.game.core

import android.content.Context

/** Offline high scores, one number per game id. */
class Records(context: Context) {
    private val prefs = context.getSharedPreferences("records", Context.MODE_PRIVATE)

    fun best(id: String): Int = prefs.getInt(id, 0)

    /** Saves the score if it beats the old one. Returns true for a new record. */
    fun submit(id: String, score: Int): Boolean {
        if (score <= best(id)) return false
        prefs.edit().putInt(id, score).apply()
        return true
    }
}

fun Int.fa(): String = toString().map { if (it in '0'..'9') '۰' + (it - '0') else it }.joinToString("")
