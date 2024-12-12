import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class day9 {

    public static void main(String[] args) {
        String inputFile = "input.txt";
        if (args.length > 0) {
            inputFile = args[0];
        }

        try {
            String line = Files.readString(Paths.get(inputFile)).trim(); // Trim to handle extra spaces or newlines
            //System.out.println("Input File Content: " + line);
            System.out.println("Puzzle 1: " + solvePuzzle1(line));
            System.out.println("Puzzle 2: " + solvePuzzle2(line));
        } catch (IOException e) {
            System.err.println("Error: Input file '" + inputFile + "' not found.");
        }
    }
    //partially done with ai
    static long solvePuzzle1(String line) {
        List<Integer> disk = new ArrayList<>();
        boolean isSpace = false;
        int id = 0;

        // Parse the input string and populate the disk
        for (char character : line.toCharArray()) {
            if (!Character.isDigit(character)) {
                System.err.println("Skipping invalid character: " + character);
                continue;
            }

            int num = Character.getNumericValue(character);
            if (isSpace) {
                for (int i = 0; i < num; i++) disk.add(-1); // -1 represents free space
            } else {
                for (int i = 0; i < num; i++) disk.add(id); // File blocks with ID
                id++;
            }
            isSpace = !isSpace;
        }

        // Compact the disk: Move file blocks to the leftmost free space
        for (int i = 0; i < disk.size(); i++) {
            if (disk.get(i) == -1) { // Found a free space
                int val = -1;
                while (val == -1 && !disk.isEmpty()) {
                    val = disk.remove(disk.size() - 1); // Take the last block
                }
                if (val != -1) {
                    disk.set(i, val); // Replace the free space with the file block
                }
            }
        }

        // Calculate the checksum
        long checksum = 0;
        for (int i = 0; i < disk.size(); i++) {
            if (disk.get(i) != -1) { // Skip free spaces
                checksum += (long) i * disk.get(i);
            }
        }

        return checksum;
    }

    static long solvePuzzle2(String line) {
        List<Integer> disk = new ArrayList<>();
        boolean isSpace = false;
        int id = 0;

        // Parse the input string and populate the disk
        for (char character : line.toCharArray()) {
            if (!Character.isDigit(character)) {
                System.err.println("Skipping invalid character: " + character);
                continue;
            }

            int num = Character.getNumericValue(character);
            if (isSpace) {
                for (int i = 0; i < num; i++) disk.add(-1); // -1 represents free space
            } else {
                for (int i = 0; i < num; i++) disk.add(id); // File blocks with ID
                id++;
            }
            isSpace = !isSpace;
        }

        // Compact the disk: Move whole files based on decreasing file ID
        for (int currentId = id - 1; currentId >= 0; currentId--) {
            // Find the starting index and length of the current file
            int start = -1, length = 0;
            for (int i = 0; i < disk.size(); i++) {
                if (disk.get(i) == currentId) {
                    if (start == -1) start = i;
                    length++;
                } else if (start != -1) {
                    break;
                }
            }

            // Find the leftmost free space large enough for the file
            int freeStart = -1, freeLength = 0;
            for (int i = 0; i < disk.size(); i++) {
                if (disk.get(i) == -1) {
                    if (freeStart == -1) freeStart = i;
                    freeLength++;
                } else {
                    if (freeLength >= length) break;
                    freeStart = -1;
                    freeLength = 0;
                }
            }

            // Move the file if a suitable space is found
            if (freeLength >= length && freeStart < start) {
                for (int i = 0; i < length; i++) {
                    disk.set(freeStart + i, currentId);
                    disk.set(start + i, -1);
                }
            }
        }

        // Calculate the checksum
        long checksum = 0;
        for (int i = 0; i < disk.size(); i++) {
            if (disk.get(i) != -1) { // Skip free spaces
                checksum += (long) i * disk.get(i);
            }
        }

        return checksum;
    }
}
