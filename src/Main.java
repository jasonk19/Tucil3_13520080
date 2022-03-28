import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
    int[][] matrix = null;
    try {
      matrix = readMatrix("noSolution.txt");
    } catch (IOException e) {
      System.out.println("Error: " + e.getMessage());
    }

    Solver puzzle = new Solver(matrix, size);

    puzzle.printInfo();
    puzzle.printMoves();

    if (puzzle.isGoalReachable()) {
      // Print the Solution
      System.out.println("Puzzle tersebut memiliki solusi");
    } else {
      // No Solution
      System.out.println("Puzzle tersebut tidak memiliki solusi");
    }
  }
}
