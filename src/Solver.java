import java.util.ArrayList;
import java.util.List;

public class Solver {
  private int[][] matrix;
  private int size;

  public Solver(int[][] matrix, int size) {
    this.matrix = matrix;
    this.size = size;
  }

  public int Elmt(int i, int j) {
    return matrix[i][j];
  }

  public int getEmptyRow() {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (Elmt(i, j) == -1) {
          return i;
        }
      }
    }
    return -999;
  }

  public int getEmptyCol() {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (Elmt(i, j) == -1) {
          return j;
        }
      }
    }
    return -999;
  }

  public void printInfo() {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (Elmt(i, j) == -1) {
          System.out.print("  ");
        } else {
          System.out.print(Elmt(i, j) + " ");
        }
      }
      System.out.println();
    }

    System.out.println("Row: " + size);
    System.out.println("Column: " + size);
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
        if (Elmt(i, j) == -1) {
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
      if (temp == -1) {
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
        if (temp == -1) {
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
    String down = "down";
    String left = "left";
    String right = "right";
    int row = getEmptyRow();
    int col = getEmptyCol();

    if (row == 0 && col == 0) {
      list.add(down);
      list.add(right);
    } else if (row == 0 && col == size) {
      list.add(down);
      list.add(left);
    } else if (row == size && col == 0) {
      list.add(up);
      list.add(right);
    } else if (row == size && col == size) {
      list.add(up);
      list.add(left);
    } else if (row == 0 && col > 0 && col < size) {
      list.add(down);
      list.add(left);
      list.add(right);
    } else if (col == 0 && row > 0 && row < size) {
      list.add(up);
      list.add(right);
      list.add(down);
    } else if (row == size && col > 0 && col < size) {
      list.add(up);
      list.add(left);
      list.add(right);
    } else if (col == size && row > 0 && row < size) {
      list.add(up);
      list.add(left);
      list.add(down);
    } else {
      list.add(up);
      list.add(down);
      list.add(right);
      list.add(left);
    }

    String[] arrayOfMoves = new String[list.size()];
    for (int i = 0; i < arrayOfMoves.length; i++) {
      arrayOfMoves[i] = list.get(i);
    }

    return arrayOfMoves;
  }

}
