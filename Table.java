import biuoop.DrawSurface;
import java.awt.Color;
import java.util.Vector;

public class Table {
    //the radios of the circle
    private int radios;
    //the number of points in the table
    private int numPoints;
    //the times factor to the lines
    private double timesFactor;
    /*
        the checks for the setting;
     */
    private boolean toAddPoints;
    private boolean toGrowRadios;
    private boolean toGrowFactor;

    public Table(int radios, int numPoints, double timesFactor) {
        this.radios = radios;
        this.numPoints = numPoints;
        this.timesFactor = timesFactor;
        this.toAddPoints = true;
        this.toGrowRadios = true;
        this.toGrowFactor = true;
    }

    public void updateTable() {
        if (this.timesFactor <= Main.MIN_FACTOR) {
            this.toGrowFactor = true;
        }
        if (this.timesFactor > Main.MAX_FACTOR) {
            this.toGrowFactor = true;
        }
        if (this.toGrowFactor) {
            this.timesFactor += 0.1;
        } else {
            this.timesFactor -= 0.1;
        }
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

    private Vector getVectorFromIndex(double index) {
        double angle = (index * (2 * Math.PI / this.numPoints)) + Math.PI;
        double x = this.radios * Math.cos(angle) + Main.TranslateToCenter;
        double y = this.radios * Math.sin(angle) + Main.TranslateToCenter;
        Vector v = new Vector();
        v.add(x);
        v.add(y);
        return v;
    }

    public void drawTable(DrawSurface drawSurface) {
        drawSurface.setColor(Color.WHITE);
        drawSurface.drawCircle(Main.WIDTH / 2, Main.HEIGHT / 2, this.radios);
        for (int i = 0; i < this.numPoints; i++) {
            drawSurface.setColor(Color.WHITE);
            Vector a = getVectorFromIndex(i);
            Vector b = getVectorFromIndex(i * timesFactor);
            Double x1 = (Double) a.get(0);
            Double y1 = (Double) a.get(1);
            Double x2 = (Double) b.get(0);
            Double y2 = (Double) b.get(1);
            drawSurface.drawLine(x1.intValue(), y1.intValue(), x2.intValue(), y2.intValue());
        }
    }
}
