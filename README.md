###  ChessBoard App
The application displays a chessboard of size N x N (where 6 ≤ N ≤ 16) and allows the user to:

- Select a starting position (start)
- Select an ending position (end)
- Choose the maximum number of rows (default is 6)

Then, the application calculates all possible paths a knight can take to reach the end position from the start position in up to 3 moves.

Users can select the number of rows and columns for the chessboard using intuitive dropdown menus (6x6 up to 16x16).

A second dropdown allows users to  select a solution from the list of valid paths, the knight icon animates through each move step-by-step until it reaches the end position, offering a visual representation of the chosen path.



### Architecture Overview:

- MVVM (Model-View-ViewModel)
- Jetpack Compose – UI
- Room Database – Persistent storage 
- Hilt – Dependency Injection 
- Gson – JSON conversion for complex data 
- Coroutines – Background computation
