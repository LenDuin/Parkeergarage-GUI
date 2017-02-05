package ParkeergarageSimulator.main;

import ParkeergarageSimulator.Graphs.CarRatioGraph;
import ParkeergarageSimulator.logic.*;
import ParkeergarageSimulator.view.*;
import ParkeergarageSimulator.controller.*;

import javax.swing.*;

import static ParkeergarageSimulator.controller.InitController.*;

public class Parkeergarage {
    private JFrame screen;
    private ParkeergarageLogic logic;;
    private AbstractView parkeergarageView;
    private AbstractView carRatioView;
    private AbstractController initController;
    private AbstractController runController;
    private AbstractController graphController;

    public Parkeergarage() {
        logic = new ParkeergarageLogic();
        screen = new JFrame("ParkeergarageSimulator");
        initController = new InitController(logic);
        graphController = new GraphController(logic);
        parkeergarageView = new ParkeergarageView(logic);
        carRatioView = new CarRatioGraph(logic);
        screen.getContentPane().add(initController);
        screen.getContentPane().add(graphController);
        screen.getContentPane().add(parkeergarageView);
        screen.getContentPane().add(carRatioView);
        initController.setBounds(875, 5,90, 50);
        graphController.setBounds(700, 60, 640,100);
        parkeergarageView.setBounds(5,60,650, 400);
        carRatioView.setBounds(700,170,200,200);
        if (!initRun) {
            createButtons();
        }
        screen.setSize(960, 500);
        screen.setResizable(false);
        screen.setLayout(null);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setVisible(true);
    }

    public void createButtons() {
        runController = new RunController(logic);
        screen.getContentPane().add(runController);
        runController.setBounds(5,5, 865,50);
    }
}
