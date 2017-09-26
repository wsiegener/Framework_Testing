package org.usfirst.frc.team4908.Motion;

import org.usfirst.frc.team4908.Constants;

/**
 * @author Siggy
 *         $
 */
public class DriveVelocity
{
    private double dX, dR;

    public DriveVelocity(double dX, double dR)
    {
        if(dX > Constants.kDriveXDeadzone)
            this.dX = dX;
        else
            this.dX = 0.0;
        this.dR = dR;
        if(dR > Constants.kDriveRotDeadzone)
            this.dR = dX;
        else
            this.dR = 0.0;
    }

    public double getdX()
    {
        return dX;
    }

    public double getdR()
    {
        return dR;
    }
}
