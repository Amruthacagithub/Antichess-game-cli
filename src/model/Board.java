// src/model/Board.java
package model;

public class Board {
    private Piece[][] board;

    public Board() {
        board = new Piece[8][8];
        initializeBoard();
    }

    /**
     * Initialize the board with pieces in their starting positions.
     */
    public void initializeBoard() {
        // Initialize Black Pieces
        board[0][0] = new Piece(Piece.PieceType.ROOK, Piece.Color.BLACK);
        board[0][1] = new Piece(Piece.PieceType.KNIGHT, Piece.Color.BLACK);
        board[0][2] = new Piece(Piece.PieceType.BISHOP, Piece.Color.BLACK);
        board[0][3] = new Piece(Piece.PieceType.QUEEN, Piece.Color.BLACK);
        board[0][4] = new Piece(Piece.PieceType.KING, Piece.Color.BLACK);
        board[0][5] = new Piece(Piece.PieceType.BISHOP, Piece.Color.BLACK);
        board[0][6] = new Piece(Piece.PieceType.KNIGHT, Piece.Color.BLACK);
        board[0][7] = new Piece(Piece.PieceType.ROOK, Piece.Color.BLACK);
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Piece(Piece.PieceType.PAWN, Piece.Color.BLACK);
        }

        // Initialize White Pieces
        board[7][0] = new Piece(Piece.PieceType.ROOK, Piece.Color.WHITE);
        board[7][1] = new Piece(Piece.PieceType.KNIGHT, Piece.Color.WHITE);
        board[7][2] = new Piece(Piece.PieceType.BISHOP, Piece.Color.WHITE);
        board[7][3] = new Piece(Piece.PieceType.QUEEN, Piece.Color.WHITE);
        board[7][4] = new Piece(Piece.PieceType.KING, Piece.Color.WHITE);
        board[7][5] = new Piece(Piece.PieceType.BISHOP, Piece.Color.WHITE);
        board[7][6] = new Piece(Piece.PieceType.KNIGHT, Piece.Color.WHITE);
        board[7][7] = new Piece(Piece.PieceType.ROOK, Piece.Color.WHITE);
        for (int i = 0; i < 8; i++) {
            board[6][i] = new Piece(Piece.PieceType.PAWN, Piece.Color.WHITE);
        }

        // Initialize Empty Squares
        for (int i = 2; i <= 5; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }
    }

    /**
     * Display the current state of the board in the CLI.
     */
    public void displayBoard() {
        System.out.println("  a b c d e f g h");
        for (int i = 0; i < 8; i++) {
            System.out.print(8 - i + " ");
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print(". ");
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println(8 - i);
        }
        System.out.println("  a b c d e f g h");
    }

    /**
     * Check if a square is empty.
     * @param row Row index
     * @param col Column index
     * @return true if empty, false otherwise
     */
    public boolean isEmpty(int row, int col) {
        return board[row][col] == null;
    }

    /**
     * Check if the piece at the specified location is an enemy piece.
     * @param row Row index
     * @param col Column index
     * @param currentPlayerColor Current player's color
     * @return true if enemy, false otherwise
     */
    public boolean isEnemy(int row, int col, Piece.Color currentPlayerColor) {
        if (board[row][col] == null) return false;
        return board[row][col].getColor() != currentPlayerColor;
    }

    /**
     * Validate if a move is legal based on Anti-Chess rules.
     * @param start Starting position as [row, col]
     * @param end Ending position as [row, col]
     * @param currentPlayerColor Current player's color
     * @return true if valid, false otherwise
     */
    public boolean isValidMove(int[] start, int[] end, Piece.Color currentPlayerColor) {
        int startRow = start[0];
        int startCol = start[1];
        int endRow = end[0];
        int endCol = end[1];

        Piece movingPiece = board[startRow][startCol];
        if (movingPiece == null) {
            System.out.println("No piece at the starting position.");
            return false;
        }

        if (movingPiece.getColor() != currentPlayerColor) {
            System.out.println("Cannot move opponent's piece.");
            return false;
        }

        // Get all valid moves for the piece
        var validMoves = movingPiece.getValidMoves(start, this);
        boolean isMoveValid = false;
        for (int[] move : validMoves) {
            if (move[0] == endRow && move[1] == endCol) {
                isMoveValid = true;
                break;
            }
        }

        if (!isMoveValid) {
            System.out.println("Invalid move for the selected piece.");
            return false;
        }

        // Anti-Chess Rule: If capture is possible, it must be taken
        if (isCapturePossible(currentPlayerColor)) {
            if (board[endRow][endCol] == null || board[endRow][endCol].getColor() == currentPlayerColor) {
                System.out.println("A capture move is available and must be taken.");
                return false;
            }
        }

        return true;
    }

    /**
     * Make the move on the board.
     * @param start Starting position as [row, col]
     * @param end Ending position as [row, col]
     */
    public void makeMove(int[] start, int[] end) {
        Piece movingPiece = board[start[0]][start[1]];
        board[end[0]][end[1]] = movingPiece;
        board[start[0]][start[1]] = null;
    }

    /**
     * Check if any capture is possible for the current player.
     * @param currentPlayerColor Current player's color
     * @return true if at least one capture is possible, false otherwise
     */
    public boolean isCapturePossible(Piece.Color currentPlayerColor) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece != null && piece.getColor() == currentPlayerColor) {
                    var moves = piece.getValidMoves(new int[]{i, j}, this);
                    for (int[] move : moves) {
                        if (board[move[0]][move[1]] != null && board[move[0]][move[1]].getColor() != currentPlayerColor) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check if the game is over (i.e., one player has no pieces left).
     * @return true if game over, false otherwise
     */
    public boolean isGameOver() {
        boolean whiteExists = false;
        boolean blackExists = false;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece != null) {
                    if (piece.getColor() == Piece.Color.WHITE) {
                        whiteExists = true;
                    } else {
                        blackExists = true;
                    }
                }
            }
        }

        return !whiteExists || !blackExists;
    }

    /**
     * Determine the winner based on remaining pieces.
     * @return The color of the winner
     */
    public Piece.Color determineWinner() {
        boolean whiteExists = false;
        boolean blackExists = false;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece != null) {
                    if (piece.getColor() == Piece.Color.WHITE) {
                        whiteExists = true;
                    } else {
                        blackExists = true;
                    }
                }
            }
        }

        if (whiteExists && !blackExists) {
            return Piece.Color.WHITE;
        } else if (blackExists && !whiteExists) {
            return Piece.Color.BLACK;
        } else {
            return null; // No winner yet or draw (not typical in Anti-Chess)
        }
    }
}
