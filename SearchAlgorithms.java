import java.util.Scanner;

/**
 * Robotic Search System for Engine Assembly
 * Demonstrates exhaustive and heuristic search algorithms
 * 
 * @author Matías Carabella
 * @version 1.0
 */
public class SearchAlgorithms {
    
    // Configuration constants
    private static final double INITIAL_POSITION = 250.0;
    private static final double TARGET_POSITION = 450.0;
    private static final double INCREMENT = 15.0;
    private static final double RING_RADIUS = 40.0;
    private static final double TOLERANCE = 7.5;
    private static final double MIN_RANGE = 50.0;
    private static final double MAX_RANGE = 750.0;
    
    private static final Scanner SCANNER = new Scanner(System.in);
    
    public static void main(String[] args) {
        int option;
        
        do {
            displayMenu();
            option = readOption();
            
            if (option == 1 || option == 2) {
                executeSearch(option);
                waitForUser();
            } else if (option == 3) {
                System.out.println("\nGoodbye!");
            } else {
                System.out.println("\nX Invalid option. Please try again.");
            }
            
        } while (option != 3);
        
        SCANNER.close();
    }
    
    private static void displayMenu() {
        clearScreen();
        System.out.println("===========================================================");
        System.out.println("  ROBOTIC SEARCH SYSTEM - ENGINE ASSEMBLY");
        System.out.println("===========================================================");
        System.out.println();
        System.out.println("Problem Configuration:");
        System.out.println("  - Initial position (B): " + INITIAL_POSITION);
        System.out.println("  - Target position (A): " + TARGET_POSITION + " (unknown to algorithms)");
        System.out.println("  - Displacement: " + (TARGET_POSITION - INITIAL_POSITION) + " units");
        System.out.println("  - Search increment: " + INCREMENT);
        System.out.println();
        System.out.println("-----------------------------------------------------------");
        System.out.println("                      MAIN MENU                            ");
        System.out.println("-----------------------------------------------------------");
        System.out.println("  1. Exhaustive Search (Bidirectional)");
        System.out.println("  2. Heuristic Search (Relief Gradient)");
        System.out.println("  3. Exit");
        System.out.println("-----------------------------------------------------------");
        System.out.print("\nSelect an option: ");
    }
    
    private static int readOption() {
        try {
            int option = SCANNER.nextInt();
            SCANNER.nextLine();
            return option;
        } catch (Exception e) {
            SCANNER.nextLine();
            return -1;
        }
    }
    
    private static void executeSearch(int option) {
        SearchAlgorithm algorithm = (option == 1) 
            ? new ExhaustiveSearch() 
            : new HeuristicSearch();
        
        algorithm.execute();
    }
    
    private static void waitForUser() {
        System.out.println("\nPress Enter to continue...");
        SCANNER.nextLine();
    }
    
    private static void clearScreen() {
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }
    
    // Getters for constants
    static double getInitialPosition() { return INITIAL_POSITION; }
    static double getTargetPosition() { return TARGET_POSITION; }
    static double getIncrement() { return INCREMENT; }
    static double getRingRadius() { return RING_RADIUS; }
    static double getTolerance() { return TOLERANCE; }
    static double getMinRange() { return MIN_RANGE; }
    static double getMaxRange() { return MAX_RANGE; }
}

/**
 * Statistics tracker following Single Responsibility Principle
 */
class SearchStatistics {
    private int steps = 0;
    private double distanceTraveled = 0.0;
    private double finalPosition = 0.0;
    
    public void incrementSteps() {
        steps++;
    }
    
    public void addDistance(double distance) {
        distanceTraveled += distance;
    }
    
    public void setFinalPosition(double position) {
        finalPosition = position;
    }
    
    public int getSteps() {
        return steps;
    }
    
    public double getFinalPosition() {
        return finalPosition;
    }
    
