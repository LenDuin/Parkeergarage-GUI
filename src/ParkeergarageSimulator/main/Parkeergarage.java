package ParkeergarageSimulator.main;

import ParkeergarageSimulator.logic.*;
import ParkeergarageSimulator.view.*;
import ParkeergarageSimulator.controller.*;

import javax.swing.*;
import java.awt.*;

public class Parkeergarage {
    private JFrame screen;
    private AbstractView parkeergarageView;
    private AbstractView settingsView;
    private ParkeergarageLogic logic;
    private AbstractController initController;
    private AbstractController runController;

    public Parkeergarage() {
        logic = new ParkeergarageLogic();
        initController = new InitController(logic);
        runController = new RunController(logic);
        parkeergarageView = new ParkeergarageView(logic);
        settingsView = new BezettingsStatsView(logic);

        screen = new JFrame("ParkeergarageSimulator");
        screen.setSize(1366, 768);
        screen.setResizable(false);
        screen.setLayout(null);
        screen.getContentPane().add(parkeergarageView);
//        screen.getContentPane().add(settingsView);
        screen.getContentPane().add(runController);
        screen.getContentPane().add(initController);
        parkeergarageView.setBounds(5,55,1271, 673);
//        settingsView.setBounds(0,0,0,0);
        runController.setBounds(5,5, 500,50);
        initController.setBounds(1271, 5,90, 768);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setVisible(true);
    }
}
