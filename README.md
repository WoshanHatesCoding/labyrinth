# 🌀 Labyrinth Game

> 🎮 **Escape the maze, avoid the dragon, and survive the darkness!**

---

> ⚠️ **NOTE:**  
> This repository contains the full source code of the Labyrinth Game.  
> It is **not** a fully portable game release and requires:
>
> - Java Runtime Environment (8+)
> - MySQL setup (for high scores)
> - Manual compilation if not using the included `.jar`

This project was developed as an **Object-Oriented Programming (OOP)** assignment and is meant for **educational and demonstration purposes**.

---

## 🧩 Gameplay Features

- 🧍 **Start:** Bottom-left corner of the maze  
- 🏁 **Goal:** Reach the top-right corner  
- 🐉 **Danger:** A dragon roams randomly — if it gets adjacent to you, you lose  
- 🌑 **Visibility:** Player can only see up to **3 tiles away** due to darkness  
- 🧠 **Intelligence:** Dragon moves until it hits a wall, then changes direction randomly  
- 🗃️ **Progress:** Number of completed labyrinths is recorded  
- ☠️ **Game Over:** On death, your score and name are saved to the high score database  
- 🏆 **High Scores:** View the top 10 results from the menu  
- 🔄 **Restart Option** available via menu

---

## 🛠 Implementation Details

### 📁 Project Structure
- `src/labirithm/*.java` – Game logic, UI, and database classes
- `data/img/` – Game images: player, dragon, wall, etc.
- `data/levels/` – Maze layout files (`.txt`)
- `labirithm.jar` – Runnable compiled version of the game
- `README.TXT` – Original notes

### 🧱 Core Classes
- `GameEngine.java` – Core game loop & logic
- `GameGUI.java` – Graphical interface
- `MainMenu.java` – Start menu & navigation
- `Leader.java` – High score logic
- `DB.java` – MySQL database handler

---

## 🚀 How to Run the Game

> 💡 **Requires Java 8 or later**

### ▶️ Run the Prebuilt Game
```bash
java -jar labirithm.jar
