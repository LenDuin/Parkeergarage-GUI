package ParkeergarageSimulator.main;

import ParkeergarageSimulator.logic.*;
import ParkeergarageSimulator.view.*;
import ParkeergarageSimulator.controller.*;

import javax.swing.*;

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
        screen.getContentPane().add(settingsView);
        screen.getContentPane().add(runController);
        screen.getContentPane().add(initController);
        parkeergarageView.setBounds(5,runController.getHeight() + 5,screen.getWidth() - initController.getWidth(), screen.getHeight() - (runController.getHeight() + 45));
        settingsView.setBounds(5,5,200,200);
        runController.setBounds(5,5, screen.getWidth(),50);
        initController.setBounds(screen.getWidth() - (initController.getWidth() + 5), parkeergarageView.getY(),90, screen.getHeight());
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setVisible(true);
    }
}
