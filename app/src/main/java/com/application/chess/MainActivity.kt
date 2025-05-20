package com.application.chess

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.application.chess.ui.board.BoardViewModel
import com.application.chess.ui.board.composables.ChessBoardScreen
import com.application.chess.ui.theme.ChessTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: BoardViewModel = hiltViewModel()
            viewModel.initBoardScreen()
            ChessTheme {
                ChessBoardScreen(viewModel = viewModel)
            }
        }
    }
}