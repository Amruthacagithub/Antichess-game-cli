// src/model/Piece.java
package model;

import java.util.ArrayList;
import java.util.List;

public class Piece {
    public enum PieceType { PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING }
    public enum Color { WHITE, BLACK }

    private PieceType type;
    private Color color;

    public Piece(PieceType type, Color color) {
        this.type = type;
        this.color = color;
    }

    public PieceType getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        String symbol = "";
        switch (type) {
            case PAWN: symbol = "P"; break;
            case ROOK: symbol = "R"; break;
            case KNIGHT: symbol = "N"; break;
            case BISHOP: symbol = "B"; break;
            case QUEEN: symbol = "Q"; break;
            case KING: symbol = "K"; break;
        }
        return color == Color.WHITE ? symbol.toUpperCase() : symbol.toLowerCase();
    }

    /**
     * Determine valid moves for this piece based on its type and position.
     * @param start Starting position as [row, col]
     * @param board Current state of the board
     * @return List of valid end positions as [row, col]
     */
    public List<int[]> getValidMoves(int[] start, Board board) {
        List<int[]> moves = new ArrayList<>();
        int row = start[0];
        int col = start[1];

        switch (type) {
            case PAWN:
                moves.addAll(getPawnMoves(row, col, board));
                break;
            // Implement other piece types similarly
            case ROOK:
                moves.addAll(getRookMoves(row, col, board));
                break;
            case KNIGHT:
                moves.addAll(getKnightMoves(row, col, board));
                break;
            case BISHOP:
                moves.addAll(getBishopMoves(row, col, board));
                break;
            case QUEEN:
                moves.addAll(getQueenMoves(row, col, board));
                break;
            case KING:
                moves.addAll(getKingMoves(row, col, board));
                break;
        }

        return moves;
    }

    // Example implementation for Pawn moves
    private List<int[]> getPawnMoves(int row, int col, Board board) {
        List<int[]> moves = new ArrayList<>();
        int direction = (color == Color.WHITE) ? -1 : 1; // White moves up, Black moves down
        int newRow = row + direction;

        // Move forward
        if (board.isEmpty(newRow, col)) {
            moves.add(new int[]{newRow, col});
        }

        // Capture diagonally left
        if (col - 1 >= 0 && board.isEnemy(newRow, col - 1, color)) {
            moves.add(new int[]{newRow, col - 1});
        }

        // Capture diagonally right
        if (col + 1 < 8 && board.isEnemy(newRow, col + 1, color)) {
            moves.add(new int[]{newRow, col + 1});
        }

        return moves;
    }

    // Implement similar methods for other piece types
    private List<int[]> getRookMoves(int row, int col, Board board) {
        List<int[]> moves = new ArrayList<>();
        // Up
        for (int r = row - 1; r >= 0; r--) {
            if (board.isEmpty(r, col)) {
                moves.add(new int[]{r, col});
            } else {
                if (board.isEnemy(r, col, color)) {
                    moves.add(new int[]{r, col});
                }
                break;
            }
        }
        // Down
        for (int r = row + 1; r < 8; r++) {
            if (board.isEmpty(r, col)) {
                moves.add(new int[]{r, col});
            } else {
                if (board.isEnemy(r, col, color)) {
                    moves.add(new int[]{r, col});
                }
                break;
            }
        }
        // Left
        for (int c = col - 1; c >= 0; c--) {
            if (board.isEmpty(row, c)) {
                moves.add(new int[]{row, c});
            } else {
                if (board.isEnemy(row, c, color)) {
                    moves.add(new int[]{row, c});
                }
                break;
            }
        }
        // Right
        for (int c = col + 1; c < 8; c++) {
            if (board.isEmpty(row, c)) {
                moves.add(new int[]{row, c});
            } else {
                if (board.isEnemy(row, c, color)) {
                    moves.add(new int[]{row, c});
                }
                break;
            }
        }
        return moves;
    }

    private List<int[]> getKnightMoves(int row, int col, Board board) {
        List<int[]> moves = new ArrayList<>();
        int[][] offsets = {
            {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
            {1, -2}, {1, 2}, {2, -1}, {2, 1}
        };

        for (int[] offset : offsets) {
            int newRow = row + offset[0];
            int newCol = col + offset[1];
            if (isWithinBounds(newRow, newCol)) {
                if (board.isEmpty(newRow, newCol) || board.isEnemy(newRow, newCol, color)) {
                    moves.add(new int[]{newRow, newCol});
                }
            }
        }

        return moves;
    }

    private List<int[]> getBishopMoves(int row, int col, Board board) {
        List<int[]> moves = new ArrayList<>();
        // Diagonals: up-left, up-right, down-left, down-right
        int[][] directions = { {-1, -1}, {-1, 1}, {1, -1}, {1, 1} };

        for (int[] dir : directions) {
            int r = row + dir[0];
            int c = col + dir[1];
            while (isWithinBounds(r, c)) {
                if (board.isEmpty(r, c)) {
                    moves.add(new int[]{r, c});
                } else {
                    if (board.isEnemy(r, c, color)) {
                        moves.add(new int[]{r, c});
                    }
                    break;
                }
                r += dir[0];
                c += dir[1];
            }
        }

        return moves;
    }

    private List<int[]> getQueenMoves(int row, int col, Board board) {
        List<int[]> moves = new ArrayList<>();
        moves.addAll(getRookMoves(row, col, board));
        moves.addAll(getBishopMoves(row, col, board));
        return moves;
    }

    private List<int[]> getKingMoves(int row, int col, Board board) {
        List<int[]> moves = new ArrayList<>();
        int[][] offsets = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},          {0, 1},
            {1, -1},  {1, 0}, {1, 1}
        };

        for (int[] offset : offsets) {
            int newRow = row + offset[0];
            int newCol = col + offset[1];
            if (isWithinBounds(newRow, newCol)) {
                if (board.isEmpty(newRow, newCol) || board.isEnemy(newRow, newCol, color)) {
                    moves.add(new int[]{newRow, newCol});
                }
            }
        }

        return moves;
    }

    private boolean isWithinBounds(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
}