    public void displayResults(long executionTimeMs) {
        System.out.println("\n-----------------------------------------------------------");
        System.out.println("                      STATISTICS                           ");
        System.out.println("-----------------------------------------------------------");
        System.out.printf("Total steps:             %3d%n", steps);
        System.out.printf("Distance traveled:       %6.1f units%n", distanceTraveled);
        System.out.printf("Execution time:          %4d ms%n", executionTimeMs);
        System.out.printf("Final position:          %6.1f%n", finalPosition);
        System.out.println("-----------------------------------------------------------");
    }
}

/**
 * Abstract base class for search algorithms (Open/Closed Principle)
 */
abstract class SearchAlgorithm {
    protected SearchStatistics stats;
    
    public SearchAlgorithm() {
        this.stats = new SearchStatistics();
    }
    
    public void execute() {
        displayHeader();
        long startTime = System.currentTimeMillis();
        
        performSearch();
        
        long endTime = System.currentTimeMillis();
        stats.displayResults(endTime - startTime);
    }
    
    protected abstract void displayHeader();
    protected abstract void performSearch();
    
    protected boolean isTargetFound(double position) {
        return Math.abs(position - SearchAlgorithms.getTargetPosition()) <= SearchAlgorithms.getTolerance();
    }
    
    protected boolean isWithinBounds(double position) {
        return position >= SearchAlgorithms.getMinRange() && position <= SearchAlgorithms.getMaxRange();
    }
}

/**
 * Exhaustive bidirectional search implementation
 */
class ExhaustiveSearch extends SearchAlgorithm {
    
    @Override
    protected void displayHeader() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║           EXHAUSTIVE SEARCH (BIDIRECTIONAL)               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
        System.out.println("Description:");
        System.out.println("  Systematically explores both directions from the initial");
        System.out.println("  position without information about the target location.\n");
        System.out.println("  Pattern: B -> B+d -> B-2d -> B+3d -> B-4d -> ...\n");
        System.out.println("Starting search...\n");
        System.out.println("-".repeat(60));
    }
    
    @Override
    protected void performSearch() {
        double currentPosition = SearchAlgorithms.getInitialPosition();
        double previousPosition = currentPosition;
        
        System.out.printf("Step %3d: Position %6.1f (start)%n", 1, currentPosition);
        stats.incrementSteps();
        
        final int MAX_ITERATIONS = 1000;
        for (int i = 1; stats.getSteps() < MAX_ITERATIONS && !isTargetFound(currentPosition); i++) {
            // Move right
            double newPosition = SearchAlgorithms.getInitialPosition() + i * SearchAlgorithms.getIncrement();
            if (isWithinBounds(newPosition)) {
                currentPosition = moveToPosition(currentPosition, newPosition, previousPosition, "right ->");
                previousPosition = currentPosition;
                if (isTargetFound(currentPosition)) break;
            }
            
            // Move left
            newPosition = SearchAlgorithms.getInitialPosition() - i * SearchAlgorithms.getIncrement();
            if (isWithinBounds(newPosition)) {
                currentPosition = moveToPosition(currentPosition, newPosition, previousPosition, "left <-");
                previousPosition = currentPosition;
                if (isTargetFound(currentPosition)) break;
            }
        }
        
        finalizeSearch(currentPosition);
    }
    
    private double moveToPosition(double current, double target, double previous, String direction) {
        stats.addDistance(Math.abs(target - previous));
        stats.incrementSteps();
        System.out.printf("Step %3d: Position %6.1f (%s)%n", stats.getSteps(), target, direction);
        return target;
    }
    
    private void finalizeSearch(double position) {
        stats.setFinalPosition(position);
        System.out.println("-".repeat(60));
        
        if (isTargetFound(position)) {
            System.out.printf("\nTARGET FOUND at position %.1f%n", position);
        } else {
            System.out.printf("\nTARGET NOT FOUND. Final position: %.1f%n", position);
        }
    }
}

/**
 * Heuristic search using relief gradient
 */
class HeuristicSearch extends SearchAlgorithm {
    
