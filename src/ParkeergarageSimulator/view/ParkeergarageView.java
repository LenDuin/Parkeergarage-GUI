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


        Car[][][] state = logic.getState();

        if (state == null) {
            return;
        }

        int margin = 100 - state.length * 2;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                for (int k = 0; k < state[i][j].length; k++) {
                    if (state[i][j][k] == null) {
                        g.setColor(Color.WHITE);
                        g.fillRect(margin+20*i,margin+20*j,15,15);
                    } else {
                        if (state[i][j][k].getHasToPay()) {
                            g.setColor(Color.RED);
                            g.fillRect(margin + 20 * i, margin + 20 * j, 15, 15);
                        } else {
                            g.setColor(Color.BLUE);
                                g.fillRect(margin + 20 * i, margin + 20 * j, 15, 15);
                        }
                    }
                }
            }
        }
    }
}
