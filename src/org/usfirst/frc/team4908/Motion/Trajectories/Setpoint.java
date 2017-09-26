package org.usfirst.frc.team4908.Motion.Trajectories;
import java.text.DecimalFormat;

/**
 * @author Siggy
 *         $
 */
public class Setpoint
{
    private double sVal;        // "s" value, percent completion
    private double x, y;        // X and Y position
    private double dX, dY;      // dX/ds and dY/ds
    private double dYdX;        // Linear speed
    private double h;           // Current heading in degrees
    private double dH;          // change in heading between points
    private double dHdS;        // change in heading with respect to time
    private double timeStamp;   // Time in seconds

    private DecimalFormat df;

    public Setpoint(double sVal, double x, double y, double dX, double dY, double dYdX, double h, double dH, double dHdS, double timeStamp)
    {
        this.sVal = sVal;
        this.x = x;
        this.y = y;
        this.dX = dX;
        this.dY = dY;
        this.dYdX = dYdX;
        this.h = h;
        this.dH = dH;
        this.dHdS = dHdS;
        this.timeStamp = timeStamp;

        df = new DecimalFormat("#.###");
    }

    public double getsVal()
    {
        return sVal;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public double getdX()
    {
        return dX;
    }

    public double getdY()
    {
        return dY;
    }

    public double getdYdX()
    {
        return dYdX;
    }

    public double getH()
    {
        return h;
    }

    public double getdH()
    {
        return dH;
    }

    public double getdHdS()
    {
        return dHdS;
    }

    public double getTimeStamp()
    {
        return timeStamp;
    }

    @Override
    public String toString()
    {
        return ("S: " + df.format(sVal) + "\t\tX: " + df.format(x) +
                "\t\tY: " + df.format(y) + "\t\tdX: " + df.format(dX) +
                "\t\tdY: " + df.format(dY) + "\t\tdYdX: " + df.format(dYdX) +
                "\t\tH: " + df.format(Math.toDegrees(h)) + "\t\tdH: " + df.format(dH) +
                "\t\tdHdS: " + df.format(dHdS) + "\t\tTime: " + df.format(timeStamp));
    }
}
