package com.application.chess.ui.mapper

import com.application.chess.data.BoardUiStateEntity
import com.application.chess.ui.board.model.BoardUiState

fun BoardUiStateEntity.toUiState(): BoardUiState {
    return BoardUiState(
        numberOfRows = numberOfRows,
        startingCell = if (startRow != null && startCol != null) Pair(startRow, startCol) else null,
        finalCell = if (lastRow != null && lastCol != null) Pair(lastRow, lastCol) else null,
        position = if (positionRow != null && positionCol != null) Pair(positionRow, positionCol) else null,
        description = description,
        buttonsEnabled = buttonsEnabled,
        successPaths = successPaths
    )
}