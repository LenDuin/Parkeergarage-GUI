package ParkeergarageSimulator.controller;

import java.awt.event.*;
import javax.swing.*;
import ParkeergarageSimulator.logic.*;

public class InitController extends AbstractController implements ActionListener{
    private static final long serialVersionUID = 8084081366423909672L;
    private JButton init;
    public static boolean initRun = false;

    /**
     * InitController will create the initial view or at least prepare it to be shown on your screen
     * @param logic is an ParkeergarageLogic-object
     */
    public InitController(ParkeergarageLogic logic) {
        super(logic);
        init = new JButton("Reset");

        init.addActionListener(this);

        this.setLayout(null);
        add(init);
        int x = 5;
        int y = 0;
        int width = 70;
        int height = 30;
        init.setBounds(x , y, width, height);

//        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        initRun = true;
        logic.setParkeergarage();
    }
}
