package org.usfirst.frc.team4908.Motion;

/**
 * @author Siggy
 *         $
 */
public class RobotPose
{
    private double mX;
    private double mY;
    private double mTheta;

    public RobotPose(double x, double y, double theta)
    {
        mX = x;
        mY = y;
        mTheta = theta;
    }

    public double getX()
    {
        return mX;
    }

    public double getY()
    {
        return mY;
    }

    public double getTheta()
    {
        return mTheta;
    }

    public void setX(double x)
    {
        mX = x;
    }

    public void setY(double y)
    {
        mY = y;
    }

    public void setTheta(double theta)
    {
        mTheta = theta;
    }

    public void set(RobotPose p)
    {
        this.mX = p.getX();
        this.mY = p.getY();
        this.mTheta = p.getTheta();
    }

}
