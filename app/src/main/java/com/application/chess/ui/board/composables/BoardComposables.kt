package com.application.chess.ui.board.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.application.chess.R
import com.application.chess.ui.board.BoardViewModel


@Composable
fun ChessBoardScreen(
    viewModel: BoardViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val numberOfRows = uiState.value.numberOfRows
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp, bottom = 24.dp),
            text = "Board App"
        )
        SelectNumberOfRow(onSetNumber = viewModel::setNumberOfRows, enable = uiState.value.buttonsEnabled)
        Board(
            numberOfRows = numberOfRows,
            position = uiState.value.position,
            start = uiState.value.startingCell,
            last = uiState.value.finalCell,
            enable = uiState.value.buttonsEnabled,
            onCellClick = if (uiState.value.startingCell == null) viewModel::onSetStartPosition else viewModel::onLastPositionClick
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(top = 24.dp, bottom = 24.dp),
            text = uiState.value.description,
            textAlign = TextAlign.Center
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(modifier = Modifier.padding(end = 16.dp), onClick = viewModel::onFindClick) {
                Text(text = "Find Solutions")
            }
            Button(onClick = viewModel::resetBoard) {
                Text(text = "Reset")
            }
        }
        SelectSuccessResult(
            list = uiState.value.successPaths,
            showPath = viewModel::showPath,
            enableButtons = uiState.value.buttonsEnabled
        )
    }
}


@Composable
fun Board(
    numberOfRows: Int,
    position: Pair<Int, Int>?,
    start: Pair<Int, Int>?,
    last: Pair<Int, Int>?,
    enable: Boolean = false,
    onCellClick: (Pair<Int, Int>) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    Column(modifier = Modifier.fillMaxWidth()) {
        for (row in 1 until numberOfRows + 1) {
            Row(Modifier.fillMaxWidth()) {
                for (col in 1 until numberOfRows + 1) {
                    CellsContent(
                        size = (screenWidth / numberOfRows).dp,
                        darkColor = (row + col) % 2 == 1,
                        isFirst = start == Pair(row, col),
                        isLast = last == Pair(row, col),
                        isPosition = position == Pair(row, col),
                        enable = enable,
                        col = col,
                        row = row,
                        onCellClick = { onCellClick(Pair(row, col)) }

                    )
                }
            }
        }
    }
}

@Composable
fun SelectSuccessResult(
    list: List<List<Pair<Int, Int>>>,
    enableButtons: Boolean,
    showPath: (Int) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(end = 6.dp)
                .clickable { expanded.value = true },
            text =
            if (enableButtons) {
                ""
            } else if (list.isEmpty()) {
                "Δεν υπάρχει λύση"
            } else {
                "Επίλεξε Λύση : "
            }
        )

        if (list.isNotEmpty()) {
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = !expanded.value },
                content = {
                    Column {
                        repeat(list.size) { i ->
                            DropdownMenuItem(
                                text = { ResultText(num = i + 1) },
                                onClick = {
                                    showPath(i)
                                    expanded.value = false
                                }
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun SelectNumberOfRow(
    onSetNumber: (number: Int) -> Unit,
    enable: Boolean
) {
    val expanded = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(end = 6.dp)
                .clickable(enabled = enable) { expanded.value = true }, text = "Επίλεξε Board : "
        )
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = !expanded.value },
            content = {
                Column {
                    for (i in 6 until 17) {
                        DropdownMenuItem(
                            text = { NumberText(num = i) },
                            onClick = {
                                onSetNumber(i)
                                expanded.value = false
                            }
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun CellsContent(
    col: Int,
    row: Int,
    size: Dp,
    darkColor: Boolean,
    isFirst: Boolean = false,
    isLast: Boolean = false,
    isPosition: Boolean = false,
    enable: Boolean = false,
    onCellClick: (Pair<Int, Int>) -> Unit
) {
    Box(
        modifier = Modifier
            .size(size)
            .background(color = if (darkColor) Color.Black else Color.White)
    ) {
        Button(
            modifier = Modifier.fillMaxSize(),
            colors = ButtonDefaults.buttonColors(containerColor = if (darkColor) Color.Black else Color.White),
            enabled = enable,
            onClick = { onCellClick(Pair(row, col)) },
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(0.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterVertically), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isFirst || isLast) {
                    Box(
                        modifier = Modifier
                            .padding(6.dp)
                            .fillMaxSize(0.2f)
                            .clip(shape = CircleShape)
                            .background(color = if (isFirst) Color.Green else Color.Red)

                    )
                }
                if (isPosition) {
                    Image(
                        modifier = Modifier
                            .padding(6.dp)
                            .size(24.dp),
                        painter = painterResource(R.drawable.chess_24),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
fun NumberText(num: Int) {
    Text("$num x $num")
}

@Composable
fun ResultText(num: Int) {
    Text("Λύση $num")
}