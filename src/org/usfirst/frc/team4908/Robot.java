package org.usfirst.frc.team4908;

import edu.wpi.first.wpilibj.IterativeRobot;
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

    public void teleopInit()
    {
        mSubsystems.add(Drive.getInstance());
    }

    public void teleopPeriodic()
    {
        for(Subsystem  s : mSubsystems)
        {
            s.loop();
        }
    }
}
