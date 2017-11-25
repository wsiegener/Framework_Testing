package org.usfirst.frc.team4908.IO;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import org.usfirst.frc.team4908.Subsystems.Drive;
import org.usfirst.frc.team4908.Util.Constants;

/**
 * @author Siggy
 *         $
 *
 *         Handles communications of desired states to arduino for LED visual aids
 *         update() will be called every teleopPeriodic()
 */
public class ArduinoComms
{
    private static ArduinoComms  mInstance = new ArduinoComms();
    public static ArduinoComms getInstance()
    {
        return mInstance;
    }

    private I2C mI2C;

    // data vars
    private int mLastSend;
    private int mData;

    // robot flags
    private final int ALLIANCE      = 0; // LSB
    private final int TELEOP        = 1; // 2nd Bit
    private final int MOVING        = 2; // 3rd Bit
    private final int PATH_MODE     = 3; // etc.

    private final int ENABLED = 7; // MSB signifies if the robot is enabled or disabled

    public ArduinoComms()
    {
        mI2C = new I2C(I2C.Port.kOnboard, Constants.kArduinoI2CAddress);
    }

    public void update(int mode)
    {
        // Check various robot values and set flags if necessary

        if(DriverStation.getInstance().getAlliance().equals(DriverStation.Alliance.Blue))
        {
            mData |= (1 << ALLIANCE); // enables 0th bit
        }
        else
        {
            mData &= ~(1 << ALLIANCE); // disables 0th bit
        }

        switch (mode)
        {
            case 0: // disabled
            {
                mData &= ~(1 << ENABLED); // robot disabled
            }
            case 1: // teleop
            {
                mData |= (1 << ENABLED); // robot enabled
                mData |= (1 << TELEOP);  // in teleop mode
            }
            case 2: // auto
            {
                mData |= (1 << ENABLED); // robot enabled
                mData &= ~(1 << TELEOP); // not teleop mode
            }
        }

        if(OperatorInterface.getInstance().joysticksMoving())
        {
            mData |= (1 << MOVING);
        }
        else
        {
            mData &= ~(1 << MOVING);
        }

        if(Drive.getInstance().getDriveState().equals(Drive.DriveState.PATH_FOLLOWING))
        {
            mData |= (1 << PATH_MODE);
        }
        else
        {
            mData &= ~(1 << PATH_MODE);
        }


        // avoids flooding the arduino with the same data repeatedly
        if(mData != mLastSend)
        {
            sendData(mData);
        }

        mLastSend = mData;
    }

    public void sendData(int data)
    {
        mI2C.write(1, data);
    }

}
