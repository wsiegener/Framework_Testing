package org.usfirst.frc.team4908.Util;

/**
 * @author Siggy
 *         $
 */
public class Constants
{
    //TODO: Fill Drive vars

    //Drive System
    public static final int kLeftMasterID = 0;
    public static final int kRightMasterID = 1;
    public static final int kLeftSlaveID = 2;
    public static final int kRightSlaveID = 3;

    public static final int kLeftEncoderAChannel = 0;
    public static final int kLeftEncoderBChannel = 0;
    public static final int kRightEncoderAChannel = 0;
    public static final int kRightEncoderBChannel = 0;

    public static final double kPathFollowerProfileKP = 0.0;
    public static final double kPathFollowerProfileKI = 0.0;
    public static final double kPathFollowerProfileKD = 0.0;
    public static final double kPathFollowerProfileKF = 0.0;

    public static final double kVelocityProfileKP = 0.0;
    public static final double kVelocityProfileKI = 0.0;
    public static final double kVelocityProfileKD = 0.0;
    public static final double kVelocityProfileKF = 0.0;

    public static final double kRotationProfileKP = 0.0;
    public static final double kRotationProfileKI = 0.0;
    public static final double kRotationProfileKD = 0.0;
    public static final double kRotationProfileKF = 0.0;
    public static final double kRotationErrorMin = 1.0;

    public static final double kTurnSensitivity = 0.5;


    // TODO: fix ports

    // Operator Interface
    public static final int kDriverStickPort = 0;
    public static final int kOperatorPort = 1;

    public static final double kDriveXDeadzone = 0.01;
    public static final double kDriveRotDeadzone = 0.01;

    // Values
    public static final byte kArduinoI2CAddress = 0x01;


}
