using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace day5
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

                // Create lists for each section
                List<(int, int)> pairs = new List<(int, int)>();
                List<List<int>> numberLists = new List<List<int>>();

                // Process the first section (pairs)
                int i = 0;
                for (; i < lines.Length; i++)
                {
                    string line = lines[i].Trim();
                    if (string.IsNullOrEmpty(line)) break;

                    string[] parts = line.Split('|');
                    if (parts.Length == 2 && int.TryParse(parts[0], out int first) && int.TryParse(parts[1], out int second))
                    {
                        pairs.Add((first, second));
                    }
                }

                // Process the second section (comma-separated lists)
                for (i = i + 1; i < lines.Length; i++) // Skip the empty line
                {
                    string line = lines[i].Trim();
                    if (!string.IsNullOrEmpty(line))
                    {
                        string[] numbers = line.Split(',');
                        List<int> currentList = new List<int>();
                        foreach (var number in numbers)
                        {
                            if (int.TryParse(number, out int value))
                            {
                                currentList.Add(value);
                            }
                        }
                        numberLists.Add(currentList);
                    }
                }

                // Output results
                Console.WriteLine($"Puzzle 1: {Puzzle1(pairs, numberLists)}");
                Console.WriteLine($"Puzzle 2: {Puzzle2(pairs, numberLists)}");
            }
            catch (FileNotFoundException)
            {
                Console.WriteLine($"Error: Input file '{inputFile}' not found.");
            }
        }

        static int Puzzle1(List<(int, int)> pairs, List<List<int>> numberLists)
        {
            int totalMiddleSum = 0;

            foreach (var numbers in numberLists)
            {
                if (IsValidUpdate(numbers, pairs))
                {
                    int middlePage = numbers[numbers.Count / 2];
                    totalMiddleSum += middlePage;
                }
            }

            return totalMiddleSum;
        }

        static bool IsValidUpdate(List<int> numbers, List<(int, int)> pairs)
        {
            var pageIndex = numbers.Select((page, index) => (page, index)).ToDictionary(p => p.page, p => p.index);

            foreach (var pair in pairs)
            {
                if (pageIndex.ContainsKey(pair.Item1) && pageIndex.ContainsKey(pair.Item2))
                {
                    if (pageIndex[pair.Item1] > pageIndex[pair.Item2])
                    {
                        return false;
                    }
                }
            }

            return true;
        }

        static int Puzzle2(List<(int, int)> pairs, List<List<int>> numberLists)
        {
            int totalMiddleSum = 0;

            foreach (var numbers in numberLists)
            {
                if (!IsValidUpdate(numbers, pairs))
                {
                    var reordered = TopologicalSort(numbers, pairs);

                    int middlePage = reordered[reordered.Count / 2];
                    totalMiddleSum += middlePage;
                }
            }

            return totalMiddleSum;
        }

        // Sorting done by AI
        static List<int> TopologicalSort(List<int> numbers, List<(int, int)> pairs)
        {
            // Create a graph representation
            Dictionary<int, List<int>> graph = numbers.ToDictionary(x => x, x => new List<int>());
            Dictionary<int, int> inDegree = numbers.ToDictionary(x => x, x => 0);

            // Build the graph and in-degree counts
            foreach (var pair in pairs)
            {
                if (graph.ContainsKey(pair.Item1) && graph.ContainsKey(pair.Item2))
                {
                    graph[pair.Item1].Add(pair.Item2);
                    inDegree[pair.Item2]++;
                }
            }

            // Topological sorting using Kahn's Algorithm
            Queue<int> queue = new Queue<int>();
            foreach (var node in numbers)
            {
                if (inDegree[node] == 0)
                {
                    queue.Enqueue(node);
                }
            }

            List<int> sortedOrder = new List<int>();
            while (queue.Count > 0)
            {
                int current = queue.Dequeue();
                sortedOrder.Add(current);

                foreach (var neighbor in graph[current])
                {
                    inDegree[neighbor]--;
                    if (inDegree[neighbor] == 0)
                    {
                        queue.Enqueue(neighbor);
                    }
                }
            }

            return sortedOrder;
        }
    }

}
