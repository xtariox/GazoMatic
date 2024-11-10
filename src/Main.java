import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private StationPanel stationPanel;
    private JMenuBar menuBar;
    private  JProgressBar fuelGasGauge;
    private GasPump gasPump;

    public Main() {
        stationPanel = new StationPanel();
        menuBar = new JMenuBar();
        fuelGasGauge = new JProgressBar();
        gasPump = new GasPump(100);

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
        fuelGasGauge.setStringPainted(true); // show percentage
        fuelGasGauge.setMinimum(0);
        fuelGasGauge.setMaximum(100);
        fuelGasGauge.setPreferredSize(new Dimension(1280, 30));
        fuelGasGauge.setBackground(Color.LIGHT_GRAY);
        fuelGasGauge.setForeground(Color.DARK_GRAY);
        // ------------------------------------------------------

        // -- STATION PANEL --
        stationPanel.setLayout(new BorderLayout());
        stationPanel.add(fuelGasGauge, BorderLayout.SOUTH);
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

    // -- visually represent the fuel level of the pump on a fuel gauge, like a progress bar. --
    public void updateFuelGauge(){
        fuelGasGauge.setValue(gasPump.getCurrentFuelLevel());
    }

    public void dispenseFuel(int amount){
        gasPump.dispenseFuel(amount);
        updateFuelGauge();
    }

    public void refill(){
        gasPump.refill();
        updateFuelGauge();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }

}