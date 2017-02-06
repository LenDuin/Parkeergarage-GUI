package ParkeergarageSimulator.view;

import ParkeergarageSimulator.logic.*;
import java.awt.*;

public class ParkeergarageView extends AbstractView{
    private static final long serialVersionUID = -8200251211832614969L;

    public ParkeergarageView(ParkeergarageLogic logic) {
        super(logic);
    }

    public void paintComponent(Graphics g) {
        Car[][][] state = logic.getState();

        if (state == null) {
            return;
        }

        int hmargin = 10;
        int vmargin = 50;
        g.setColor(Color.decode("#dddddd"));
        g.fillRoundRect(0, 0, 1000, 1000, 10, 10);
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (i > 0 && j == 0) {
                    hmargin += 150;
                }
                if (j % 2 == 0) {
                    hmargin += 20;
                }
                for (int k = 0; k < state[i][j].length; k++) {
                    if (state[i][j][k] == null) {
                        g.setColor(Color.WHITE);
                    } else {
                        g.setColor(state[i][j][k].getColor());
                    }
                    g.fillRect(hmargin + 20 * j, vmargin + 11 * k, 15, 10);
                }
            }
        }
    }
}
