import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private JPanel stationPanel;
    private JMenuBar menuBar;
    private  JProgressBar progressBar;
    public Main() {
        stationPanel = new JPanel();
        menuBar = new JMenuBar();
        progressBar = new JProgressBar();

        // -- MENUBAR --
        JMenu start = new JMenu("start");
        JMenu pause = new JMenu("pause");
        JMenu reset = new JMenu("reset");

        menuBar.add(start);
        menuBar.add(pause);
        menuBar.add(reset);

        menuBar.setBackground(Color.LIGHT_GRAY);
        menuBar.setBorderPainted(false);
        // ------------------------------------------------------

        // -- PROGRESS BAR --
        progressBar.setStringPainted(true); // show percentage
        progressBar.setValue(100);
        progressBar.setString("100%");
        progressBar.setPreferredSize(new Dimension(1280, 30));
        progressBar.setBackground(Color.LIGHT_GRAY);
        progressBar.setForeground(Color.DARK_GRAY);
        // ------------------------------------------------------

        // -- STATION PANEL --
        stationPanel.setLayout(new BorderLayout());
        stationPanel.add(progressBar, BorderLayout.SOUTH);
        // ------------------------------------------------------

        this.setContentPane(stationPanel);
        this.setSize(1280, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(menuBar);
        this.setTitle("GasoMatic");
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        stationPanel.setSize(this.getSize());

    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }

}