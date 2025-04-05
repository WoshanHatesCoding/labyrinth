🌀 Labyrinth Game
Labyrinth is a Java-based game where the player must escape from a maze while avoiding a randomly-moving dragon. This project was developed as an object-oriented programming assignment and demonstrates concepts like GUI development, game logic, randomness, and database interaction for high scores.

🎮 Gameplay
The player starts at the bottom-left corner of a maze.

The goal is to reach the top-right corner as fast as possible.

The dragon moves in a straight line until hitting a wall, then changes direction randomly.

If the dragon becomes adjacent to the player, the player loses.

Due to darkness, the player can only see up to 3 units away.

The game tracks how many labyrinths the player has successfully escaped.

If the player loses, their name and score are saved in a database.

A highscore menu displays the top 10 scores.

Includes a menu item to restart the game.

📂 Features & Structure
src/labirithm/*.java – Source code:

GameEngine, GameGUI, MainMenu: game logic and user interface.

DB.java: manages high score database.

data/img/ – Contains game graphics: player, dragon, walls, exit.

data/levels/ – Maze layout text files.

labirithm.jar – Prebuilt JAR file to run the game.

README.TXT – Original notes (included in dist/ folder).

🚀 How to Run
Make sure you have Java 8+ installed.

Run the game from the .jar file:

bash
Copy
Edit
java -jar labirithm.jar
💡 You may need the MySQL JDBC driver (mysql-connector-j-8.2.0.jar) in your classpath if you're rebuilding from source and using the high score feature.

🏆 High Scores
Stored in a MySQL database.

Automatically updates when the player loses.

Accessible through the menu.
