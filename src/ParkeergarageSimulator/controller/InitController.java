package ParkeergarageSimulator.controller;

import java.awt.event.*;
import java.text.ParseException;
import javax.swing.*;
import ParkeergarageSimulator.logic.*;

public class InitController extends AbstractController implements ActionListener{
    private static final long serialVersionUID = 8084081366423909672L;
    private JSpinner queue;
    private JSpinner rows;
    private JSpinner spots;
    private SpinnerModel queueModel;
    private SpinnerModel rowsModel;
    private SpinnerModel spotsModel;
    private JButton init;

    /**
     * InitController will create the initial view or at least prepare it to be shown on your screen
     * @param logic is an ParkeergarageLogic-object
     */
    public InitController(ParkeergarageLogic logic) {
        super(logic);
        setSize(90, 130);

        queueModel = new SpinnerNumberModel(0, 0, 10, 1);
        rowsModel = new SpinnerNumberModel(1, 1, 10, 1);
        spotsModel = new SpinnerNumberModel(1, 1, 10, 1);

        queue = new JSpinner(queueModel);
        rows = new JSpinner(rowsModel);
        spots = new JSpinner(spotsModel);
        init = new JButton("Init");

        init.addActionListener(this);

        this.setLayout(null);
        add(queue);
        add(rows);
        add(spots);
        add(init);
        int x = 10;
        int y = 10;
        int width = 70;
        int height = 30;
        rows.setBounds(x , y , width, height);
        spots.setBounds(x , y += (height + 2), width, height);
        queue.setBounds(x , y += (height + 2), width, height);
        init.setBounds(x , y += (height + 2), width, height);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*
        first we try to commit any "manual" changes, you can use the arrow-buttons or manually type a value
        manually typed values are, by default, not committed before we make an attempt at getting the value
        therefore we manually commit before we get
        */
        try {
            queue.commitEdit();
            rows.commitEdit();
            spots.commitEdit();
        }
        catch (ParseException pe) {
            pe.printStackTrace();
        }
        int f = 3;
        int r = (int)rows.getValue();
        int s = (int)spots.getValue();

        try {
            logic.setParkeergarage(f, r, s);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
