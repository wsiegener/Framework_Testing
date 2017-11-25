package org.usfirst.frc.team4908.Motion.Trajectories;

import java.util.ArrayList;

/**
 * @author Siggy
 *         $
 */
public class Trajectory
{
    private Spline spline1;
    private ArrayList<Setpoint> setPoints;
    private ArrayList<ReferencePoint> referencePoints;
    private double sIncrement;

    public Trajectory(double time, ArrayList<ReferencePoint> refPoints)
    {
        referencePoints = new ArrayList<>();
        referencePoints = refPoints;

        spline1 = new Spline(time, referencePoints);

        sIncrement =  1.0/(time * 50.0);

        setPoints = new ArrayList<>();

        for(double s = 0; s <= 1.0; s += sIncrement)
        {
            setPoints.add(new Setpoint(s, spline1.getX(s), spline1.getY(s),
                                            spline1.getDX(s),  spline1.getDY(s),
                                            spline1.getdYdX(s), spline1.getH(s),
                                            spline1.getDH(s), spline1.getdHdS(s),
                                            s*time));
        }
    }

    public void spew()
    {
        for (Setpoint sp : setPoints)
            System.out.println(sp);
    }

    public ArrayList<Setpoint> getSetpoints()
    {
        return setPoints;
    }

    public ArrayList<ReferencePoint> getReferencePoints()
    {
        return referencePoints;
    }
}
