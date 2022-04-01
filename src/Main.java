import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import lib.*;

public class Main {
  public static int size = 0;
  private static boolean solvable;

  public static int[][] readMatrix(String filename) throws IOException {
    int[][] matrix = null;

    BufferedReader buffer = new BufferedReader(new FileReader(filename));

    String line;
    int row = 0;

    while ((line = buffer.readLine()) != null) {
      String[] vals = line.trim().split("\\s+");

      // Instansiasi matriks
      if (matrix == null) {
        size = vals.length;
        matrix = new int[size][size];
      }

      for (int col = 0; col < size; col++) {
        matrix[row][col] = Integer.parseInt(vals[col]);
      }

      row++;
    }
    buffer.close();

    return matrix;
  }

  public static void main(String[] args) {
    int[][] matrix = null;
    int[][] solution = null;

    try {
      matrix = readMatrix("../test/test.txt");
      solution = readMatrix("solution.txt");
    } catch (IOException e) {
      System.out.println("Error: " + e.getMessage());
    }

    Solver puzzle = new Solver(matrix, solution, size);

    System.out.println("Initial Puzzle Position");
    puzzle.printInfo(matrix);
    System.out.println();

    if (puzzle.isGoalReachable()) {
      // Print the Solution
      System.out.println("Puzzle tersebut memiliki solusi");
      solvable = true;
    } else {
      // No Solution
      System.out.println("Puzzle tersebut tidak memiliki solusi");
      solvable = false;
    }

    if (solvable) {
      puzzle.Solve();
    }
  }
}
