package ParkeergarageSimulator.main;

import ParkeergarageSimulator.logic.*;
import ParkeergarageSimulator.view.*;
import ParkeergarageSimulator.controller.*;

import javax.swing.*;

import static ParkeergarageSimulator.controller.InitController.*;

public class Parkeergarage {
    private JFrame screen;
    private AbstractView parkeergarageView;
    private AbstractView graphView;
//    private AbstractView settingsView;
    private ParkeergarageLogic logic;;
    private AbstractController initController;
    private AbstractController runController;

    public Parkeergarage() {
        logic = new ParkeergarageLogic();
        screen = new JFrame("ParkeergarageSimulator");
        initController = new InitController(logic);
        parkeergarageView = new ParkeergarageView(logic);
        graphView = new GraphView(logic);
        screen.getContentPane().add(initController);
        screen.getContentPane().add(parkeergarageView);
        screen.getContentPane().add(graphView);
        initController.setBounds(1300, 5,90, 400);
        parkeergarageView.setBounds(5,60,650, 400);
        graphView.setBounds(660, 60, 630, 400);
        if (!initRun) {
            createButtons();
        }
        screen.setSize(1400, 500);
        screen.setResizable(false);
        screen.setLayout(null);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setVisible(true);
    }

    public void createButtons() {
        runController = new RunController(logic);
//        settingsView = new BezettingsStatsView(logic);
//        screen.getContentPane().add(settingsView);
        screen.getContentPane().add(runController);
//        settingsView.setBounds(0,0,0,0);
        runController.setBounds(5,5, 1271,50);
    }
}
