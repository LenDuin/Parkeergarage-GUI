package ParkeergarageSimulator.controller;

import ParkeergarageSimulator.exception.ParkeergarageException;
import ParkeergarageSimulator.logic.*;

import javax.swing.*;
import java.awt.event.*;
import java.text.ParseException;

public class RunController extends AbstractController implements ActionListener{
    private static final long serialVersionUID = -8776795932665582315L;

    private JButton stepOne;
    private JButton startSteps;
    private JButton stopSteps;

    private JLabel delayLabel;
    private JLabel reservationsLabel;
    private JLabel clockLabel;
    private static JLabel clock;

    private JSpinner steps;
    private static JSpinner delay;
    private static JSpinner reservations;

    private SpinnerModel stepsModel;
    private SpinnerModel delayModel;
    private SpinnerModel reservationsModel;

    private int x = 0;
    private int y = 0;
    private int width = 90;
    private int height = 35;

    private boolean created = false;

    public RunController(ParkeergarageLogic logic) {
        super(logic);
        //JButtons
        stepOne = new JButton("One step");
        startSteps = new JButton("Start");
        stopSteps = new JButton("Stop");

        //SpinnerModels
        stepsModel = new SpinnerNumberModel(10080,1,Integer.MAX_VALUE,100);
        delayModel = new SpinnerNumberModel(1, 0, 1000, 10);
        reservationsModel = new SpinnerNumberModel(120, 0, 540, 10);

        //JSpinners
        steps = new JSpinner(stepsModel);
        delay = new JSpinner(delayModel);
        reservations = new JSpinner(reservationsModel);

        //JLabels
        delayLabel = new JLabel("Delay: ");
        reservationsLabel = new JLabel("Reservations: ");
        clockLabel = new JLabel("SimTime: ");
        clock = new JLabel("maandag, 00:00");

        //actionListeners
        stepOne.addActionListener(this);
        startSteps.addActionListener(this);
        stopSteps.addActionListener(this);

        if (!created) {
            createController();
        }
//        setVisible(true);
    }

    public static int getDelay() {
        return (int)delay.getValue();
    }

    public static int getReservations() {
        return (int)reservations.getValue();
    }

    private void createController() {
        this.setLayout(null);

        add(stepOne);
        add(steps);
        add(startSteps);
        add(stopSteps);
        add(delayLabel);
        add(delay);
        add(reservationsLabel);
        add(reservations);
        add(clockLabel);
        add(clock);

        stepOne.setBounds(x, y, width, height);
        steps.setBounds(x = width + 5, y, width, height);
        startSteps.setBounds(x * 2, y, width, height);
        stopSteps.setBounds(x * 3, y, width, height);
        delayLabel.setBounds(x * 4, y, width - 30, height);
        delay.setBounds(x = x * 5 - 50, y, width, height);
        reservationsLabel.setBounds(x = x + width + 5, y, width, height);
        reservations.setBounds(x = x + width, y, width, height);
        clockLabel.setBounds(x = x + width + 5, y, width, height);
        clock.setBounds(x = x + width - 30, y, width + 20, height);

        created = true;
    }

    public static void setClock(String time) {
        clock.setText(time);
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
