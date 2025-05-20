package com.application.chess.ui.board.model

data class BoardUiState(
    val numberOfRows: Int,
    val startingCell: Pair<Int, Int>?,
    val finalCell: Pair<Int, Int>?,
    val position: Pair<Int, Int>?,
    val description: String,
    val buttonsEnabled: Boolean = true,
    val successPaths: List<List<Pair<Int,Int>>>
)