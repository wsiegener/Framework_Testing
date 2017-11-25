package org.usfirst.frc.team4908.Motion;

/**
 * @author Siggy
 *         $
 */
public class PoseChange
{
    private double dX;
    private double dY;
    private double dTheta;

    public PoseChange(double x, double y, double theta)
    {
        dX = x;
        dY = y;
        dTheta = theta;
    }

    public PoseChange(RobotPose current, RobotPose last)
    {
        dX = current.getX() - last.getX();
        dY = current.getY() - last.getY();
        dTheta = current.getTheta() - last.getTheta();
    }

    public double getdX()
    {
        return dX;
    }

    public double getdY()
    {
        return dY;
    }

    public double getdTheta()
    {
        return dTheta;
    }

}