    @Override
    protected void displayHeader() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║        HEURISTIC SEARCH (RELIEF GRADIENT)                 ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
        System.out.println("Description:");
        System.out.println("  Uses the prominent ring relief as a guide.");
        System.out.println("  Moves towards where the relief is greater.\n");
        System.out.println("  Heuristic function: h(n) = relief at position n\n");
        System.out.println("Starting search...\n");
        System.out.println("-".repeat(60));
    }
    
    @Override
    protected void performSearch() {
        double currentPosition = SearchAlgorithms.getInitialPosition();
        double previousPosition = currentPosition;
        int stepsWithoutImprovement = 0;
        
        double relief = calculateRelief(currentPosition);
        System.out.printf("Step %3d: Pos %6.1f | Relief %5.2f (start)%n", 
                         1, currentPosition, relief);
        stats.incrementSteps();
        
        final int MAX_STEPS = 100;
        while (!isTargetFound(currentPosition) && stats.getSteps() < MAX_STEPS) {
            double currentRelief = calculateRelief(currentPosition);
            double rightPos = currentPosition + SearchAlgorithms.getIncrement();
            double leftPos = currentPosition - SearchAlgorithms.getIncrement();
            
            double rightRelief = isWithinBounds(rightPos) ? calculateRelief(rightPos) : -1;
            double leftRelief = isWithinBounds(leftPos) ? calculateRelief(leftPos) : -1;
            
            MoveDecision decision = decideBestMove(currentRelief, rightRelief, leftRelief, 
                                                   rightPos, leftPos, stepsWithoutImprovement);
            
            if (!isWithinBounds(decision.position)) {
                System.out.println("\n(!) Position out of range");
                break;
            }
            
            stats.addDistance(Math.abs(decision.position - previousPosition));
            previousPosition = currentPosition;
            currentPosition = decision.position;
            stepsWithoutImprovement = decision.stepsWithoutImprovement;
            
            relief = calculateRelief(currentPosition);
            stats.incrementSteps();
            System.out.printf("Step %3d: Pos %6.1f | Relief %5.2f (%s)%n", 
                             stats.getSteps(), currentPosition, relief, decision.direction);
        }
        
        finalizeSearch(currentPosition);
    }
    
    private MoveDecision decideBestMove(double currentRelief, double rightRelief, double leftRelief,
                                       double rightPos, double leftPos, int stepsWithoutImprovement) {
        if (rightRelief > currentRelief && rightRelief >= leftRelief) {
            return new MoveDecision(rightPos, "right ->", 0);
        } else if (leftRelief > currentRelief) {
            return new MoveDecision(leftPos, "left <-", 0);
        } else {
            stepsWithoutImprovement++;
            if (stepsWithoutImprovement >= 3) {
                System.out.println("    -> Local maximum detected, exploratory jump");
                double exploratoryPos = rightPos + ((Math.random() > 0.5 ? 1 : -1) * SearchAlgorithms.getIncrement());
                return new MoveDecision(exploratoryPos, "exploration", 0);
            }
            double nextPos = (rightRelief >= leftRelief) ? rightPos : leftPos;
            String direction = (rightRelief >= leftRelief) ? "right ->" : "left <-";
            return new MoveDecision(nextPos, direction, stepsWithoutImprovement);
        }
    }
    
    private double calculateRelief(double position) {
        double distanceToCenter = Math.abs(position - SearchAlgorithms.getTargetPosition());
        double relief = SearchAlgorithms.getRingRadius() - distanceToCenter;
        return Math.max(0.0, relief);
    }
    
    private void finalizeSearch(double position) {
        stats.setFinalPosition(position);
        System.out.println("-".repeat(60));
        System.out.printf("\nTARGET FOUND at position %.1f%n", position);
    }
    
    private static class MoveDecision {
        final double position;
        final String direction;
        final int stepsWithoutImprovement;
        
        MoveDecision(double position, String direction, int stepsWithoutImprovement) {
            this.position = position;
            this.direction = direction;
            this.stepsWithoutImprovement = stepsWithoutImprovement;
        }
    }
}
