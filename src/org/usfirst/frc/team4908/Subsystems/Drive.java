package org.usfirst.frc.team4908.Subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;
import org.usfirst.frc.team4908.Constants;
import org.usfirst.frc.team4908.Motion.DriveCommand;
import org.usfirst.frc.team4908.Motion.DriveHelper;
import org.usfirst.frc.team4908.Motion.Paths.TestPath1;
import org.usfirst.frc.team4908.Motion.Trajectories.Util;
import org.usfirst.frc.team4908.OperatorInterface;

/**
 * @author Siggy
 *         $
 */
public class Drive extends Subsystem
{
    // Instances
    public static Drive mInstance = new Drive();
    public static Drive getInstance()
    {
        return mInstance;
    }
    public DriveHelper mDriveHelper;

    // Control States
    public enum DriveState
    {
        NEUTRAL,
        OPEN_LOOP,
        PATH_FOLLOWING,
        ROTATE_TO_ANGLE
    }
    public static DriveState mDriveState;

    // hardware
    private final CANTalon mLeftMaster, mRightMaster, mLeftSlave, mRightSlave;
    private final AHRS mNavX;
    //private final Encoder mLeftEncoder, mRightEncoder;

    // path following variables
    TestPath1 path1 = new TestPath1();
    int mCurrentIndex = 0;

    /**
     * Drive constructor, initializes all hardware objects etc.
     */
    public Drive()
    {
        mLeftMaster     =   new CANTalon(Constants.kLeftMasterID);
        mRightMaster    =   new CANTalon(Constants.kRightMasterID);
        mLeftSlave      =   new CANTalon(Constants.kLeftSlaveID);
        mRightSlave     =   new CANTalon(Constants.kRightSlaveID);
        mDriveHelper    =   new DriveHelper();

        mNavX           =   new AHRS(SerialPort.Port.kUSB);
        /*
        mLeftEncoder    =   new Encoder(Constants.kLeftEncoderAChannel,
                                        Constants.kLeftEncoderBChannel,
                                        false,
                                        CounterBase.EncodingType.k4X);
        mRightEncoder   =   new Encoder(Constants.kRightEncoderAChannel,
                                        Constants.kRightEncoderBChannel,
                                        true,
                                        CounterBase.EncodingType.k4X);
        */
        configTalonsOpenLoopMode();
    }

    /**
     * gets run once on startup
     */
    @Override
    public void init()
    {

    }

    /**
     * gets run once per teleop periodic
     */
    @Override
    public void loop()
    {
        if(OperatorInterface.getInstance().joysticksMoving() && mDriveState != DriveState.OPEN_LOOP)
        {
            mDriveState = DriveState.OPEN_LOOP;
            configTalonsOpenLoopMode();
        }

        if(OperatorInterface.getInstance().getPathModeButton() && mDriveState != DriveState.PATH_FOLLOWING)
        {
            mDriveState = DriveState.PATH_FOLLOWING;
            configTalonsVelocityMode();
            mCurrentIndex = 0;
        }

        if(OperatorInterface.getInstance().getRotToAngleButton() && mDriveState != DriveState.ROTATE_TO_ANGLE)
        {
            mDriveState = DriveState.ROTATE_TO_ANGLE;
            mNavX.zeroYaw();
            configTalonsRotateToAngleMode();
        }

        switch (mDriveState)
        {
            case NEUTRAL:
            {
                setMotors(new DriveCommand(0.0, 0.0));
                break;
            }
            case OPEN_LOOP:
            {
                setMotors(mDriveHelper.joystickDrive());
                break;
            }
            case PATH_FOLLOWING:
            {
                setMotors(mDriveHelper.followPath(mCurrentIndex, path1.getTrajectory()));
                if(mCurrentIndex >= path1.getTrajectory().getSetpoints().size())
                {
                    mCurrentIndex = 0;
                    mDriveState = DriveState.NEUTRAL;
                }
                else
                {
                    mCurrentIndex++;
                }
                break;
            }
            case ROTATE_TO_ANGLE:
            {
                mRightMaster.setEncPosition((int) mNavX.getAngle());
                setMotors(new DriveCommand(0.0, 90.0));
                if(!OperatorInterface.getInstance().getRotToAngleButton())
                    mDriveState = DriveState.NEUTRAL;
                break;
            }
        }
    }

