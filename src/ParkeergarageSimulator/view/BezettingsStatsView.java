package ParkeergarageSimulator.view;

import ParkeergarageSimulator.logic.ParkeergarageLogic;

import java.awt.*;
/**
 * [UNUSED]
 * intended for showing the graphs in the main window, but after deciding that new windows are more useful we ditched this
 */
public class BezettingsStatsView extends AbstractView{
    private static final long serialVersionUID = -7891669840482084995L;

    /**
     * the class which is the base for the views
     * @param logic the big, all-encapsulating logic thing that is basically the entire simulator
     */
    public BezettingsStatsView(ParkeergarageLogic logic) {
        super(logic);
    }

    public void paintComponent(Graphics g) {
//        g.setColor(Color.WHITE);
//        g.fillRect();
//
//        // int[][][] state = logic.getState();
//
//        // if (state == null) return;
//
//        // wat doet dit?
//    }
//
//    public int CarQueue() {
//        return CarQueue.size();
//    }
//
//    public int AbonnementPlekkenBezet() {
//        AbonnementPlekkenBezet() = SimulatorView.numberOfPassPlaces - SimulatorView.numberOfOpenPassPlaces;
//    }
//
//    public int ReserveringPlekkenBezet() {
//        // hier mot nog wat
    }
}
