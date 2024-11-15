package com.gazomatic;

import com.gazomatic.station.StationPanel;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private StationPanel stationPanel;
    private JMenuBar menuBar;

    public Main() {
        stationPanel = new StationPanel();
        menuBar = new JMenuBar();

        // -- MENUBAR --
        JMenu resume = new JMenu("resume");
        JMenu pause = new JMenu("pause");

        resume.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Time.resume();
            }
        });

        pause.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Time.pause();
            }
        });

        menuBar.add(resume);
        menuBar.add(pause);

        menuBar.setBackground(Color.LIGHT_GRAY);
        menuBar.setBorderPainted(false);
        // ------------------------------------------------------

        this.setContentPane(stationPanel);
        this.setSize(1280, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(menuBar);
        this.setTitle("GazoMatic");
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        stationPanel.setSize(this.getSize());

        // -- STATION PANEL --
        stationPanel.init();
        // ------------------------------------------------------
    }

    public static void main(String[] args) {
        // Set look and feel to the system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Update the time every frame
        Timer timer = new Timer(16, e -> Time.update());
        timer.start();

        SwingUtilities.invokeLater(Main::new);
    }
}