    @Override
    public void end()
    {

    }

    @Override
    public void interrupt()
    {
        mDriveState = DriveState.NEUTRAL;
    }


    public void setMotors(DriveCommand dc)
    {
        if(mDriveState == DriveState.ROTATE_TO_ANGLE)
        {
            mRightMaster.set(dc.getmRight());
            mLeftMaster.set(Constants.kRightMasterID);
            mLeftSlave.set(Constants.kRightMasterID);
            mRightSlave.set(Constants.kRightMasterID);
        }
        else
        {
            mRightMaster.set(dc.getmRight());
            mLeftMaster.set(dc.getmLeft());
            mRightSlave.set(Constants.kRightMasterID);
            mLeftSlave.set(Constants.kLeftMasterID);
        }
    }


    public void configTalonsOpenLoopMode()
    {
        // change talons to default mode (percent voltage mode)
        mRightMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
        mLeftMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);

        // configures peak and nominal voltages
        mRightMaster.configNominalOutputVoltage(+0.0f, -0.0f);
        mLeftMaster.configNominalOutputVoltage(+0.0f, -0.0f);
        mRightMaster.configPeakOutputVoltage(+12.0f, -12.0f);
        mLeftMaster.configPeakOutputVoltage(+12.0f, -12.0f);
    }

    public void configTalonsVelocityMode()
    {
        // sets motors for speed (velocity) control
        mRightMaster.changeControlMode(CANTalon.TalonControlMode.Speed);
        mLeftMaster.changeControlMode(CANTalon.TalonControlMode.Speed);

        // sets various encoder values
        mRightMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
        mLeftMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
        mRightMaster.reverseSensor(false);
        mLeftMaster.reverseSensor(true);
        mRightMaster.configEncoderCodesPerRev(-1);
        mLeftMaster.configEncoderCodesPerRev(-1);

        // configures peak and nominal voltages
        mRightMaster.configNominalOutputVoltage(+0.0f, -0.0f);
        mLeftMaster.configNominalOutputVoltage(+0.0f, -0.0f);
        mRightMaster.configPeakOutputVoltage(+12.0f, -12.0f);
        mLeftMaster.configPeakOutputVoltage(+12.0f, -12.0f);

        // configures PID values
        mRightMaster.setProfile(0);
        mRightMaster.setP(Constants.kVelocityProfileKP);
        mRightMaster.setI(Constants.kVelocityProfileKI);
        mRightMaster.setD(Constants.kVelocityProfileKD);
        mRightMaster.setF(Constants.kVelocityProfileKF);

        mLeftMaster.setProfile(0);
        mLeftMaster.setP(Constants.kVelocityProfileKP);
        mLeftMaster.setI(Constants.kVelocityProfileKI);
        mLeftMaster.setD(Constants.kVelocityProfileKD);
        mLeftMaster.setF(Constants.kVelocityProfileKF);
    }

    public void configTalonsRotateToAngleMode()
    {
        // talon configurations for rotating to angle mode
        mRightMaster.changeControlMode(CANTalon.TalonControlMode.Position);
        mLeftMaster.changeControlMode(CANTalon.TalonControlMode.Follower);

        // sets PIDF gains
        mRightMaster.setProfile(1);
        mRightMaster.setP(Constants.kRotationProfileKP);
        mRightMaster.setI(Constants.kRotationProfileKI);
        mRightMaster.setD(Constants.kRotationProfileKD);
        mRightMaster.setF(Constants.kRotationProfileKF);

        // configures the error tolerance
        mRightMaster.setAllowableClosedLoopErr((int)Constants.kRotationErrorMin);
    }
}
