package ParkeergarageSimulator.logic;

import ParkeergarageSimulator.view.*;
import java.util.*;

public abstract class AbstractModel {
    private List<AbstractView> views;

    /**
     * The constructor of this class here it creates a array for vieuws
     *
     */
    public AbstractModel() {
        views = new ArrayList<AbstractView>();
    }

    /**
     * the method where it adds a view to the array
     *
     * @param view Here it does call the view from the class AbstractView and it adds the view that is in the class
     */
    public void addView(AbstractView view) {
        views.add(view);
    }

    /**
     * The method where it updates the view.
     *
     */
    public void notifyViews() {
        for (AbstractView v : views) {
            v.updateView();
        }
    }
}
