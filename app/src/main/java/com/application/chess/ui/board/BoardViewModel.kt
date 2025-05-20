package com.application.chess.ui.board

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.chess.ui.board.model.BoardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(
        BoardUiState(
            numberOfRows = 6,
            start = null,
            last = null,
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
            _uiState.update { it.copy(description = "Select a number") }
        }
    }

    fun resetBoard() {
        uiState.update {
            it.copy(
                start = null,
                last = null,
                buttonsEnabled = true,
                position = null,
                description = "Select First Cell",
                successPaths = emptyList()
            )
        }
    }

    private fun clearBoard() {
        uiState.update { it.copy(position = it.start ?: Pair(12, 12), buttonsEnabled = false, description = "Λύση") }
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
        if (_uiState.value.start == null) {
            _uiState.update { it.copy(start = position, position = position, description = "Select the last") }
        }
    }

    fun onLastPositionClick(last: Pair<Int, Int>) {
        if (_uiState.value.last == null) {
            _uiState.update { it.copy(last = last, description = "Lets go", buttonsEnabled = false) }
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

    fun onClick() {
        result.removeAll { true }
        _uiState.update { it.copy(successPaths = listOf()) }
        findPaths(currentPosition = _uiState.value.position, path = listOf(), 0)
        uiState.update { it.copy(successPaths = result) }
    }

    private fun findPaths(currentPosition: Pair<Int, Int>?, path: List<Pair<Int, Int>>, moves: Int) {
        if (moves > 3) return
        if (currentPosition == _uiState.value.last && moves < 3) {
            if (!result.contains(path)) {
                result.add(path)
                return
            }
        }
        for (a in possibleMoves(currentPosition ?: Pair(0, 0))) {
            findPaths(a, path + a, moves + 1)
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