import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class day8 {

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

        List<String> grid = Arrays.asList(lines);
        int n = grid.size();

        Set<Point> antinodes = new HashSet<>();

        Map<Character, List<Point>> allLocs = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                char c = grid.get(i).charAt(j);
                if (c != '.') {
                    allLocs.computeIfAbsent(c, k -> new ArrayList<>()).add(new Point(i, j));
                }
            }
        }

        for (List<Point> locs : allLocs.values()) {
            for (int i = 0; i < locs.size(); i++) {
                for (int j = i + 1; j < locs.size(); j++) {
                    Point a = locs.get(i);
                    Point b = locs.get(j);
                    antinodes.addAll(getAntinodes(a, b, n));
                }
            }
        }

        return antinodes.size();
    }

    private static boolean inBounds(int x, int y, int n) {
        return 0 <= x && x < n && 0 <= y && y < n;
    }

    private static List<Point> getAntinodes(Point a, Point b, int n) {
        List<Point> antinodes = new ArrayList<>();
        int cx = a.x - (b.x - a.x);
        int cy = a.y - (b.y - a.y);
        int dx = b.x + (b.x - a.x);
        int dy = b.y + (b.y - a.y);

        if (inBounds(cx, cy, n)) {
            antinodes.add(new Point(cx, cy));
        }
        if (inBounds(dx, dy, n)) {
            antinodes.add(new Point(dx, dy));
        }
        return antinodes;
    }

 

    static int Puzzle2(String[] lines) {
        List<String> grid = Arrays.asList(lines);
        int n = grid.size();

        Map<Character, List<Point>> antennas = new HashMap<>();
        Set<Point> antiNodes = new HashSet<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                char c = grid.get(i).charAt(j);
                if (c != '.') {
                    antennas.computeIfAbsent(c, k -> new ArrayList<>()).add(new Point(i, j));
                }
            }
        }

        for (List<Point> positions : antennas.values()) {
            for (int i = 0; i < positions.size(); i++) {
                for (int j = i + 1; j < positions.size(); j++) {
                    Point pos1 = positions.get(i);
                    Point pos2 = positions.get(j);

                    int deltaRow = pos2.x - pos1.x;
                    int deltaCol = pos2.y - pos1.y;

                    int antiNode1Row = pos1.x;
                    int antiNode1Col = pos1.y;

                    while (antiNode1Row >= 0 && antiNode1Row < n && antiNode1Col >= 0 && antiNode1Col < n) {
                        antiNodes.add(new Point(antiNode1Row, antiNode1Col));
                        antiNode1Row -= deltaRow;
                        antiNode1Col -= deltaCol;
                    }

                    int antiNode2Row = pos2.x;
                    int antiNode2Col = pos2.y;

                    while (antiNode2Row >= 0 && antiNode2Row < n && antiNode2Col >= 0 && antiNode2Col < n) {
                        antiNodes.add(new Point(antiNode2Row, antiNode2Col));
                        antiNode2Row += deltaRow;
                        antiNode2Col += deltaCol;
                    }
                }
            }
        }

        return antiNodes.size();
    }

    
    private static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

}