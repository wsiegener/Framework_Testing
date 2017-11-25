package org.usfirst.frc.team4908.Subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * @author Siggy
 *         $
 */
public class Intake extends Subsystem
{
    CANTalon motor = new CANTalon(1);

    Solenoid piston = new Solenoid(1);

    @Override
    public void init()
    {

    }

    @Override
    public void loop()
    {

    }

    @Override
    public void end()
    {

    }

    @Override
    public void interrupt()
    {

    }
}
