package org.usfirst.frc.team4908.Motion;

import org.usfirst.frc.team4908.Motion.Trajectories.Trajectory;
import org.usfirst.frc.team4908.OperatorInterface;

/**
 * @author Siggy
 *         $
 *         This class is used for any conversion methods to turn joystick values, spline velocities, etc. into usable
 *         Left Right drive values that can be used in the Drive Class
 */
public class DriveHelper
{
    /**
     * most basic method, simply converts joystick x and y into left and right values
     * Squares and limits values before sending them back to the setMotors method
     * @return a drive command used by the drive class
     */
    public DriveCommand joystickDrive()
    {
        double x = OperatorInterface.getInstance().getDriverX();
        double rot = OperatorInterface.getInstance().getDriverRot();

        // Square x and Rot Values
        if(x < 0)
            x = -1.0*(Math.pow(x, 2.0));
        else if(x > 0)
            x = Math.pow(x, 2.0);

        if(rot < 0)
            rot = -1.0*(Math.pow(rot, 2.0));
        else if(rot > 0)
            rot = Math.pow(rot, 2.0);

        // limit x and rot values to 1.0
        if(x > 1.0)
            x = 1.0;
        if(x < -1.0)
            x = -1.0;

        if(rot > 1.0)
            rot = 1.0;
        if (rot < -1.0)
            rot = 1.0;

        return new DriveCommand( x - rot, x + rot);
    }

    /**
     * converts spline setpoint rotations and velocities at specific points into left and right values
     * @param index index of the setpoint being read
     * @param traj trajectory that values are being read from
     * @return a drive command used by the drive class
     */
    public DriveCommand followPath(int index, Trajectory traj)
    {
        //TODO: convert to revolutions?? Radians?? degrees?? no idea??

        double dX = traj.getSetpoints().get(index).getdYdX(); // linear velocity in fps
        double dR = Math.toRadians(traj.getSetpoints().get(index).getdHdS()); // rotational velocity in radians per second
        double deltaV;

        if(dR <= 0.001)
        {
            return new DriveCommand(dX, dX);
        }
        else
        {
            deltaV = 30.0 * dR / 2.0;
            return new DriveCommand(dX - deltaV, dX + deltaV);
        }
    }

}
