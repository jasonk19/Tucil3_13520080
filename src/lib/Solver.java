package lib;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

// Class Solver sebagai class utama untuk mencari solusi
public class Solver {
  // variable matrix untuk menyimpan matrix yang dites
  private int[][] matrix;
  // variable solution untuk menyimpan matrix keadaan terakhir (goal dari puzzle)
  private int[][] solution;
  // variable size sebagai ukuran matrix (4x4)
  private int size;
  // antrian sebagai prioqueue berdasarkan cost dari Node
  public static PriorityQueue<Node> antrian = new PriorityQueue<Node>(new NodeComparator());
  // List of solutions dari hasil pop prioqueue
  public static List<Node> solutions = new ArrayList<Node>();
  // variable execution time
  public static long execTime;
  // List of path berupa jalur dari matrix awal ke goal
  public static List<Node> path = new ArrayList<Node>();

  // Constructor dari Solver
  public Solver(int[][] matrix, int[][] solution, int size) {
    this.matrix = matrix;
    this.solution = solution;
    this.size = size;
  }

  // Get elemen matrix
  public int Elmt(int i, int j) {
    return this.matrix[i][j];
  }

  // Get elemen solusi
  public int Sol(int i, int j) {
    return this.solution[i][j];
  }

  // get index row dari elemen empty
  public int getEmptyRow() {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (Elmt(i, j) == 16) {
          return i;
        }
      }
    }
    return -999;
  }

  // get index column dari elemen empty
  public int getEmptyCol() {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (Elmt(i, j) == 16) {
          return j;
        }
      }
    }
    return -999;
  }

  // print matrix ke console/terminal
  public void printInfo(int[][] matrix) {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (matrix[i][j] == 16) {
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

  // menentukan value dari x
  // jika sel kosong di posisi yang diarsir, maka x = 1;
  // else x = 0;
  public int valueOfX() {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (Elmt(i, j) == 16) {
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

  // mengubah matrix menjadi array 1 dimensi
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

  // kalkulasi value dari kurang,
  // yaitu banyaknya ubin yang valuenya kurang dari ubin acuan
  public int KURANG(int x, int position) {
    int count = 0;
    int[] list = convertToOneD(matrix);
    for (int i = position; i < list.length; i++) {
      int temp = list[i];
      if (temp < x) {
        count++;
      }
    }

    return count;
  }

  // mencari hasil dari sumOf(KURANG)
  public int valueOfKurang() {
    int sum = 0;
    int count = 0;
    int position = 0;
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        int temp = Elmt(row, col);
        count = KURANG(temp, position);
        position += 1;
        sum += count;
      }
    }

    return sum;
  }

  // Menentukan apakah puzzle dapat mencapai goal atau tidak
  // - jika KURANG + x bernilai genap maka reachable
  // - else tidak reachable
  public boolean isGoalReachable() {
    // isGoalReachable menggunakan rumus KURANG(i) + X;
    
    // Pencarian nilai x
    int x = valueOfX();
    // Pencarian total dari KURANG(i)
    int kurang = valueOfKurang();

    return (kurang + x) % 2 == 0;
  }

  // get kemungkinan moves yang dapat dilakukan oleh sel kosong berdasarkan posisinya
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

  // mengassign suatu matrix dengan matrix lain
  public int[][] copyMatrix(int[][] inputMat) {
    int[][] newMatrix = new int[size][size];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        newMatrix[i][j] = inputMat[i][j];
      }
    }

    return newMatrix;
  }

  // untuk melakukan swapping antara sel kosong dan sel sekitarnya berdasarkan move
  public void swapEmpty(int rowSrc, int colSrc, int rowDest, int colDest) {
    int temp = this.matrix[rowSrc][colSrc];
    this.matrix[rowSrc][colSrc] = this.matrix[rowDest][colDest];
    this.matrix[rowDest][colDest] = temp;
  }

  // command move untuk menggerakkan sel kosong
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

  // perhitungan cost suatu puzzle
  public int countCost(int[][] inputMat, int depth) {
    int count = 0;
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (inputMat[i][j] != 16) {
          if (inputMat[i][j] != this.solution[i][j]) {
            count++;
          }
        }
      }
    }
    return count + depth;
  }

  // melakukan pengecekan puzzle dengan solusi/goal
  // - jika ada elemen yang tidak sama, maka notSolution mengembalikan true
  // - jika semua elemen sama, maka matrix tersebut adalah solution
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

  // memeriksa apakah kedua matrix yang dibandingkan sama
  // jika sama maka true, else false
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

  // mengembalikan true atau false berdasarkan masukkan command move
  // agar puzzle yang akan diekspansi tidak melakukan move yang akan mengembalikan dia
  // ke posisi semula
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
    // while matrix tidak sama dengan solusi/goal, loop
    while(notSolution(this.matrix)) {
      int[][] initialMatrix = copyMatrix(this.matrix);
      String[] possibleMoves = getPossibleMoves();

      for (int i = 0; i < possibleMoves.length; i++) {
        move(possibleMoves[i]);
        if (!returnTheSame(firstMove, possibleMoves[i])) {
          cost = countCost(this.matrix, level);
          // Memasukkan Node puzzle ke antrian
          antrian.add(new Node(this.matrix, initialMatrix, level, cost, possibleMoves[i]));
        }
        this.matrix = copyMatrix(initialMatrix);
      }
      // Mengambil Node puzzle pertama pada prioqueue
      Node nextMove = antrian.poll();
      firstMove = nextMove.move;

      if (nextMove.level < level || nextMove.level > level) {
        level = nextMove.level;
      }

      level += 1;
      // Memasukkan Node puzzle yang menjadi move berikutnya ke list of solusi
      solutions.add(nextMove);

      this.matrix = copyMatrix(nextMove.matrix);

      // jika puzzle nextMove berupa solusi, maka dimasukkan ke path
      if (!notSolution(nextMove.matrix)) {
        path.add(nextMove);
      }
    }
    long stopTime = System.currentTimeMillis();

    // pencarian jalur dengan menghubungkan puzzle dengan parent dari elemen terakhir path
    for (int i = solutions.size() - 1; i >= 0; i--) {
      if (isSame(solutions.get(i).matrix, path.get(path.size() - 1).parent)) {
        path.add(solutions.get(i));
      }
    }
    // perhitungan ekesekusi waktu
    execTime = stopTime - startTime;

  }

}
 