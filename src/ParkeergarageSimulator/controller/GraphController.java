package ParkeergarageSimulator.controller;

import ParkeergarageSimulator.Graphs.OccupationGraph;
import ParkeergarageSimulator.Graphs.RevenueGraph;
import ParkeergarageSimulator.logic.ParkeergarageLogic;
import ParkeergarageSimulator.logic.Simulator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphController extends AbstractController implements ActionListener{
    private static final long serialVersionUID = 7897987874654135474L;

    private JLabel label;
    private JButton dayOccupation;
    private JButton dayRevenue;

    private int x = 5;
    private int y = 0;
    private int width = 200;
    private int height = 30;

    /**
     * the class which is only there to create the buttons to show the graphs we currently have
     * @param logic the big, all-encapsulating logic thing that is basically the entire simulator
     */
    public GraphController(ParkeergarageLogic logic) {
        super(logic);

        label = new JLabel("Graphs: ");
        dayOccupation = new JButton("Daily Occupation");
        dayRevenue = new JButton("Total Revenue");

        this.setLayout(null);

        add(label);
        add(dayOccupation);
        add(dayRevenue);

        label.setBounds(x,y,width,height);
        dayOccupation.setBounds(x, y = y + height + 5, width, height);
        dayRevenue.setBounds(x, y + height + 5, width, height);

        dayOccupation.addActionListener(this);
        dayRevenue.addActionListener(this);
    }

    /**
     * this method checks which of the buttons has been pressed
     * @param e an action event which can have two different value for now, occupation or revenue
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dayOccupation) {
            OccupationGraph.visible = true;
            OccupationGraph.values = Simulator.getDayVisitors();
            OccupationGraph.draw();
        }
        if (e.getSource() == dayRevenue) {
            RevenueGraph.visible = true;
            RevenueGraph.values = Simulator.getDayRevenueList();
            RevenueGraph.draw();
        }
    }
}
