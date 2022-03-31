import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import lib.*;

public class Main {
  public static int size = 0;

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
    Scanner input = new Scanner(System.in);

    int[][] matrix = null;
    int[][] solution = null;
    
    // System.out.print("Enter puzzle file name to test (*.txt): ");
    // String filename = input.nextLine();

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
      puzzle.Solve();
    } else {
      // No Solution
      System.out.println("Puzzle tersebut tidak memiliki solusi");
    }
  }
}
