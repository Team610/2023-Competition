package frc.robot;

import static frc.robot.Constants.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class MotorConfig {
    
    /**
     * Creates a new motor
     * @param CAN_ID Tells the method which CAN Id the motor uses
     * @param FX A boolean that indicates whether it is an FX or not(meaning its an SRX)
     */
    private MotorConfig(int CAN_ID, Boolean FX) {
        BaseTalon motor;

        motor = FX ? new TalonFX(CAN_ID) : new TalonSRX(CAN_ID);
        if(FX) ((TalonFX) motor).setNeutralMode(NeutralMode.Coast);
        else ((TalonSRX) motor).setNeutralMode(NeutralMode.Coast);
    }

    /**
     * Creates a motor on default settings
     * @param CAN_ID tells the code which CAN id it uses
     * @param FX indicates whether or not it is an FX
     * @return returns the talon created
     */
    public static BaseTalon createDefaultTalon(int CAN_ID, boolean FX) {
        BaseTalon talon;
        talon = FX ? new TalonFX(CAN_ID) : new TalonSRX(CAN_ID);

        talon.configFactoryDefault();
        return talon;
    }

    public static TalonFX configDriveMotor(int CAN_ID, boolean inverted, boolean sensorPhase) {
        TalonFX talon = (TalonFX)MotorConfig.createDefaultTalon(CAN_ID, true);
        talon.setInverted(inverted);
        talon.setSensorPhase(sensorPhase);
        talon.setNeutralMode(NeutralMode.Brake);
        return talon;
    }

    public static TalonFX configDriveFollower(int CAN_ID, int remoteId, boolean inverted, boolean sensorPhase) {
        TalonFX talon = MotorConfig.configDriveMotor(CAN_ID, inverted, sensorPhase);
        talon.set(ControlMode.Follower, remoteId);
        return talon;
    }

}