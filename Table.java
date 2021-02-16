import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

/**
 * The type Table.
 */
public class Table {
    /**
     * The Radios.
     */
//the radios of the circle
    private int radios;
    /**
     * The Num points.
     */
//the number of points in the table
    private int numPoints;
    /**
     * The Times factor.
     */
//the times factor to the lines
    private double timesFactor;
    /**
     * The To add points.
     */
/*
        the checks for the setting;
     */
    private boolean toAddPoints;
    /**
     * The To grow radios.
     */
    private boolean toGrowRadios;
    /**
     * The To grow factor.
     */
    private boolean toGrowFactor;
    /**
     * The Ticks.
     */
    private int ticks;

    /**
     * Instantiates a new Table.
     *
     * @param radios      the radios
     * @param numPoints   the num points
     * @param timesFactor the times factor
     */
    public Table(int radios, int numPoints, double timesFactor) {
        this.radios = radios;
        this.numPoints = numPoints;
        this.timesFactor = timesFactor;
        this.toAddPoints = true;
        this.toGrowRadios = true;
        this.toGrowFactor = true;
        this.ticks = 0;
    }

    /**
     * Update time factor.
     */
    private void updateTimeFactor() {
        if (this.timesFactor <= Main.MIN_FACTOR) {
            this.toGrowFactor = true;
        }
        if (this.timesFactor > Main.MAX_FACTOR) {
            this.toGrowFactor = true;
        }
        if (this.toGrowFactor) {
            this.timesFactor +=0.01;
        } else {
            this.timesFactor -= 0.01;
        }
    }

    /**
     * Update num points.
     */
    private void updateNumPoints() {
        if (this.numPoints <= Main.MIN_POINTS) {
            this.toAddPoints = true;
        }
        if (this.numPoints > Main.MAX_POINTS) {
            this.toAddPoints = false;
        }
        if (this.toAddPoints) {
            this.numPoints++;
        } else {
            this.numPoints--;
        }
    }

    /**
     * Update grow radios.
     */
    private void updateGrowRadios() {
        if (this.radios > Main.MAX_R) {
            this.toGrowRadios = false;
        }
        if (this.radios <= Main.MIN_R) {
            this.toGrowRadios = true;
        }
        if (this.toGrowRadios) {
            this.radios++;
        } else {
            this.radios--;
        }
    }

    /**
     * Update table.
     */
    public void updateTable() {
        if(ticks%Main.FPS==0){
            updateNumPoints();
        }
        updateTimeFactor();
        updateGrowRadios();
        ticks++;
    }

    /**
     * Gets vector from index.
     *
     * @param index the index
     *
     * @return the vector from index
     */
    private Vector getVectorFromIndex(double index) {
        double angle = (index * (2 * Math.PI / this.numPoints)) + Math.PI;
        double x = this.radios * Math.cos(angle) + Main.TranslateToCenter;
        double y = this.radios * Math.sin(angle) + Main.TranslateToCenter;
        Vector v = new Vector();
        v.add(x);
        v.add(y);
        return v;
    }

    /**
     * Render.
     *
     * @param graphics the graphics
     */
    public void render(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        for (int i = 0; i < this.numPoints; i++) {
            Vector a = getVectorFromIndex(i);
            Vector b = getVectorFromIndex(i * timesFactor);
            Double x1 = (Double) a.get(0);
            Double y1 = (Double) a.get(1);
            Double x2 = (Double) b.get(0);
            Double y2 = (Double) b.get(1);
            graphics.drawLine(x1.intValue(), y1.intValue(), x2.intValue(), y2.intValue());
        }
    }
}
