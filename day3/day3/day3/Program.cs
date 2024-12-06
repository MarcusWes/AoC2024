using System;
using System.IO;
using System.Linq;
using System.Text.RegularExpressions;

namespace day3
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
			string pattern = @"mul\((\d+),(\d+)\)";
			Regex regex = new Regex(pattern);
			int sumRes = 0;

			List<Tuple<int, int>> numberPairs = new List<Tuple<int, int>>();


			foreach (string line in lines)
			{
				MatchCollection matches = regex.Matches(line);

				foreach (Match match in matches)
				{
					//Console.WriteLine("The match: " + match.Groups[0]);
					int x = int.Parse(match.Groups[1].Value);
					int y = int.Parse(match.Groups[2].Value);
					numberPairs.Add(new Tuple<int, int>(x, y));
					//Console.WriteLine($"({x}, {y})");
				}
			}

			sumRes = MultiplyAndSumTuples(numberPairs);

			return sumRes;
		}

		static int MultiplyAndSumTuples(List<Tuple<int, int>> numberPairs)
		{
			int result = 0;

			foreach (var pair in numberPairs)
			{
				result += pair.Item1 * pair.Item2;
			}

			return result;
		}

		static int Puzzle2(string[] lines)
		{
			string pattern = @"(mul|do|don't)\s*\((\d*),?(\d*)\)";
			Regex regex = new Regex(pattern);
			int sumRes = 0;
			bool enabled = true;

			List<Tuple<int, int>> numberPairs = new List<Tuple<int, int>>();

			foreach (string line in lines)
			{
				MatchCollection matches = regex.Matches(line);
				foreach (Match match in matches)
				{
					string functionName = match.Groups[1].Value;

					if (functionName == "mul")
					{
						if (enabled)
						{
							int x = int.Parse(match.Groups[2].Value);
							int y = int.Parse(match.Groups[3].Value);
							numberPairs.Add(new Tuple<int, int>(x, y));
						}
					}
					else if (functionName == "do")
					{
						enabled = true;
					}
					else { enabled = false; }
					

				}
			}

			sumRes = MultiplyAndSumTuples(numberPairs);
			return sumRes;
		}

	}
}