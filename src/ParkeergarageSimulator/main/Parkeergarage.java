package ParkeergarageSimulator.main;

import ParkeergarageSimulator.logic.*;
import ParkeergarageSimulator.view.*;
import ParkeergarageSimulator.controller.*;

import javax.swing.*;

import static ParkeergarageSimulator.controller.InitController.*;


public class Parkeergarage {
    private JFrame screen;
    private ParkeergarageLogic logic;;
    private AbstractView parkeergarageView;
    private AbstractController initController;
    private AbstractController runController;
    private AbstractController graphController;

    /**
     * This will create the window that will contain all buttons and number thingies
     */
    public Parkeergarage() {
        logic = new ParkeergarageLogic();
        screen = new JFrame("Parking Lot Simulator");
        initController = new InitController(logic);
        graphController = new GraphController(logic);
        parkeergarageView = new ParkeergarageView(logic);
        screen.getContentPane().add(initController);
        screen.getContentPane().add(graphController);
        screen.getContentPane().add(parkeergarageView);
        initController.setBounds(875, 5,90, 50);
        graphController.setBounds(700, 60, 640,100);
        parkeergarageView.setBounds(5,60,650, 400);
        if (!initRun) {
            createButtons();
        }
        screen.setSize(960, 500);
        screen.setResizable(false);
        screen.setLayout(null);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logic.setParkeergarage();
        screen.setVisible(true);
    }

    /**
     * and additional method to make stuff clearer
     */
    private void createButtons() {
        runController = new RunController(logic);
        screen.getContentPane().add(runController);
        runController.setBounds(5,5, 865,50);
    }
}
