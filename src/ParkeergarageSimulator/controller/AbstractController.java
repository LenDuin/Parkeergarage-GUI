package ParkeergarageSimulator.controller;

import ParkeergarageSimulator.logic.*;
import javax.swing.*;

public abstract class AbstractController extends JPanel{
    protected ParkeergarageLogic logic;

    public AbstractController(ParkeergarageLogic logic) {
        this.logic = logic;
    }
}
