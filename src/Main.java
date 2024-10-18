// src/Main.java
import model.Game;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Anti-Chess CLI Game!");

        System.out.print("Enter name for Player 1 (White): ");
        String player1Name = scanner.nextLine().trim();
        if (player1Name.isEmpty()) {
            player1Name = "Player 1";
        }

        System.out.print("Enter name for Player 2 (Black): ");
        String player2Name = scanner.nextLine().trim();
        if (player2Name.isEmpty()) {
            player2Name = "Player 2";
        }

        Game game = new Game(player1Name, player2Name);
        game.start();

        scanner.close();
    }
}
