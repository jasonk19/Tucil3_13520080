package lib;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Solver {
  private int[][] matrix;
  private int[][] solution;
  private int size;
  private static PriorityQueue<Node> antrian = new PriorityQueue<Node>(new NodeComparator());
  private static List<Node> solutions = new ArrayList<Node>();
  private static List<Node> path = new ArrayList<Node>();
  private static List<String> moves = new ArrayList<String>();

  public Solver(int[][] matrix, int[][] solution, int size) {
    this.matrix = matrix;
    this.solution = solution;
    this.size = size;
  }

  public int Elmt(int i, int j) {
    return this.matrix[i][j];
  }

  public int Sol(int i, int j) {
    return this.solution[i][j];
  }

  public int getEmptyRow() {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (Elmt(i, j) == 0) {
          return i;
        }
      }
    }
    return -999;
  }

  public int getEmptyCol() {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (Elmt(i, j) == 0) {
          return j;
        }
      }
    }
    return -999;
  }

  public void printInfo(int[][] matrix) {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (matrix[i][j] == 0) {
          System.out.print("   ");
        } else if (matrix[i][j] < 10) {
          System.out.print(" " + matrix[i][j] + " ");
        } else {
          System.out.print(matrix[i][j] + " ");
        }
      }
      System.out.println();
    }
  }

  public void printMoves() {
    String[] moves = getPossibleMoves();
    System.out.print("[");
    for (int i = 0; i < moves.length; i++) {
      System.out.print(moves[i]);
      if (i != moves.length - 1) {
        System.out.print(", ");
      }
    }
    System.out.print("]");
    System.out.println();
  }

  public int valueOfX() {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (Elmt(i, j) == 0) {
          if ((i+1) % 2 == 1) {
            if ((j+1) % 2 == 0) {
              return 1;
            } else {
              return 0;
            }
          } else if ((i+1) % 2 == 0) {
            if ((j+1) % 2 == 1) {
              return 1;
            } else {
              return 0;
            }
          }
        }
      }
    }
    return -999;
  }

  public int[] convertToOneD(int[][] matrix) {
    List<Integer> list = new ArrayList<Integer>();
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        list.add(Elmt(i, j));
      }
    }

    int[] oneDArray = new int[list.size()];
    for (int i = 0; i < oneDArray.length; i++) {
      oneDArray[i] = list.get(i);
    }

    return oneDArray;
  }

  public int KURANG(int x, int position) {
    int count = 0;
    int[] list = convertToOneD(matrix);
    for (int i = position; i < list.length; i++) {
      int temp = list[i];
      if (temp == 0) {
        temp = 16;
      }
      if (temp < x) {
        count++;
      }
    }

    return count;
  }
 
  public int valueOfKurang() {
    int sum = 0;
    int count = 0;
    int position = 0;
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        int temp = Elmt(row, col);
        if (temp == 0) {
          temp = 16;
        }
        count = KURANG(temp, position);
        position += 1;
        sum += count;
      }
    }

    return sum;
  }

  public boolean isGoalReachable() {
    // isGoalReachable menggunakan rumus KURANG(i) + X;
    
    // Pencarian nilai x
    int x = valueOfX();
    // Pencarian total dari KURANG(i)
    int kurang = valueOfKurang();

    return (kurang + x) % 2 == 0;
  }

  public String[] getPossibleMoves() {
    List<String> list = new ArrayList<String>();
    String up = "up";
    String right = "right";
    String down = "down";
    String left = "left";
    int row = getEmptyRow();
    int col = getEmptyCol();

    if (row == 0 && col == 0) {
      list.add(right);
      list.add(down);
    } else if (row == 0 && col == size - 1) {
      list.add(down);
      list.add(left);
    } else if (row == size - 1 && col == 0) {
      list.add(up);
      list.add(right);
    } else if (row == size - 1 && col == size - 1) {
      list.add(up);
      list.add(left);
    } else if (row == 0 && col > 0 && col < size) {
      list.add(right);
      list.add(down);
      list.add(left);
    } else if (col == 0 && row > 0 && row < size) {
      list.add(up);
      list.add(right);
      list.add(down);
    } else if (row == size - 1 && col > 0 && col < size) {
      list.add(up);
      list.add(right);
      list.add(left);
    } else if (col == size - 1 && row > 0 && row < size) {
      list.add(up);
      list.add(down);
      list.add(left);
    } else {
      list.add(up);
      list.add(right);
      list.add(down);
      list.add(left);
    }

    String[] arrayOfMoves = new String[list.size()];
    for (int i = 0; i < arrayOfMoves.length; i++) {
      arrayOfMoves[i] = list.get(i);
    }

    return arrayOfMoves;
  }

  public int notInPositionCount() {
    int count = 0;
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (Elmt(i, j) != Sol(i, j)) {
          count++;
        }
      }
    }

    return count;
  }

  public int[][] copyMatrix(int[][] inputMat) {
    int[][] newMatrix = new int[size][size];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        newMatrix[i][j] = inputMat[i][j];
      }
    }

    return newMatrix;
  }

  public void swapEmpty(int rowSrc, int colSrc, int rowDest, int colDest) {
    int temp = this.matrix[rowSrc][colSrc];
    this.matrix[rowSrc][colSrc] = this.matrix[rowDest][colDest];
    this.matrix[rowDest][colDest] = temp;
  }

  public void move(String command) {
    int rowEmpty = getEmptyRow();
    int colEmpty = getEmptyCol();
    if (command == "up") {
      swapEmpty(rowEmpty, colEmpty, rowEmpty - 1, colEmpty);
    } else if (command == "down") {
      swapEmpty(rowEmpty, colEmpty, rowEmpty + 1, colEmpty);
    } else if (command == "right") {
      swapEmpty(rowEmpty, colEmpty, rowEmpty, colEmpty + 1);
    } else if (command == "left") {
      swapEmpty(rowEmpty, colEmpty, rowEmpty, colEmpty - 1);
    }
  }

  public int countCost(int[][] inputMat, int depth) {
    int count = 0;
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (inputMat[i][j] != -1) {
          if (inputMat[i][j] != this.solution[i][j]) {
            count++;
          }
        }
      }
    }
    return count + depth;
  }

  public boolean notSolution(int[][] matrix) {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (matrix[i][j] != solution[i][j]) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean isSame(int[][] matrix, int[][] initialMatrix) {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (matrix[i][j] != initialMatrix[i][j]) {
          return false;
        }
      }
    }
    return true;
  }

  public boolean returnTheSame(String first, String second) {
    if (first == "left" && second == "right") {
      return true;
    } else if (first == "right" && second == "left") {
      return true;
    } else if (first == "down" && second == "up") {
      return true;
    } else if (first == "up" && second == "down") {
      return true;
    } 
    return false;
  }

  // Main Solving Method
  public void Solve() {
    int level = 1;
    int cost;
    String firstMove = "null";
    long startTime = System.currentTimeMillis();
    while(notSolution(this.matrix)) {
      int[][] initialMatrix = copyMatrix(this.matrix);
      String[] possibleMoves = getPossibleMoves();

      for (int i = 0; i < possibleMoves.length; i++) {
        move(possibleMoves[i]);
        if (!returnTheSame(firstMove, possibleMoves[i])) {
          cost = countCost(this.matrix, level);
          antrian.add(new Node(this.matrix, initialMatrix, level, cost, possibleMoves[i]));
        }
        this.matrix = copyMatrix(initialMatrix);
      }
      Node nextMove = antrian.poll();
      firstMove = nextMove.move;

      if (nextMove.level < level || nextMove.level > level) {
        level = nextMove.level;
      }

      level += 1;
      
      solutions.add(nextMove);

      this.matrix = copyMatrix(nextMove.matrix);

      if (!notSolution(nextMove.matrix)) {
        path.add(nextMove);
      }
    }
    long stopTime = System.currentTimeMillis();

    for (int i = solutions.size() - 1; i >= 0; i--) {
      if (isSame(solutions.get(i).matrix, path.get(path.size() - 1).parent)) {
        path.add(solutions.get(i));
      }
    }

    for (int i = path.size() - 1; i >= 0; i--) {
      printInfo(path.get(i).matrix);
      moves.add(path.get(i).move);
      System.out.println("Move: " + path.get(i).move);
      System.out.println();
    }

    System.out.println("Moves to reach solution: " + moves);

    System.out.println("Execution Time: " + (stopTime - startTime) + "ms");
  }

}
 