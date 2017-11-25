package org.usfirst.frc.team4908.Motion;

import org.usfirst.frc.team4908.Util.Constants;
import org.usfirst.frc.team4908.Motion.Trajectories.Trajectory;
import org.usfirst.frc.team4908.IO.OperatorInterface;

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

        return new DriveCommand( x + rot, x - rot);
    }


    /**
     * Does some voodoo witchcraft shit and makes turning super good at high speeds
     * Doesnt work at low speeds so check for turn button to turn in place
     */


    public DriveCommand duxDrive()
    {
        double mThrottle, mRot;
        double mLeft, mRight;
        double overPower; // ????????????

        mThrottle = OperatorInterface.getInstance().getDriverX();
        mRot = OperatorInterface.getInstance().getDriverRot();

        if(!OperatorInterface.getInstance().getTurnButton())
        {
            mRot *= (Math.abs(mThrottle) * Constants.kTurnSensitivity);
            overPower = 0.0; //????????????
        }
        else
        {
            overPower = 1.0; //????????????
        }

        mLeft = mThrottle + mRot;
        mRight = mThrottle - mRot;

        /**
         * so uhhh 254 does NOT use this skim value if they are NOT in quickTurn mode... they only use it when QTing
         * all they do is limit the values to 1.0 or -1.0...
         */

        if(mLeft >= 1.0)
        {
            mRight -= (mLeft - 1.0) * overPower; //?? ???  ?????????
            mLeft = 1.0;
        }
        else if(mLeft <= -1.0)
        {
            mRight += (mLeft + 1.0) * overPower; //?            ????????
            mLeft = -1.0;
        }


        if(mRight >= 1.0)
        {
            mLeft -= (mRight - 1.0) * overPower; //???????????
            mRight = 1.0;
        }
        else if(mRight <= -1.0)
        {
            mLeft += (mRight + 1.0) * overPower; //>>>>!>!>?!!?!???!?
            mRight = -1.0;
        }


        return new DriveCommand(mLeft, mRight);
    }

    /**
     * converts spline setpoint rotations and velocities at specific points into left and right values
     * @param index index of the setpoint being read
     * @param traj trajectory that values are being read from
     * @return a drive command used by the drive class
     */
    public DriveCommand followPath(int index, Trajectory traj)
    {
        //TODO: BROKEENN maybe?

        double dX = (traj.getSetpoints().get(index).getdYdX() * 60.0 / (Constants.kWheelCircumference / 12.0)); // linear velocity in fps * 60 seconds/min *
        double dR = Math.toRadians(traj.getSetpoints().get(index).getdHdS()) * 60.0 / Math.PI; // rotational velocity in radians per second
        double deltaV;

        if(Math.abs(dR) <= 0.001)
        {
            return new DriveCommand(dX, dX);
        }
        else
        {
            deltaV = (Constants.kTrackWidth / 2.0) * dR;
            return new DriveCommand(dX - deltaV, dX + deltaV);
        }
    }

}
