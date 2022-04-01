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
    private Timer timer;
    private int iter;

    public PuzzleSolverGUI(String title) {
        super(title);

        this.titleLabel.setFont(new Font("Monospace", Font.PLAIN, 28));
        this.puzzleView.setFont(new Font("Monospace", Font.PLAIN, 30));
        this.puzzleStatus.setFont(new Font("Monospace", Font.PLAIN, 18));
        this.moveLabel.setFont(new Font("Monospace", Font.BOLD, 16));
        this.SOLVEButton.setEnabled(false);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setSize(420, 420);
        this.setResizable(false);

        VIEWPUZZLEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String initialPuzzle;
                matrix = null;
                solution = null;

                String fileName = inputField.getText();

                try {
                    matrix = readMatrix("./test/" + fileName);
                    solution = readMatrix("./src/solution.txt");
                } catch (IOException err) {
                    System.out.println("Error: " + err.getMessage());
                }

                puzzle = new Solver(matrix, solution, size);

                Solver.path.clear();
                Solver.moves.clear();
                moveLabel.setText("");

                if (puzzle.isGoalReachable()) {
                    puzzleStatus.setText("Puzzle memiliki solusi");
                    solvable = true;
                } else {
                    puzzleStatus.setText("Puzzle tidak memiliki solusi");
                    solvable = false;
                }

                if (solvable) {
                    initialPuzzle = printMatrixToString(matrix);
                    puzzleView.setText(initialPuzzle);
                    SOLVEButton.setEnabled(true);
                }

            }
        });

        SOLVEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                puzzleStatus.setText("Calculating...");

                puzzle.Solve();

                String executionTime = Long.toString(Solver.execTime);
                puzzleStatus.setText("Execution Time: " + executionTime +"ms");

                iter = Solver.path.size() - 1;

                timer.start();

                SOLVEButton.setEnabled(false);
            }
        });

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (iter == 0) {
                    timer.stop();
                }
                puzzleView.setText(printMatrixToString(Solver.path.get(iter).matrix));
                moveLabel.setText(Solver.path.get(iter).move);
                iter--;
            }
        });
    }

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

    public static void main(String[] args) {
        JFrame frame = new PuzzleSolverGUI("15 Puzzle Solver");
        frame.setVisible(true);
    }
}
