package control;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UI extends JFrame {
    private ArrayList<ArrayList<String>> matrix;
    private JPanel matrixPanel;

    public UI(ArrayList<ArrayList<String>> matrix, int ag1x, int ag1y, int ag2x, int ag2y) {
        this.matrix = matrix;
        this.matrixPanel = new JPanel(new GridLayout(matrix.size(), matrix.get(0).size()));

        Color color;
        for (int i=0;i<matrix.size();i++) {
            for (int j=0;j<matrix.size();j++) {
                if ( i == ag1x && j == ag1y) {
                    color = Color.GREEN;
                }else if ( i == ag2x && j == ag2y) {
                    color = Color.BLUE;
                }else {
                    color = switch (matrix.get(i).get(j)) {
                        case "1" -> Color.YELLOW;
                        case "3" -> Color.RED;
                        default -> Color.WHITE;
                    };

                }
                JPanel cell = new JPanel();
                cell.setBackground(color);
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Adiciona borda preta
                matrixPanel.add(cell);
            }
        }

        this.add(matrixPanel);
        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void interfaceUpdate(ArrayList<ArrayList<String>> matrix,int ag1x, int ag1y, int ag2x, int ag2y) {
        this.matrix = matrix;
        matrixPanel.removeAll();

        Color color;
        for (int i=0;i<matrix.size();i++) {
            for (int j=0;j<matrix.size();j++) {
                if ( i == ag1x && j == ag1y) {
                    color = Color.GREEN;
                }else if ( i == ag2x && j == ag2y) {
                    color = Color.BLUE;
                }else {
                    color = switch (matrix.get(i).get(j)) {
                        case "1" -> Color.YELLOW;
                        case "3" -> Color.RED;
                        default -> Color.WHITE;
                    };

                }
                JPanel cell = new JPanel();
                cell.setBackground(color);
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Adiciona borda preta
                matrixPanel.add(cell);
            }
        }

        if (matrixPanel.getComponentCount() > 0) {
            matrixPanel.revalidate();
            matrixPanel.repaint();
        }
    }

}