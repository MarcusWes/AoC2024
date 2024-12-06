using System;
using System.IO;

namespace day4
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


		//
		// Day 4 is quite AI-assisted
		//
		static int Puzzle1(string[] lines)
		{
			int count = 0;
			int rows = lines.Length;
			int cols = lines[0].Length;

			for (int row = 0; row < rows; row++)
			{
				for (int col = 0; col < cols; col++)
				{
					foreach (var direction in new[]
					{
						(1, 0), (-1, 0), (0, 1), (0, -1),
						(1, 1), (1, -1), (-1, 1), (-1, -1)
					})
					{
						if (IsXmasPresent(lines, row, col, direction, rows, cols))
						{
							count++;
						}
					}
				}
			}

			return count;
		}

		static bool IsXmasPresent(string[] lines, int row, int col, (int, int) direction, int rows, int cols)
		{
			int dx = direction.Item1;
			int dy = direction.Item2;

			return (row + 3 * dx >= 0 && row + 3 * dx < rows &&
					col + 3 * dy >= 0 && col + 3 * dy < cols &&
					lines[row][col] == 'X' &&
					lines[row + dx][col + dy] == 'M' &&
					lines[row + 2 * dx][col + 2 * dy] == 'A' &&
					lines[row + 3 * dx][col + 3 * dy] == 'S');
		}

		//
		// Struggled a lot with puzzle2 help from AI and inspiration from:
		// https://github.com/MartinZikmund/advent-of-code/blob/main/src/AdventOfCode.Puzzles/2024/04/Part2/AoC2024Day4Part2.cs
		//
		static int Puzzle2(string[] lines)
		{
			int count = 0;
			int rows = lines.Length;
			int cols = lines[0].Length;

			for (int row = 0; row < rows; row++)
			{
				for (int col = 0; col < cols; col++)
				{
					if (CheckDiagonals(lines, row, col))
					{
						count++;
					}
				}
			}

			return count;
		}

		static bool CheckDiagonals(string[] lines, int row, int col)
		{
			var directions = new[]
			{
				(1, 1), (-1, -1), (1, -1), (-1, 1)
			};

			int foundCount = 0;

			foreach (var dir in directions)
			{
				int dx = dir.Item1;
				int dy = dir.Item2;

				if (CheckWord(lines, row - dx, col - dy, dx, dy, "MAS"))
				{
					foundCount++;
				}
			}

			return foundCount == 2;
		}

		static bool CheckWord(string[] lines, int row, int col, int dx, int dy, string word)
		{
			int rows = lines.Length;
			int cols = lines[0].Length;

			for (int i = 0; i < word.Length; i++)
			{
				int newRow = row + i * dx;
				int newCol = col + i * dy;

				if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols ||
					lines[newRow][newCol] != word[i])
				{
					return false;
				}
			}

			return true;
		}
	}
}
