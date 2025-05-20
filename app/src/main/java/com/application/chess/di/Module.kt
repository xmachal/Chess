package com.application.chess.di

import android.content.Context
import androidx.room.Room
import com.application.chess.data.AppDatabase
import com.application.chess.data.BoardDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "chess_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBoardDao(database: AppDatabase): BoardDao {
        return database.boardDao()
    }
}