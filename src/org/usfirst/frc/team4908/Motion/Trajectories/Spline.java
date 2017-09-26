package org.usfirst.frc.team4908.Motion.Trajectories;

/**
 * @author Siggy
 *         $
 */
public class Spline
{
    private String type;
    private int degree;

    private ReferencePoint[] positionPoints;
    private ReferencePoint[] velocityPoints;

    private double sIncrement;

    public Spline(double totalTime, ReferencePoint[] referencePoints)
    {
        this.sIncrement = 1.0/(totalTime * 50.0);

        switch (referencePoints.length)
        {
            case 2:
                type = "Linear";
                break;
            case 3:
                type = "Quadratic";
                break;
            case 4:
                type = "Cubic";
                break;
            case 5:
                type = "Quartic";
                break;
            default:
                break;
        }


        degree = referencePoints.length - 1;
        positionPoints = referencePoints;
        velocityPoints = new ReferencePoint[degree];

        for(int i = 0; i < velocityPoints.length; i++)
        {
            velocityPoints[i] = new ReferencePoint(positionPoints[i+1].getX()-positionPoints[i].getX(),positionPoints[i+1].getY()-positionPoints[i].getY());
        }
    }

    public double getX(double s)
    {
        double sum = 0.0;

        for(int v = 0; v <= degree; v++)
        {
            sum += Util.bernstein(degree, v, s) * positionPoints[v].getX();
        }

        return sum;
    }

    public double getY(double s)
    {
        double sum = 0.0;

        for(int v = 0; v <= degree; v++)
        {
            sum += Util.bernstein(degree, v, s) * positionPoints[v].getY();
        }

        return sum;
    }

    public double getDX(double s)
    {
        double sum = 0.0;

        for(int v = 0; v < degree; v++) // non inclusive because vPoints always has 1 less element
        {
            sum += Util.bernstein(degree-1, v, s) * velocityPoints[v].getX();
        }

        return degree*sum;
    }

    public double getDY(double s)
    {
        double sum = 0.0;

        for(int v = 0; v < degree; v++) // non inclusive because vPoints always has 1 less element
        {
            sum += Util.bernstein(degree-1, v, s) * velocityPoints[v].getY();
        }

        return degree*sum;
    }

    public double getdYdX(double s)
    {
        return Math.sqrt(Math.pow(getDX(s), 2) + Math.pow(getDY(s), 2));
    }

    public double getH(double s)
    {
        return Math.atan2(getDY(s), getDX(s));
    }

    public double getDH(double s)
    {
        if(s == 0.0 || s-sIncrement < 0.0)
            return 0.0;
        else
            return Math.toDegrees(getH(s)-getH(s-sIncrement));
    }

    public double getdHdS(double s)
    {
        return getDH(s)/(sIncrement);
    }

    public String getType()
    {
        return type;
    }
}
