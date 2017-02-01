package ParkeergarageSimulator.view;

import ParkeergarageSimulator.logic.*;
import java.awt.*;

public class ParkeergarageView extends AbstractView{
    private static final long serialVersionUID = -8200251211832614969L;

    public ParkeergarageView(ParkeergarageLogic logic) {
        super(logic);
        setSize(200,200);;
    }

    public void paintComponent(Graphics g) {


        int[][][] state = logic.getState();

        if (state == null) return;

        int margin = 100 - state.length * 2;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                for (int k = 0; k < state[j].length; k++) {
                    if (state[i][j][k] == 1) {
                        g.setColor(Color.RED);
                        g.fillRect(margin + 4 * i, margin + 4 * j, 3, 3);
                    }
                    if (state[i][j][k] == 2) {
                        g.setColor(Color.BLUE);
                        g.fillRect(margin + 4 * i, margin + 4 * j, 3, 3);
                    }
                }
            }
        }
    }
}
