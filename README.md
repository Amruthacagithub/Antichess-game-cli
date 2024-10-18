# Anti-Chess CLI Game

A command-line interface (CLI) based Anti-Chess game for two players. In Anti-Chess, the objective is to lose all your pieces before your opponent. The game enforces mandatory captures.

## Features

- Standard 8x8 chessboard setup.
- All standard chess pieces with their movement rules.
- Mandatory captures: If a capture is available, it must be taken.
- Players can input moves in the format "A2 B3".
- Option to quit the game at any time.

## Project Structure

antichess-cli-java/ │ ├── src/ │ ├── model/ │ │ ├── Piece.java │ │ ├── Board.java │ │ ├── Player.java │ │ └── Game.java │ └── Main.java │ ├── test/ │ └── (Unit tests can be added here) │ ├── .gitignore └── README.md


## Prerequisites

- Java Development Kit (JDK) 8 or higher installed.
- `javac` and `java` commands available in your system PATH.

## Building and Running the Game

1. **Clone the Repository:**

   ```bash
   git clone <repository-url>
   cd antichess-cli-java

2. **Compile the Source Code:**

mkdir bin
javac -d bin src/model/*.java src/Main.java

3. **Run the Game:**

java -cp bin Main

4. **GamePlay:**

- Enter player names when prompted.
- Input moves in the format "A2 B3" to move a piece from A2 to B3.
- Type quit to exit the game, declaring the opponent as the winner.
