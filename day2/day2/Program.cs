using System;
using System.IO;
using System.Linq;

namespace day2
{
    internal class Program
    {
        static void Main(string[] args)
        {
            string inputFile = "input.txt";
            if (args.Length > 0)
            {
                inputFile = args[0];
            }

            try
            {
                string[] lines = File.ReadAllLines(inputFile);
                Console.WriteLine($"Puzzle 1: {Puzzle1(lines)}");
                Console.WriteLine($"Puzzle 2: {Puzzle2(lines)}");
            }
            catch (FileNotFoundException)
            {
                Console.WriteLine($"Error: Input file '{inputFile}' not found.");
            }
        }

        static int Puzzle1(string[] lines)
        {
            List<int[]> numbersList = new List<int[]>();
            int numberOfSafeReports = 0;

            foreach (string line in lines)
            {
                // Split the line by spaces, parse each number, and store in an array
                int[] numbers = line.Split(' ').Select(int.Parse).ToArray();

                // Add the array to the list
                numbersList.Add(numbers);
            }

            foreach (int[] numbers in numbersList)
            {
                bool reportIsSafe = true;
                bool isIncreasing = (numbers[0] < numbers[1]);
                bool isDecreasing = (numbers[0] > numbers[1]);

                for (int i = 0; i < numbers.Length - 1; i++)
                {
                    if (isIncreasing)
                    {
                        if (!CheckIfIncreasing(numbers[i], numbers[i + 1]))
                        {
                            reportIsSafe = false;
                            break;
                        }
                    }
                    else if (isDecreasing)
                    {
                        if (!CheckIfDecreasing(numbers[i], numbers[i + 1]))
                        {
                            reportIsSafe = false;
                            break;
                        }
                    }
                    else
                    {
                        reportIsSafe = false;
                    }
                }
                if (reportIsSafe)
                {
                    numberOfSafeReports++;
                }
            }

            return numberOfSafeReports;
        }

        static bool CheckIfIncreasing(int number, int secondNumber)
        {
            bool isIncOrDec = false;

            //Check if numbers are increasing
            if (number < secondNumber && Math.Abs(number - secondNumber) < 4 && Math.Abs(number - secondNumber) > 0)
            {
                isIncOrDec = true;
            }

            return isIncOrDec;
        }

        static bool CheckIfDecreasing(int number, int secondNumber)
        {
            bool isDecreasing = false;

            if (number > secondNumber && Math.Abs(number - secondNumber) < 4 && Math.Abs(number - secondNumber) > 0)
            {
                isDecreasing = true;
            }

            return isDecreasing;
        }

        static int Puzzle2(string[] lines)
        {
            List<int[]> numbersList = new List<int[]>();
            int numberOfSafeReports = 0;

            foreach (string line in lines)
            {
                // Split the line by spaces, parse each number, and store in an array
                int[] numbers = line.Split(' ').Select(int.Parse).ToArray();

                // Add the array to the list
                numbersList.Add(numbers);
            }

            foreach (int[] numbers in numbersList)
            {
                // Check if safe without modification
                bool reportIsSafe = IsReportSafe(numbers); 

                if (!reportIsSafe)
                {
                    for (int i = 0; i < numbers.Length; i++)
                    {
                        // Remove level i
                        int[] modifiedNumbers = numbers.Where((n, index) => index != i).ToArray(); 
                        if (IsReportSafe(modifiedNumbers))
                        {
                            reportIsSafe = true;
                            break;
                        }
                    }
                }

                if (reportIsSafe)
                {
                    numberOfSafeReports++;
                }
            }

            return numberOfSafeReports;
        }

        static bool IsReportSafe(int[] numbers)
        {
            bool isIncreasing = numbers[0] < numbers[1];

            for (int i = 0; i < numbers.Length - 1; i++)
            {
                if (isIncreasing)
                {
                    if (!CheckIfIncreasing(numbers[i], numbers[i + 1]))
                    {
                        return false;
                    }
                }
                else
                {
                    if (!CheckIfDecreasing(numbers[i], numbers[i + 1]))
                    {
                        return false;
                    }
                }
            }

            return true;
        }
    }
}