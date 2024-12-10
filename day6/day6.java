import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class day6 {

    //Partially done with AI
    public static void main(String[] args) {
        String inputFile = "input.txt";
        if (args.length > 0) {
            inputFile = args[0];
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(inputFile));
            System.out.println("Puzzle 1: " + Puzzle1(lines.toArray(new String[0])));
            System.out.println("Puzzle 2: " + Puzzle2(lines.toArray(new String[0])));
        } catch (IOException e) {
            System.out.println("Error: Input file '" + inputFile + "' not found.");
        }
    }

    static int Puzzle1(String[] lines) {
        char[][] grid = createGridFromArray(lines);

        int[] position = findCharacter(grid, '^');
        if (position == null) {
            System.out.println("Start position could not be found.");
            return 0;
        }

        Set<String> visited = new HashSet<>();
        visited.add(position[0] + "," + position[1] + ",0"); // Include direction in the visited state

        int direction = 0; // 0: up, 1: right, 2: down, 3: left

        while (true) {
            int newRow = position[0];
            int newCol = position[1];

            // Check next pos
            switch (direction) {
                case 0: newRow--; break; // up
                case 1: newCol++; break; // right
                case 2: newRow++; break; // down
                case 3: newCol--; break; // left
            }

            // Check if guard is leaving map
            if (newRow < 0 || newRow >= grid.length || newCol < 0 || newCol >= grid[0].length) {
                return visited.size();
            }

            if (grid[newRow][newCol] == '#') {
                direction = (direction + 1) % 4; // Turn right
            } else {
                grid[position[0]][position[1]] = '.'; // Remove "^" from old pos
                position[0] = newRow;
                position[1] = newCol;
                grid[position[0]][position[1]] = '^'; // Place "^" at new pos

                String state = position[0] + "," + position[1] + "," + direction;
                if (!visited.add(state)) {
                    break; // Detect a loop
                }
            }
        }
        return visited.size();
    }

    static char[][] createGridFromArray(String[] lines) {
        int rows = lines.length;
        int cols = lines[0].length();
        char[][] grid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            String line = lines[i];
            for (int j = 0; j < cols; j++) {
                grid[i][j] = line.charAt(j);
            }
        }

        return grid;
    }

    static int[] findCharacter(char[][] grid, char target) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == target) {
                    return new int[] { i, j };
                }
            }
        }
        return null;
    }

    static int Puzzle2(String[] lines) {
        char[][] grid = createGridFromArray(lines);
        int[] startPosition = findCharacter(grid, '^');
        if (startPosition == null) {
            System.out.println("Start position not found.");
            return 0;
        }

        // List all possible positions to place obstructions
        Set<String> possiblePositions = new HashSet<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '.' && !(i == startPosition[0] && j == startPosition[1])) {
                    possiblePositions.add(i + "," + j);
                }
            }
        }

        // Count how many of these positions create a loop
        int loopCount = 0;
        for (String position : possiblePositions) {
            String[] pos = position.split(",");
            int row = Integer.parseInt(pos[0]);
            int col = Integer.parseInt(pos[1]);

            // Place an obstruction and simulate
            grid[row][col] = '#';
            if (causesLoop(grid, startPosition)) {
                loopCount++;
            }
            // Reset
            grid[row][col] = '.';
        }

        return loopCount;
    }

    static boolean causesLoop(char[][] grid, int[] startPosition) {
        int[] position = startPosition.clone();
        Set<String> visited = new HashSet<>();
        visited.add(position[0] + "," + position[1] + ",0"); // Include direction in visited

        int direction = 0; // 0: up, 1: right, 2: down, 3: left
        while (true) {
            int newRow = position[0];
            int newCol = position[1];

            // Calculate next pos
            switch (direction) {
                case 0: newRow--; break; // up
                case 1: newCol++; break; // right
                case 2: newRow++; break; // down
                case 3: newCol--; break; // left
            }

            // Check if guard leaves the grid
            if (newRow < 0 || newRow >= grid.length || newCol < 0 || newCol >= grid[0].length) {
                return false; // No loop if the guard exits the map
            }

            // Check for obstacle
            if (grid[newRow][newCol] == '#') {
                direction = (direction + 1) % 4; // Turn right
            } else {
                position[0] = newRow;
                position[1] = newCol;

                // Check if position and direction have already been visited
                String currentState = position[0] + "," + position[1] + "," + direction;
                if (!visited.add(currentState)) {
                    return true; // A loop is detected
                }
            }
        }
    }
}
