public class Node {
  public int[][] matrix;
  public int level;
  public int cost;
  public String move;

  public Node(int[][] matrix, int level, int cost, String move) {
    this.matrix = matrix;
    this.level = level;
    this.cost = cost;
    this.move = move;
  }

} 
