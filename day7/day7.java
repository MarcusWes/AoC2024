import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class day7 {

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

    static long Puzzle1(String[] lines) 
    {
        long solvableCount = 0;

        for (String line : lines) 
        {
            String[] parts = line.split(":");
            long targetValue = Long.parseLong(parts[0].trim());
            String[] numbers = parts[1].trim().split(" ");

            int[] nums = new int[numbers.length];
            for (int i = 0; i < numbers.length; i++)
            {
                nums[i] = Integer.parseInt(numbers[i]);
            }

            if (canSolve(targetValue, nums))
            {
                solvableCount += targetValue;
            }
        }
        return solvableCount;
    }

    static boolean canSolve(long targetValue, int[] nums)
    {
        int n = nums.length;
        int operatorCount = n - 1; // Number of operator slots
        
        // << n = 2^, meaning we can get the total amount of combinations
        //int totalCombinations = 1 << operatorCount;
        int totalCombinations = (int) Math.pow(2, operatorCount); 

        for(int i = 0; i < totalCombinations; i++)
        {
            long result = nums[0];
            int currentCombination = i;

            for (int j = 0; j < operatorCount; j++) {
                if ((currentCombination & 1) == 0) { // Choose +
                    result += nums[j + 1];
                } else { // Choose *
                    result *= nums[j + 1];
                }
                currentCombination >>= 1; // Move to the next operator
            }
            // Check if the result matches the target
            if (result == targetValue) {
                return true;
            }
        }

        return false;
    }

    static long Puzzle2(String[] lines)
    {
        long solvableCount = 0;

        for (String line : lines) 
        {
            String[] parts = line.split(":");
            long targetValue = Long.parseLong(parts[0].trim());
            String[] numbers = parts[1].trim().split(" ");

            int[] nums = new int[numbers.length];
            for (int i = 0; i < numbers.length; i++)
            {
                nums[i] = Integer.parseInt(numbers[i]);
            }

            if (canSolve2(targetValue, nums))
            {
                solvableCount += targetValue;
            }
        }
        return solvableCount;
    }

    static boolean canSolve2(long targetValue, int[] nums) {
        int n = nums.length;
        int operatorCount = n - 1;
        int totalCombinations = (int) Math.pow(3, operatorCount); // 3 operators for each slot
    
        for (int i = 0; i < totalCombinations; i++) {
            long result = nums[0];
            int currentCombination = i;
    
            for (int j = 0; j < operatorCount; j++) {
                int operator = currentCombination % 3; // Get the operator for this slot
                currentCombination /= 3; // Move to the next operator
    
                switch (operator) {
                    case 0: // +
                        result += nums[j + 1];
                        break;
                    case 1: // *
                        result *= nums[j + 1];
                        break;
                    case 2: // ||
                        result = Long.parseLong(String.valueOf(result) + nums[j + 1]);
                        break;
                }
            }
            if (result == targetValue) {
                return true;
            }
        }
        return false;
    }
}