package ParkeergarageSimulator.view;

import ParkeergarageSimulator.logic.*;
import javax.swing.*;

public abstract class AbstractView extends JPanel{
    private static final long serialVersionUID = 6437976554496769048L;
    protected ParkeergarageLogic logic;

    public AbstractView(ParkeergarageLogic logic) {
        this.logic = logic;
        logic.addView(this);
    }

    public ParkeergarageLogic getModel() {
        return logic;
    }

    public void updateView() {
        repaint();
    }
}
