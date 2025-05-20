package com.application.chess.ui.board.model

data class CellsUiModel(
    val name: String,
    val selected: Boolean
)

data class BoardUiState(
    val numberOfRows: Int,
    val start: Pair<Int, Int>?,
    val last: Pair<Int, Int>?,
    val position: Pair<Int, Int>?,
    val description: String,
    val buttonsEnabled: Boolean = true,
    val successPaths: List<List<Pair<Int,Int>>>
)