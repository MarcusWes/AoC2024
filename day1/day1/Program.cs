using System;
using System.IO;
using System.Linq;

namespace day1
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
            //Splitting the columns and lines into left and right hand list whilst removing empty spaces
            int[] leftList = lines.Select(line => int.Parse(line.Split(new[] { ' ' }, StringSplitOptions.RemoveEmptyEntries)[0])).ToArray();
            int[] rightList = lines.Select(line => int.Parse(line.Split(new[] { ' ' }, StringSplitOptions.RemoveEmptyEntries)[1])).ToArray();
            int distance = 0;

            Array.Sort(leftList);
            Array.Sort(rightList);

            for (int i = 0; i < leftList.Length; i++)
            {
                distance += Math.Abs(leftList[i] - rightList[i]);
            }

            return distance;
        }

        static int Puzzle2(string[] lines)
        {
            //Splitting the columns and lines into left and right hand list whilst removing empty spaces
            int[] leftList = lines.Select(line => int.Parse(line.Split(new[] { ' ' }, StringSplitOptions.RemoveEmptyEntries)[0])).ToArray();
            int[] rightList = lines.Select(line => int.Parse(line.Split(new[] { ' ' }, StringSplitOptions.RemoveEmptyEntries)[1])).ToArray();
            int similarityScore = 0;


            for (int i = 0; i < leftList.Length; i++)
            {
                int leftOccur = 0;
                for (int j = 0; j < rightList.Length; j++)
                {
                    if(leftList[i] == rightList[j])
                    {
                        leftOccur += 1;
                    }
                }

                similarityScore += leftList[i] * leftOccur;
            }

            return similarityScore;
        }
    }
}