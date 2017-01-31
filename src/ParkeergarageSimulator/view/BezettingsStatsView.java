package ParkeergarageSimulator.view;

import java.awt.*;
import ParkeergarageSimulator.logic.*;

public class BezettingsStatsView extends AbstractView{
    private static final long serialVersionUID = -7891669840482084995L;

    public BezettingsStatsView(ParkeergarageLogic logic) {
        super(logic);
        setSize(200,200);
    }

    public void paintComponent(Graphics g) {
    }
}
