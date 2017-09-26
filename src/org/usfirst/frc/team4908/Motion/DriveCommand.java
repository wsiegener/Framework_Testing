package org.usfirst.frc.team4908.Motion;

/**
 * @author Siggy
 *         $
 */
public class DriveCommand
{
    private double mLeft, mRight;

    public DriveCommand(double left, double right)
    {
        mLeft = left;
        mRight = right;
    }

    public double getmLeft()
    {
        return mLeft;
    }

    public double getmRight()
    {
        return mRight;
    }
}
