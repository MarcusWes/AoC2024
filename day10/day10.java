import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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
        return 1;
    }

 

    static int Puzzle2(String[] lines) {
        return 2;
    }
}
