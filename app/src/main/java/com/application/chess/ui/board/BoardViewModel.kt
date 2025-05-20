package com.application.chess.ui.board

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.chess.data.BoardDao
import com.application.chess.mapper.toEntity
import com.application.chess.ui.board.model.BoardUiState
import com.application.chess.ui.mapper.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val boardDao: BoardDao
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        BoardUiState(
            numberOfRows = 6,
            startingCell = null,
            finalCell = null,
            position = null,
            description = "Description",
            buttonsEnabled = true,
            successPaths = emptyList()
        )
    )
    val uiState: MutableStateFlow<BoardUiState> = _uiState
    private val result: MutableList<List<Pair<Int, Int>>> = mutableListOf()

    fun initBoardScreen() {
        viewModelScope.launch {
            val entity = boardDao.getUiState()
            if (entity != null) {
                _uiState.value = entity.toUiState()
            } else {
                _uiState.update { it.copy(description = "Select a number") }
            }
        }
    }

    fun resetBoard() {
        uiState.update {
            it.copy(
                startingCell = null,
                finalCell = null,
                buttonsEnabled = true,
                position = null,
                description = "Select First Cell",
                successPaths = emptyList()
            )
        }
    }

    private fun clearBoard() {
        uiState.update { it.copy(position = it.startingCell ?: Pair(12, 12), buttonsEnabled = false, description = "Λύση") }
    }

    fun showPath(i: Int) {
        viewModelScope.launch {
            clearBoard()

            _uiState.value.successPaths[i].forEach { thesi ->
                delay(400)
                _uiState.update { it.copy(position = thesi) }
            }
        }
    }

    fun onSetStartPosition(position: Pair<Int, Int>) {
        if (_uiState.value.startingCell == null) {
            _uiState.update { it.copy(startingCell = position, position = position, description = "Select the finalCell") }
        }
    }

    fun onLastPositionClick(last: Pair<Int, Int>) {
        if (_uiState.value.finalCell == null) {
            _uiState.update { it.copy(finalCell = last, description = "Lets go", buttonsEnabled = false) }
        }
    }

    fun setNumberOfRows(number: Int) {
        _uiState.update { it.copy(numberOfRows = number) }
    }

    private fun possibleMoves(position: Pair<Int, Int>): List<Pair<Int, Int>> {
        return possibleMoves.mapNotNull { (row, col) ->
            val newPositionRow = position.first + row
            val newPositionColumn = position.second + col
            if (newPositionColumn in 1 until _uiState.value.numberOfRows && newPositionColumn in 1 until _uiState.value.numberOfRows) {
                Pair(newPositionRow, newPositionColumn)
            } else {
                null
            }
        }
    }

    fun onFindClick() {
        result.removeAll { true }
        _uiState.update { it.copy(successPaths = listOf()) }
        findPaths(currentPosition = _uiState.value.position, path = listOf(), 0)
        uiState.update { it.copy(successPaths = result) }
        saveToRoomDatabase(state = _uiState.value)
    }

    private fun findPaths(currentPosition: Pair<Int, Int>?, path: List<Pair<Int, Int>>, moves: Int) {
        if (moves > 4) return
        if (currentPosition == _uiState.value.finalCell && moves < 4) {
            if (!result.contains(path)) {
                result.add(path)
                return
            }
        }
        for (i in possibleMoves(currentPosition ?: Pair(0, 0))) {
            findPaths(i, path + i, moves + 1)
        }
    }

    private fun saveToRoomDatabase(state: BoardUiState) {
        _uiState.value = state
        viewModelScope.launch {
            boardDao.saveUiState(state.toEntity())
        }
    }

    private val possibleMoves = listOf(
        Pair(-2, -1),
        Pair(-2, 1),
        Pair(-1, -2),
        Pair(-1, 2),
        Pair(1, -2),
        Pair(1, 2),
        Pair(2, -1),
        Pair(2, 1)
    )
}