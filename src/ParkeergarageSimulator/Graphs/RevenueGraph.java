package ParkeergarageSimulator.Graphs;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class RevenueGraph extends JPanel {
    public static List<Double> values;
    public static boolean visible = false;
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private static int width = 1000;
    private static int height = 400;
    private int yDivisions = 10;
    private int padding = 25;
    private int labelPadding = 25;
    private int pointDiameter = 4;
    private int pointRadius = pointDiameter / 2;
    private Color lineColor = new Color(44,102,230,180);
    private Color pointColor = new Color(100,100,100,180);
    private Color gridColor = new Color(200,200,200,200);

    /**
     * Constructor for the revenue graph
     * @param values a list-object that contains all values for the graph
     */
    private RevenueGraph(List<Double> values) {
        RevenueGraph.values = values;
    }

    /**
     * [duplicate code in OccupationGraph]
     * an custom iteration of the Graphics.paintComponent() method, this creates the entire graph
     * @param g a Graphics-object which is defined in the code below
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double)getWidth() - 2 * padding - labelPadding) / (values.size() - 1);
        double yScale = ((double)getHeight() - (2 * padding) - labelPadding) / (getMaxValue() - getMinValue());

        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < values.size(); i++){
            int x1 = (int)(i * xScale + padding + labelPadding);
            int y1 = (int)((getMaxValue() - values.get(i)) * yScale + padding);
            graphPoints.add(new Point(x1, y1));
        }

        g2.setColor(Color.white);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - (2 * padding) - labelPadding);
        g2.setColor(Color.black);
        //create hatch marks and grid lines for yAxis
        createGrid(g2);

        //and now we create them
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

        Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);

        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            g2.drawLine(x1,y1,x2,y2);
        }

        g2.setStroke(oldStroke);
        g2.setColor(pointColor);
        for (Point graphPoint : graphPoints) {
            int x = graphPoint.x - pointRadius;
            int y = graphPoint.y - pointRadius;
            int ovalW = pointDiameter;
            int ovalH = pointDiameter;
            g2.fillOval(x, y, ovalW, ovalH);
        }
    }

    /**
     * creates the grid which is to de drawn
     * @param g2 a 2d graphics thing
     */
    private void createGrid(Graphics2D g2) {
        for (int i = 0; i < yDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointDiameter + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / yDivisions + padding + labelPadding);
            int y1 = y0;

            if (values.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointDiameter, y0, getWidth() - padding, y1);
                g2.setColor(Color.black);
                String yLabel = ((int)((getMinValue() + (getMaxValue() - getMinValue()) * ((i * 1.0) / yDivisions)) * 100)) / 100.0 + "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        //and here the same for the xAxis
        for (int i = 0; i < values.size(); i++) {
            if (values.size() > 1) {
                int x0 = i * (getWidth() - (2 * padding) - labelPadding) / (values.size() - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointDiameter;
                if ((i % ((int) ((values.size() / 20.0)) + 1)) == 0) {
                    g2.setColor(gridColor);
                    g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointDiameter, x1, padding);
                    g2.setColor(Color.BLACK);
                    String xLabel = i + "";
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                g2.drawLine(x0, y0, x1, y1);
            }
        }
    }

    /**
     * gets the maximum values for the graph scale
     * @return the maximum value in values
     */
    private double getMaxValue() {
        double maxValue = Double.MIN_VALUE;
        for (Double score : values) {
            maxValue = Math.max(maxValue, score);
        }
        return maxValue;
    }

    /**
     * gets the minimum values for the graph scale
     * @return the minimum value in values
     */
    private double getMinValue() {
        double minValue = Double.MAX_VALUE;
        for (Double score : values) {
            minValue = Math.min(minValue, score);
        }
        return minValue;
    }

    /**
     * might set values for the values list if used
     * @param values all values for the values list as a list object
     */
    public void setValues(List<Double> values) {
        RevenueGraph.values = values;
        invalidate();
        this.repaint();
    }

    /**
     * and this is a getter for the values list
     * @return a list object containing all values in values
     */
    public List<Double> getValues() {
        return values;
    }

    /**
     * creates the window for the graph with the graph in it, unfortunately this does not update automatically
     * I was thinking about adding this, but alas...
     */
    public static void draw() {
        RevenueGraph graph = new RevenueGraph(values);
        graph.setPreferredSize(new Dimension(width, height));
        JFrame frame = new JFrame("Revenue Graph");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(graph);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(visible);
    }
}