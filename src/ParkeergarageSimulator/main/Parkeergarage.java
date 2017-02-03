package ParkeergarageSimulator.main;

import ParkeergarageSimulator.logic.*;
import ParkeergarageSimulator.view.*;
import ParkeergarageSimulator.controller.*;

import javax.swing.*;

import static ParkeergarageSimulator.controller.InitController.*;

public class Parkeergarage {
    private JFrame screen;
    private AbstractView parkeergarageView;
//    private AbstractView settingsView;
    private ParkeergarageLogic logic;;
    private AbstractController initController;
    private AbstractController runController;

    public Parkeergarage() {
        logic = new ParkeergarageLogic();
        screen = new JFrame("ParkeergarageSimulator");
        initController = new InitController(logic);
        screen.getContentPane().add(initController);
        initController.setBounds(1271, 5,90, 768);
        if (!initRun) {
            createButtons();
        }
        screen.setSize(1366, 768);
        screen.setResizable(false);
        screen.setLayout(null);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setVisible(true);
    }

    public void createButtons() {
        runController = new RunController(logic);
        parkeergarageView = new ParkeergarageView(logic);
//        settingsView = new BezettingsStatsView(logic);
        screen.getContentPane().add(parkeergarageView);
//        screen.getContentPane().add(settingsView);
        screen.getContentPane().add(runController);
        parkeergarageView.setBounds(5,55,1271, 673);
//        settingsView.setBounds(0,0,0,0);
        runController.setBounds(5,5, 1200,50);
    }
}
