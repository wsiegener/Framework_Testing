package org.usfirst.frc.team4908;

import edu.wpi.first.wpilibj.Joystick;

/**
 * @author Siggy
 *         $
 */
public class OperatorInterface
{
    public static OperatorInterface mInstance = new OperatorInterface(Constants.kDriverStickPort, Constants.kOperatorPort);
    public static OperatorInterface getInstance()
    {
        return mInstance;
    }

    private Joystick mDriverStick;
    private Joystick mOperatorStick;

    // toggles
    private boolean mVelocityMode = false;

    public OperatorInterface(int driverPort, int operatorPort)
    {
        mDriverStick = new Joystick(driverPort);
        mOperatorStick = new Joystick(operatorPort);
    }


    // driver Commands
    public double getDriverX()
    {
        return mDriverStick.getRawAxis(0);
    }

    public double getDriverRot()
    {
        return mDriverStick.getRawAxis(1);
    }

    public boolean joysticksMoving()
    {
        return (mDriverStick.getRawAxis(0) >= Constants.kDriveXDeadzone || mDriverStick.getRawAxis(1) >= Constants.kDriveRotDeadzone);
    }

    public boolean getVelocityButton()
    {
        return mDriverStick.getRawButton(1);
    }

    public boolean getRotToAngleButton()
    {
        return mDriverStick.getRawButton(2);
    }

    public boolean getPathModeButton()
    {
        return mDriverStick.getRawButton(3);
    }


    // operator commands

}
