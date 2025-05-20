package com.application.chess.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BoardDao {
    @Query("SELECT * FROM board_ui_state WHERE id = 1")
    suspend fun getUiState(): BoardUiStateEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUiState(state: BoardUiStateEntity)
}