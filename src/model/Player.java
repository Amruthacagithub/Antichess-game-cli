// src/model/Player.java
package model;

import java.util.Scanner;

public class Player {
    private String name;
    private Piece.Color color;
    private Scanner scanner;

    public Player(String name, Piece.Color color) {
        this.name = name;
        this.color = color;
        this.scanner = new Scanner(System.in);
    }

    public String getName() {
        return name;
    }

    public Piece.Color getColor() {
        return color;
    }

    /**
     * Prompt the player to make a move.
     * @param board Current game board
     * @return An array containing start and end positions or 'quit' command
     */
    public MoveInput makeMove(Board board) {
        while (true) {
            System.out.print(name + " (" + color.toString().toLowerCase() + "), enter your move (e.g., A2 B3) or type 'quit' to exit: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("quit")) {
                return new MoveInput("quit", "quit");
            }

            String[] parts = input.split(" ");
            if (parts.length != 2) {
                System.out.println("Invalid input format. Please enter moves like 'A2 B3'.");
                continue;
            }

            String start = parts[0];
            String end = parts[1];

            if (!isValidPosition(start) || !isValidPosition(end)) {
                System.out.println("Invalid board positions. Please use positions like 'A2' to 'H8'.");
                continue;
            }

            int[] startPos = convertPosition(start);
            int[] endPos = convertPosition(end);

            return new MoveInput(startPos, endPos);
        }
    }

    /**
     * Validate the format of a board position.
     * @param pos Position string (e.g., "A2")
     * @return true if valid, false otherwise
     */
    private boolean isValidPosition(String pos) {
        if (pos.length() != 2) return false;
        char file = pos.toUpperCase().charAt(0);
        char rank = pos.charAt(1);
        return (file >= 'A' && file <= 'H') && (rank >= '1' && rank <= '8');
    }

    /**
     * Convert a board position string to array indices.
     * @param pos Position string (e.g., "A2")
     * @return Array with row and column indices
     */
    private int[] convertPosition(String pos) {
        char file = pos.toUpperCase().charAt(0);
        char rank = pos.charAt(1);
        int col = file - 'A';
        int row = 8 - (rank - '0');
        return new int[]{row, col};
    }

    /**
     * Inner class to encapsulate move input.
     */
    public static class MoveInput {
        private String command;
        private int[] start;
        private int[] end;

        // Constructor for 'quit' command
        public MoveInput(String command, String dummy) {
            this.command = command;
        }

        // Constructor for normal moves
        public MoveInput(int[] start, int[] end) {
            this.start = start;
            this.end = end;
        }

        public boolean isQuit() {
            return "quit".equalsIgnoreCase(command);
        }

        public int[] getStart() {
            return start;
        }

        public int[] getEnd() {
            return end;
        }
    }
}
