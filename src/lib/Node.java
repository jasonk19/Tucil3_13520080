package lib;

// Class Node sebagai identitas simpul setiap puzzle
public class Node {
  // matrix untuk menyimpan bentuk puzzle
  public int[][] matrix;
  // parent untuk menyimpan puzzle induknya
  public int[][] parent;
  // level untuk menyimpan tingkatan/kedalaman dari simpul puzzle
  public int level;
  // cost untuk menyimpan cost pada simpul puzzle
  public int cost;
  // move untuk menyimpan move apa yang digunakan untuk mencapai simpul this
  public String move;

  // Constructor dari Node dengan parameter
  public Node(int[][] matrix, int[][] parent, int level, int cost, String move) {
    this.matrix = matrix;
    this.parent = parent;
    this.level = level;
    this.cost = cost;
    this.move = move;
  }

} 
