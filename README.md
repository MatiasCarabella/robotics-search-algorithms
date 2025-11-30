# Search Algorithms - Robotic Engine Assembly

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?logo=openjdk&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-blue.svg)
![Version](https://img.shields.io/badge/version-1.0-green.svg)

</div>

A Java implementation demonstrating exhaustive and heuristic search algorithms in the context of robotic positioning for engine assembly. This project showcases fundamental AI search strategies with a practical application scenario.

## 🎯 About

This project simulates a robotic search system designed to locate a target position on an engine assembly line. The robot starts at an initial position and must find a specific target location using two different search strategies:

- **Exhaustive Search**: A systematic bidirectional exploration that alternates between moving right and left with increasing distances
- **Heuristic Search**: An intelligent approach using relief gradient information to guide the search toward the target

The implementation follows SOLID principles and clean code practices, making it an excellent educational resource for understanding search algorithms and object-oriented design.

## 📺 Example Output

**Exhaustive Search:**
```
╔═══════════════════════════════════════════════════════════╗
║           EXHAUSTIVE SEARCH (BIDIRECTIONAL)               ║
╚═══════════════════════════════════════════════════════════╝

Description:
  Systematically explores both directions from the initial
  position without information about the target location.

  Pattern: B -> B+d -> B-2d -> B+3d -> B-4d -> ...

Starting search...

============================================================
....................S.......................T...............
S=Start(250.0) T=Target(450.0)
============================================================
Step   1: Position  250.0 (start) [·······█············]
Step   2: Position  265.0 (right ->) [········█···········]
Step   3: Position  220.0 (left <-) [······█·············]
Step   4: Position  280.0 (right ->) [·········█··········]
...
Step  14: Position  445.0 (right ->) [···············█····]

TARGET FOUND at position 450.0

-----------------------------------------------------------
                      STATISTICS                           
-----------------------------------------------------------
Total steps:             14
Distance traveled:      975.0 units
Execution time:            2 ms
Final position:          450.0
-----------------------------------------------------------
```

**Heuristic Search:**
```
╔═══════════════════════════════════════════════════════════╗
║        HEURISTIC SEARCH (RELIEF GRADIENT)                 ║
╚═══════════════════════════════════════════════════════════╝

Description:
  Uses the prominent ring relief as a guide.
  Moves towards where the relief is greater.

  Heuristic function: h(n) = relief at position n

Starting search...

============================================================
....................S.......................T...............
S=Start(250.0) T=Target(450.0)
============================================================
Step   1: Pos  250.0 | Relief  0.00 [░░░░░░░░░░] (start) [·······█············]
Step   2: Pos  265.0 | Relief 25.00 [██████░░░░] (right ->) [········█···········]
Step   3: Pos  280.0 | Relief 30.00 [███████░░░] (right ->) [·········█··········]
Step   4: Pos  295.0 | Relief 35.00 [████████░░] (right ->) [··········█·········]
...
Step  14: Pos  450.0 | Relief 40.00 [██████████] (right ->) [···············█····]

TARGET FOUND at position 450.0

-----------------------------------------------------------
                      STATISTICS                           
-----------------------------------------------------------
Total steps:             14
Distance traveled:      200.0 units
Execution time:            1 ms
Final position:          450.0
-----------------------------------------------------------
```

## ✨ Features

- Two distinct search algorithm implementations
- Real-time step-by-step visualization
- Performance statistics tracking (steps, distance, execution time)
- Interactive console menu
- Configurable search parameters
- Clean separation of concerns with dedicated classes

## 📊 Algorithm Comparison

| Algorithm | Strategy | Pros | Cons |
|-----------|----------|------|------|
| Exhaustive | Systematic bidirectional | Guaranteed to find target | More steps required |
| Heuristic | Relief gradient following | Faster convergence | Requires domain knowledge |

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
  - Displacement: 200.0 units
  - Search increment: 15.0

-----------------------------------------------------------
                      MAIN MENU                            
-----------------------------------------------------------
  1. Exhaustive Search (Bidirectional)
  2. Heuristic Search (Relief Gradient)
  3. Exit
-----------------------------------------------------------
```

Select an option to see the algorithm in action with detailed step-by-step output and final statistics.

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

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
