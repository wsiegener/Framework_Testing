package org.usfirst.frc.team4908.Motion.Paths;

import org.usfirst.frc.team4908.Motion.Trajectories.ReferencePoint;
import org.usfirst.frc.team4908.Motion.Trajectories.Trajectory;

/**
 * @author Siggy
 *         $
 */
public class HopperAutoPath
{
    ReferencePoint r1 = new ReferencePoint(0.0, 0.0);
    ReferencePoint r2 = new ReferencePoint(0.0, 4.0);
    ReferencePoint r3 = new ReferencePoint(8.0, 4.0);
    ReferencePoint r4 = new ReferencePoint(8.0, 8.0);

    Trajectory t = new Trajectory(5, r1, r2, r3, r4);

    public Trajectory getTrajectory()
    {
        return t;
    }

}
