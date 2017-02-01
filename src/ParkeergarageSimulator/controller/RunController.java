package ParkeergarageSimulator.controller;

import ParkeergarageSimulator.exception.ParkeergarageException;
import ParkeergarageSimulator.logic.*;

import javax.swing.*;
import java.awt.event.*;
import java.text.ParseException;

public class RunController extends AbstractController implements ActionListener{
    private static final long serialVersionUID = -8776795932665582315L;
    private JButton stepOne;
    private JSpinner steps;
    private SpinnerModel stepsModel;
    private JButton startSteps;
    private JButton stopSteps;

    public RunController(ParkeergarageLogic logic) {
        super(logic);
        setSize(450, 40);
        stepOne = new JButton("One step");
        stepOne.addActionListener(this);
        stepsModel = new SpinnerNumberModel(1,1,1000,1);
        steps = new JSpinner(stepsModel);
        startSteps = new JButton("Start");
        startSteps.addActionListener(this);
        stopSteps = new JButton("Stop");
        stopSteps.addActionListener(this);

        this.setLayout(null);
        add(stepOne);
        add(steps);
        add(startSteps);
        add(stopSteps);
        int x = 0;
        int y = 0;
        int width = 90;
        int height = 35;
        stepOne.setBounds(x, y, width, height);
        steps.setBounds(x = x + width, y, width, height);
        startSteps.setBounds(x = x + width, y, width, height);
        stopSteps.setBounds(x = x + width, y, width, height);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            steps.commitEdit();
        }
        catch (ParseException pe) {
            pe.printStackTrace();
        }
        if (e.getSource() == stepOne) {
            try {
                logic.doStep();
            }
            catch (ParkeergarageException pex) {
                pex.printStackTrace();
            }
            return;
        }
        if (e.getSource() == startSteps) {
            try {
                logic.doSteps((int)steps.getValue());
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == stopSteps) {
            logic.stopSteps();
        }
    }
}
