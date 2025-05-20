package com.application.chess.mapper

import com.application.chess.data.BoardUiStateEntity
import com.application.chess.ui.board.model.BoardUiState

fun BoardUiState.toEntity(): BoardUiStateEntity {
    return BoardUiStateEntity(
        numberOfRows = numberOfRows,
        startRow = startingCell?.first,
        startCol = startingCell?.second,
        lastRow = finalCell?.first,
        lastCol = finalCell?.second,
        positionRow = position?.first,
        positionCol = position?.second,
        description = description,
        buttonsEnabled = buttonsEnabled,
        successPaths = successPaths
    )
}