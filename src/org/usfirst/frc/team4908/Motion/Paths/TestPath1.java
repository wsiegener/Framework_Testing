package org.usfirst.frc.team4908.Motion.Paths;

import org.usfirst.frc.team4908.Motion.Trajectories.ReferencePoint;
import org.usfirst.frc.team4908.Motion.Trajectories.Trajectory;

import java.util.ArrayList;

/**
 * @author Siggy
 *         $
 */
public class TestPath1
{
    ArrayList<ReferencePoint> rp;
    Trajectory t;

    public TestPath1()
    {
        rp = new ArrayList<>();

        rp.add(new ReferencePoint(0.0, 0.0));
        rp.add(new ReferencePoint(0.0, 4.0));
        rp.add(new ReferencePoint(8.0, 4.0));
        rp.add(new ReferencePoint(8.0, 8.0));

        t = new Trajectory(5, rp);
    }

    public Trajectory getTrajectory()
    {
        return t;
    }

}
