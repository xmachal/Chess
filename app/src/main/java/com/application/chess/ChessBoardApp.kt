package com.application.chess

import android.app.Application
import androidx.room.Room
import com.application.chess.data.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ChessBoardApp : Application() {
    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "chessboard_db"
        ).build()
    }
}