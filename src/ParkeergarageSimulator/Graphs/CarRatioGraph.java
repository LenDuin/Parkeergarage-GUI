package ParkeergarageSimulator.Graphs;

import ParkeergarageSimulator.logic.*;
import ParkeergarageSimulator.view.AbstractView;
import java.awt.*;

public class CarRatioGraph extends AbstractView{
    private static final long serialVersionUID = -5554778963215545226L;
    private int totalCars;
    private int cardOwners;
    private int reservations;
    private int normalCars;

    public CarRatioGraph(ParkeergarageLogic logic) {
        super(logic);
    }

    public void paintComponent(Graphics g){
        int ratioCards = cardOwners / (totalCars + 1);
        int ratioReservations = reservations / (totalCars + 1);
        int ratioNormal = normalCars / (totalCars + 1);

        int cardsAngle = 360 * ratioCards;
        int reservationsAngle = 360 * ratioReservations;
        int normalAngle = 360 * ratioNormal;

        //draw the initial circle for the pie-chart
        g.setColor(Color.gray);
        g.fillArc(5,5,180,180,0,360);

        //the colors correspond with the colors used in the view with the squares
        g.setColor(Color.blue);
        g.fillArc(5,5,180,180,0, cardsAngle);
        g.setColor(Color.green);
        g.fillArc(5,5,180,180, cardsAngle, reservationsAngle);
        g.setColor(Color.red);
        g.fillArc(5,5,180,180, reservationsAngle + cardsAngle, normalAngle);
    }

    public void setTotalCars(int totalCars) {
        this.totalCars = totalCars;
    }

    public void setCardOwners(int cardOwners) {
        this.cardOwners = cardOwners;
    }

    public void setReservations(int reservations) {
        this.reservations = reservations;
    }

    public void setNormalCars(int normalCars) {
        this.normalCars = normalCars;
    }
}
