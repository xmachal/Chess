package com.application.chess.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "board_ui_state")
data class BoardUiStateEntity(
    @PrimaryKey val id: Int = 1,
    val numberOfRows: Int,
    val startRow: Int?,
    val startCol: Int?,
    val lastRow: Int?,
    val lastCol: Int?,
    val positionRow: Int?,
    val positionCol: Int?,
    val description: String,
    val buttonsEnabled: Boolean,
    val successPaths: List<List<Pair<Int, Int>>>
)