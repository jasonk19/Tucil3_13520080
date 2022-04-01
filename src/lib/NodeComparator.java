package lib;

import java.util.Comparator;

// Class NodeComparator untuk mengOverride comparator pada PriorityQueue untuk
// menyesuaikan dengan objek buatan bernama Node
// Comparasi dilakukan dengan membandingkan cost:
// - cost kecil memiliki prioritas tertinggi dan dimasukkan menjadi elemen pertama prioqueue
public class NodeComparator implements Comparator<Node> {
  @Override
  public int compare(Node n1, Node n2) {
    if (n1.cost < n2.cost) {
      return -1;
    } else if (n1.cost > n2.cost) {
      return 1;
    }
    return 0;
  }
}
