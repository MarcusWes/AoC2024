import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class day10 {public static void main(String[] args) {
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
        int[][] grid = createGridFromArray(lines);
        int n = grid.length;
        System.out.println(n);
        int totalNines = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    totalNines += checkForPossibleTrails(i, j, grid);
                }
            }
        }

        return totalNines;
    }

    static int checkForPossibleTrails(int i, int j, int[][] grid) {
        int n = grid.length;
        boolean[][] visited = new boolean[n][n];
        Set<String> foundNines = new HashSet<>();

        dfs(i, j, grid, visited, foundNines);

        return foundNines.size();
    }

    static void dfs(int i, int j, int[][] grid, boolean[][] visited, Set<String> foundNines) {
        int n = grid.length;

        if (i < 0 || i >= n || j < 0 || j >= n || visited[i][j]) {
            return; // Out of bounds or already visited
        }

        visited[i][j] = true;

        if (grid[i][j] == 9) {
            foundNines.add(i + "," + j);
            return;
        }

        // Define the possible directions (up, down, left, right)
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            int newI = i + dir[0];
            int newJ = j + dir[1];
            if (newI >= 0 && newI < n && newJ >= 0 && newJ < n && 
                !visited[newI][newJ] && grid[newI][newJ] == grid[i][j] + 1) {
                dfs(newI, newJ, grid, visited, foundNines);
            }
        }
    }

 

    static int Puzzle2(String[] lines) {
        int[][] grid = createGridFromArray(lines);
        int n = grid.length;
        int totalRating = 0;
    
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    totalRating += computeTrailheadRating(i, j, grid);
                }
            }
        }
    
        return totalRating;
    }
    
    static int computeTrailheadRating(int i, int j, int[][] grid) {
        int n = grid.length;
        boolean[][] visited = new boolean[n][n];
        Set<String> distinctTrails = new HashSet<>();
    
        findTrailsDFS(i, j, grid, visited, new ArrayList<>(), distinctTrails);
    
        return distinctTrails.size();
    }
    
    static void findTrailsDFS(int i, int j, int[][] grid, boolean[][] visited, List<String> currentTrail, Set<String> distinctTrails) {
        int n = grid.length;
    
        // Base conditions: out of bounds or already visited
        if (i < 0 || i >= n || j < 0 || j >= n || visited[i][j]) {
            return;
        }
    
        visited[i][j] = true;
        currentTrail.add(i + "," + j);
    
        if (grid[i][j] == 9) {
            distinctTrails.add(String.join("->", currentTrail));
        } else {
            int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    
            for (int[] dir : directions) {
                int newI = i + dir[0];
                int newJ = j + dir[1];
    
                if (newI >= 0 && newI < n && newJ >= 0 && newJ < n && !visited[newI][newJ] && grid[newI][newJ] == grid[i][j] + 1) {
                    findTrailsDFS(newI, newJ, grid, visited, currentTrail, distinctTrails);
                }
            }
        }
    
        currentTrail.remove(currentTrail.size() - 1);
        visited[i][j] = false;
    }
    


    static int[][] createGridFromArray(String[] lines) {
        int rows = lines.length;
        int cols = lines[0].length();
        int[][] grid = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            String line = lines[i];
            for (int j = 0; j < cols; j++) {
                grid[i][j] = Integer.parseInt(String.valueOf(line.charAt(j)));
            }
        }

        return grid;
    }
}
