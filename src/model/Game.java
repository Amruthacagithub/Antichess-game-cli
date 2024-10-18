// src/model/Game.java
package model;

public class Game {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    public Game(String player1Name, String player2Name) {
        board = new Board();
        player1 = new Player(player1Name, Piece.Color.WHITE);
        player2 = new Player(player2Name, Piece.Color.BLACK);
        currentPlayer = player1; // White starts first
    }

    /**
     * Start the game loop.
     */
    public void start() {
        while (true) {
            board.displayBoard();
            if (board.isGameOver()) {
                declareWinner();
                break;
            }

            if (board.isCapturePossible(currentPlayer.getColor())) {
                System.out.println("A capture move is available. You must capture an opponent's piece.");
            }

            Player.MoveInput moveInput = currentPlayer.makeMove(board);

            if (moveInput.isQuit()) {
                declareQuit();
                break;
            }

            int[] start = moveInput.getStart();
            int[] end = moveInput.getEnd();

            if (board.isValidMove(start, end, currentPlayer.getColor())) {
                board.makeMove(start, end);
                switchPlayer();
            } else {
                System.out.println("Invalid move. Please try again.");
            }
        }
    }

    /**
     * Switch the turn to the other player.
     */
    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    /**
     * Declare the winner when the game is over.
     */
    private void declareWinner() {
        Piece.Color winnerColor = board.determineWinner();
        if (winnerColor == null) {
            System.out.println("Game ended in a draw.");
        } else {
            String winnerName = (winnerColor == Piece.Color.WHITE) ? player1.getName() : player2.getName();
            System.out.println("Game Over! The winner is " + winnerName + " (" + winnerColor.toString().toLowerCase() + ").");
        }
    }

    /**
     * Handle player quitting the game.
     */
    private void declareQuit() {
        Player winner = (currentPlayer == player1) ? player2 : player1;
        System.out.println(currentPlayer.getName() + " has quit the game.");
        System.out.println("The winner is " + winner.getName() + " (" + winner.getColor().toString().toLowerCase() + ").");
    }
}
