package ParkeergarageSimulator.logic;

import ParkeergarageSimulator.view.*;
import java.util.*;

public abstract class AbstractModel {
    private List<AbstractView> views;

    /**
     * the basics for the models
     */
    public AbstractModel() {
        views = new ArrayList<AbstractView>();
    }

    /**
     * stuff to add a view to the all the views so that it can be easily accessed
     * @param view an abstractView object
     */
    public void addView(AbstractView view) {
        views.add(view);
    }

    /**
     * will update all the views when this is called
     */
    public void notifyViews() {
        for (AbstractView v : views) {
            v.updateView();
        }
    }
}
