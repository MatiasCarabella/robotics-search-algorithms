# Search Algorithms - Robotic Engine Assembly

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-blue.svg?style=for-the-badge)
![Version](https://img.shields.io/badge/version-1.0-green.svg?style=for-the-badge)

A Java implementation demonstrating exhaustive and heuristic search algorithms in the context of robotic positioning for engine assembly. This project showcases fundamental AI search strategies with a practical application scenario.

## 🎯 About

This project simulates a robotic search system designed to locate a target position on an engine assembly line. The robot starts at an initial position and must find a specific target location using two different search strategies:

- **Exhaustive Search**: A systematic bidirectional exploration that alternates between moving right and left with increasing distances
- **Heuristic Search**: An intelligent approach using relief gradient information to guide the search toward the target

The implementation follows SOLID principles and clean code practices, making it an excellent educational resource for understanding search algorithms and object-oriented design.

## ✨ Features

- Two distinct search algorithm implementations
- Real-time step-by-step visualization
- Performance statistics tracking (steps, distance, execution time)
- Interactive console menu
- Configurable search parameters
- Clean separation of concerns with dedicated classes

## 🚀 Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- A terminal or command prompt

### Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/search-algorithms.git
cd search-algorithms
```

2. Compile the program:
```bash
javac SearchAlgorithms.java
```

3. Run the program:
```bash
java SearchAlgorithms
```

## 📖 Usage

When you run the program, you'll see an interactive menu:

```
===========================================================
  ROBOTIC SEARCH SYSTEM - ENGINE ASSEMBLY
===========================================================

Problem Configuration:
  - Initial position (B): 250.0
  - Target position (A): 450.0 (unknown to algorithms)
  • Displacement: 200.0 units
  • Search increment: 15.0

-----------------------------------------------------------
                      MAIN MENU                            
-----------------------------------------------------------
  1. Exhaustive Search (Bidirectional)
  2. Heuristic Search (Relief Gradient)
  3. Exit
-----------------------------------------------------------
```

Select an option to see the algorithm in action with detailed step-by-step output and final statistics.

## 🏗️ Architecture

The project follows SOLID principles with clear separation of concerns:

- **SearchAlgorithms**: Main entry point, user interface, and configuration constants
- **SearchAlgorithm**: Abstract base class (Open/Closed Principle)
- **ExhaustiveSearch**: Concrete implementation of bidirectional search
- **HeuristicSearch**: Concrete implementation of gradient-based search
- **SearchStatistics**: Statistics tracking and reporting

## 🔧 Configuration

Default parameters can be modified in the `SearchAlgorithms` class constants:

```java
private static final double INITIAL_POSITION = 250.0;  // Starting position
private static final double TARGET_POSITION = 450.0;   // Target to find
private static final double INCREMENT = 15.0;          // Step size
private static final double RING_RADIUS = 40.0;        // Relief radius
private static final double TOLERANCE = 7.5;           // Acceptable error
private static final double MIN_RANGE = 50.0;          // Minimum boundary
private static final double MAX_RANGE = 750.0;         // Maximum boundary
```

## 📊 Algorithm Comparison

| Algorithm | Strategy | Pros | Cons |
|-----------|----------|------|------|
| Exhaustive | Systematic bidirectional | Guaranteed to find target | More steps required |
| Heuristic | Relief gradient following | Faster convergence | Requires domain knowledge |

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
