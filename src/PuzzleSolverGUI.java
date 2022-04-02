import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import lib.*;

public class PuzzleSolverGUI extends JFrame {
    private static int size = 0;
    private static boolean solvable;
    private int[][] matrix;
    private int[][] solution;
    private Solver puzzle;
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JTextField inputField;
    private JButton VIEWPUZZLEButton;
    private JButton SOLVEButton;
    private JPanel outputPanel;
    private JLabel puzzleStatus;
    private JLabel puzzleView;
    private JLabel moveLabel;
    private JLabel verticesRaised;
    private JLabel kurangxLabel;
    private final Timer timer;
    private int iter;
    private String executionTime;

    public PuzzleSolverGUI(String title) {
        super(title);

        this.titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 28));
        this.puzzleView.setFont(new Font("SansSerif", Font.PLAIN, 40));
        this.puzzleStatus.setFont(new Font("SansSerif", Font.PLAIN, 16));
        this.moveLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        this.verticesRaised.setFont(new Font("SansSerif", Font.BOLD, 16));
        this.inputField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        this.kurangxLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        this.SOLVEButton.setEnabled(false);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setSize(800, 640);


        // Action saat menekan tombol VIEWPUZZLE
        VIEWPUZZLEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // inisialisasi String, matrix, dan matrix solusi/goal
                String initialPuzzle;
                String calculateKurang;
                matrix = null;
                solution = null;
                Solver.antrian.clear();
                Solver.path.clear();
                Solver.solutions.clear();


                // Mengambil text dari input field sebagai fileName
                String fileName = inputField.getText();

                // Membaca matrix dan solusi
                try {
                    matrix = readMatrix("../test/" + fileName);
                    solution = readMatrix("solution.txt");
                } catch (IOException err) {
                    System.out.println("Error: " + err.getMessage());
                }

                // inisialisasi Solver
                puzzle = new Solver(matrix, solution, size);

                moveLabel.setText("");
                verticesRaised.setText("");

                // Memeriksa jika puzzle dapat mencapai goal atau tidak
                if (puzzle.isGoalReachable()) {
                    puzzleStatus.setText("Puzzle memiliki solusi");
                    solvable = true;
                } else {
                    puzzleStatus.setText("Puzzle tidak memiliki solusi");
                    solvable = false;
                }

                // Menunjukkan puzzle awal dan value dari kurang(i) + x
                initialPuzzle = printMatrixToString(matrix);
                calculateKurang = printKurang();
                puzzleView.setText(initialPuzzle);
                kurangxLabel.setText(calculateKurang);

                // Jika solvable, maka SOLVEButton dapat ditekan
                if (solvable) {
                    SOLVEButton.setEnabled(true);
                }

            }
        });

        // Action saat menekan SOLVEButton
        SOLVEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                puzzleStatus.setText("Calculating...");

                // jalankan method solve puzzle
                puzzle.Solve();

                executionTime = Long.toString(Solver.execTime);

                if (Solver.path.size() == 0) {
                    verticesRaised.setText("Vertices Raised: " + 0);
                    puzzleStatus.setText("<html>Steps taken: " + 0 + " steps<br/>Execution Time: " + executionTime + "ms</html>");
                    puzzleView.setText(printMatrixToString(matrix));
                    moveLabel.setText("none");
                } else {
                    iter = Solver.path.size() - 1;
                    timer.start();
                }


                SOLVEButton.setEnabled(false);
            }
        });

        timer = new Timer(800, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (iter == 0) {
                    timer.stop();
                    verticesRaised.setText("Vertices Raised: " + Solver.solutions.size());
                    puzzleStatus.setText("<html>Steps taken: " + Solver.path.size() + " steps<br/>Execution Time: " + executionTime + "ms</html>");
                }
                // menampilkan puzzle dengan delay
                puzzleView.setText(printMatrixToString(Solver.path.get(iter).matrix));
                moveLabel.setText(Solver.path.get(iter).move);
                iter--;
            }
        });
    }
    // Fungsi untuk membaca matrix dari suatu file
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
    // Fungsi untuk mengubah suatu matrix menjadi string yang dapat ditampilkan di GUI
    public String printMatrixToString(int[][] matrix) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j] == 16) {
                    sb.append("| _ |");
                } else if (matrix[i][j] < 10) {
                    sb.append("| " + matrix[i][j] + " |");
                } else {
                    sb.append("|" + matrix[i][j] + "|");
                }
            }
            sb.append("<br />");
        }
        sb.append("</html>");

        return sb.toString();
    }

    public String printKurang() {
        StringBuilder sb = new StringBuilder();
        int position = 0;
        int count = 0;
        int sum = 0;
        int x = puzzle.valueOfX();
        sb.append("<html>");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int temp = matrix[i][j];
                count = puzzle.KURANG(temp, position);
                sb.append("KURANG(" + temp + ") = " + count + "<br/>");
                position += 1;
                sum += count;
            }
        }
        sb.append("Jumlah dari KURANG(i) + X = " + (sum + x));
        sb.append("</html>");

        return sb.toString();
    }

    public static void main(String[] args) {
        JFrame frame = new PuzzleSolverGUI("15 Puzzle Solver");
        frame.setVisible(true);
    }
}
