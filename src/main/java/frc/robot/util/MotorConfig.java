package frc.robot.util;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import static frc.robot.Constants.*;
import static frc.robot.Constants.TronWheel.*;

public class MotorConfig {
    
    /**
     * Creates a new motor
     * @param CAN_ID Tells the method which CAN Id the motor uses
     * @param FX A boolean that indicates whether it is an FX or not(meaning its an SRX)
     */
    private MotorConfig(int CAN_ID, boolean FX) {
        BaseTalon motor;

        motor = FX ? new WPI_TalonFX(CAN_ID) : new WPI_TalonSRX(CAN_ID);
        if(FX) ((WPI_TalonFX) motor).setNeutralMode(NeutralMode.Coast);
        else ((WPI_TalonSRX) motor).setNeutralMode(NeutralMode.Coast);
    }

    /**
     * Creates a motor on default settings, helper method to set up the other motors
     * @param CAN_ID tells the code which CAN id it uses
     * @param FX indicates whether or not it is an FX
     * @return returns the talon created
     */
    public static BaseTalon createDefaultTalon(int CAN_ID, boolean FX) {
        BaseTalon talon;
        talon = FX ? new WPI_TalonFX(CAN_ID, CAN_BUS_NAME) : new WPI_TalonSRX(CAN_ID);

        talon.configFactoryDefault();
        return talon;
    }

    /**
     * Creates a BATMAN motor on default settings
     * @param CAN_ID tells the code which CAN id it uses
     * @param inverted tells the code whether or not to invert the motor
     * @param sensorPhase tells the code whether or not it is inverted
     * @return returns the talon created
     */
    public static WPI_TalonFX configDriveMotor(int CAN_ID, boolean inverted, boolean sensorPhase) {
        WPI_TalonFX talon = (WPI_TalonFX)MotorConfig.createDefaultTalon(CAN_ID, true);
        talon.setInverted(inverted);
        talon.setSensorPhase(sensorPhase);
        talon.setNeutralMode(NeutralMode.Brake);
        return talon;
    }

    /**
     * Creates a ROBIN motor on default settings
     * @param CAN_ID tells the code which CAN id it uses
     * @param remoteId tells the code which motor to follow
     * @param inverted tells the code whether or not to invert the motor
     * @param sensorPhase tells the code whether or not it is inverted
     * @return returns the talon created
     */
    public static WPI_TalonFX configDriveFollower(int CAN_ID, int remoteId, boolean inverted, boolean sensorPhase) {
        WPI_TalonFX talon = MotorConfig.configDriveMotor(CAN_ID, inverted, sensorPhase);
        talon.set(ControlMode.Follower, remoteId);
        return talon;
    }

    public static WPI_TalonSRX configTronRotateMotor(int CAN_ID) {
        WPI_TalonSRX talon = (WPI_TalonSRX)MotorConfig.createDefaultTalon(CAN_ID, false);
        talon.setNeutralMode(NeutralMode.Brake);
        talon.setInverted(true);
        talon.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
        talon.configClearPositionOnLimitF(false, VAL_CONFIG_TIMEOUT);
        talon.configForwardSoftLimitThreshold(VAL_FWD_LIM);
        talon.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
        talon.configClearPositionOnLimitR(true, VAL_CONFIG_TIMEOUT);
        talon.setSensorPhase(true);
        talon.configMotionAcceleration(VAL_MAX_ACCELERATION, VAL_CONFIG_TIMEOUT);
        talon.configMotionCruiseVelocity(VAL_CRUISE_VELO, VAL_CONFIG_TIMEOUT);
        talon.config_kF(0, VAL_KF);
        talon.config_kP(0, VAL_KP);
        talon.config_kI(0, VAL_KI);
        talon.config_kD(0, VAL_KD);
        return talon;
    }

    public static WPI_TalonSRX configTronIntakeMotor(int CAN_ID) {
        WPI_TalonSRX talon = (WPI_TalonSRX)MotorConfig.createDefaultTalon(CAN_ID, false);
        talon.setNeutralMode(NeutralMode.Brake);
        talon.setInverted(false);
        talon.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyClosed);
        talon.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
        talon.configAllowableClosedloopError(0, VAL_CONFIG_TIMEOUT);
        talon.configClosedLoopPeriod(0, 1);
        talon.configNominalOutputForward(0.3);
        talon.configNominalOutputReverse(-0.3);
        talon.configPeakOutputForward(0.8);
        talon.configPeakOutputReverse(-0.8);
        talon.configPeakCurrentLimit(27);
        talon.configContinuousCurrentLimit(10);
        talon.configPeakCurrentDuration(100);
        talon.enableCurrentLimit(true);
        return talon;
    }
}
