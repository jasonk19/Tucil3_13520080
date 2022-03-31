package lib;

public class Node {
  public int[][] matrix;
  public int[][] parent;
  public int level;
  public int parentLevel;
  public int cost;
  public String move;

  public Node(int[][] matrix, int[][] parent, int level, int cost, String move) {
    this.matrix = matrix;
    this.parent = parent;
    this.level = level;
    this.parentLevel = level - 1;
    this.cost = cost;
    this.move = move;
  }

} 
