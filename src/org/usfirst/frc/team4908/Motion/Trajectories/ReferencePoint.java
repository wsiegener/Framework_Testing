package org.usfirst.frc.team4908.Motion.Trajectories;

/**
 * @author Siggy
 *         $
 */
public class ReferencePoint
{
    private double x;
    private double y;

    public ReferencePoint(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public void setY(double y)
    {
        this.y = y;
    }

}
