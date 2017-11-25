package org.usfirst.frc.team4908;

import edu.wpi.first.wpilibj.IterativeRobot;
import org.usfirst.frc.team4908.IO.ArduinoComms;
import org.usfirst.frc.team4908.Subsystems.Drive;
import org.usfirst.frc.team4908.Subsystems.Subsystem;

import java.util.ArrayList;

/**
 * @author Siggy
 *         $
 */
public class Robot extends IterativeRobot
{
    ArrayList<Subsystem> mSubsystems;

    public void robotInit()
    {
        mSubsystems.add(RobotState.getInstance());
        mSubsystems.add(Drive.getInstance());
    }

    public void autonomousInit()
    {

    }

    public void autonomousPeriodic()
    {
        ArduinoComms.getInstance().update(2);
    }

    public void teleopInit()
    {
        for(Subsystem s : mSubsystems)
        {
            s.init();
        }
    }

    public void teleopPeriodic()
    {
        for(Subsystem s : mSubsystems)
        {
            s.loop();
        }

        ArduinoComms.getInstance().update(1);
    }

    public void disabledInit()
    {
        for(Subsystem s : mSubsystems)
        {
            s.end();
        }
    }

    public void disabledPeriodic()
    {
        ArduinoComms.getInstance().update(0);
    }

}
