package org.usfirst.frc.team4908;

import org.usfirst.frc.team4908.Motion.PoseChange;
import org.usfirst.frc.team4908.Motion.RobotPose;
import org.usfirst.frc.team4908.Subsystems.Drive;
import org.usfirst.frc.team4908.Subsystems.Subsystem;
import org.usfirst.frc.team4908.Util.Constants;

/**
 * @author Siggy
 *         $
 */
public class RobotState extends Subsystem
{
    private static RobotState mInstance = new RobotState();
    public static RobotState getInstance()
    {
        return mInstance;
    }

    private double mLeftRawVelocity;
    private double mRightRawVelocity;

    private double mLeftCalculatedVelocity;
    private double mRightCalculatedVelocity;

    private double mLastLeftRawVelocity;
    private double mLastRightRawVelocity;

    private double mLastLeftCalculatedVelocity;
    private double mLastRightCalculatedVelocity;

    private RobotPose mRobotPose;
    private RobotPose mCalcRobotPose;
    private RobotPose mLastPose;
    private RobotPose mCalcLastPose;

    private PoseChange mPoseChange;
    private PoseChange mCalcPoseChange;

    long mLastTime = 0;
    long mCurrentTime = 0;
    long mDeltaTime = 0;


    public void init()
    {
        mRobotPose = new RobotPose(0.0, 0.0, 0.0);
        mLastPose = new RobotPose(0.0, 0.0, 0.0);

        mCalcRobotPose = new RobotPose(0.0, 0.0, 0.0);
        mCalcLastPose = new RobotPose(0.0, 0.0, 0.0);
    }

    public void loop()
    {
        // set time
        if(mLastTime == 0)
            mLastTime = System.nanoTime();
        mCurrentTime = System.nanoTime();

        mDeltaTime = mCurrentTime - mLastTime;


        mLeftRawVelocity = Drive.getInstance().getLeftVelocity();
        mRightRawVelocity = Drive.getInstance().getRightVelocity();

        mLeftCalculatedVelocity = Drive.getInstance().getLeftDistanceInches() / (mDeltaTime / 1000000000.0);
        mRightCalculatedVelocity = Drive.getInstance().getRightDistanceInches() / (mDeltaTime / 1000000000.0);

        mRobotPose.set(forwardKinematics(mLeftRawVelocity, mRightRawVelocity, mDeltaTime));
        mCalcRobotPose.set(forwardKinematics(mLeftCalculatedVelocity, mRightCalculatedVelocity, mDeltaTime));
        //mCalcRobotPose.setTheta(Math.toRadians(NavX.getAngle));

        mPoseChange = new PoseChange(mRobotPose, mLastPose);
        mCalcPoseChange = new PoseChange(mCalcRobotPose, mCalcRobotPose);

        // set "last values"
        mLastPose = mRobotPose;
        mCalcLastPose = mCalcRobotPose;

        mLastLeftRawVelocity = mLeftRawVelocity;
        mLastRightRawVelocity = mRightRawVelocity;

        mLastLeftCalculatedVelocity = mLeftCalculatedVelocity;
        mLastRightCalculatedVelocity = mRightCalculatedVelocity;
    }

    @Override
    public void end()
    {

    }

    @Override
    public void interrupt()
    {

    }


    private RobotPose forwardKinematics(double leftVelocity, double rightVelocity, double time)
    {
        double x, y, t;
        if(rightVelocity == leftVelocity)
        {
            t = mLastPose.getTheta();
            x = leftVelocity * Math.cos(t) * time;
            y = leftVelocity * Math.sin(t) * time;
        }
        else
        {
            double p = (rightVelocity + leftVelocity) / (rightVelocity - leftVelocity);

            t = ((rightVelocity - leftVelocity) / Constants.kTrackWidth) * time;
            x = p * (Constants.kTrackWidth / 2.0) * Math.sin(t);
            y = -1 * p * (Constants.kTrackWidth / 2.0) * Math.cos(t) + (p * (Constants.kTrackWidth / 2.0));
        }

        return new RobotPose(x, y, t);
    }
}
