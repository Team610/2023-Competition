package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

import edu.wpi.first.math.util.Units;

import static frc.robot.Constants.*;

public class ArmTalon implements ArmIO{
    private TalonFX motor_m;

    public ArmTalon(){
        motor_m = new TalonFX(CAN_TALONFX);

        TalonFXConfiguration config = new TalonFXConfiguration();
        config.voltageCompSaturation = 12.0;
        config.statorCurrLimit.enable = true;
        config.statorCurrLimit.currentLimit = 40;
        config.primaryPID.selectedFeedbackSensor = FeedbackDevice.IntegratedSensor;
        motor_m.configAllSettings(config);
    }

    @Override
    public void updateInputs(ArmIOInputs armIO){
        armIO.armPosition = motor_m.getSelectedSensorPosition();
        armIO.armRPM = motor_m.getSelectedSensorVelocity();
        armIO.armVoltage = motor_m.getMotorOutputVoltage();
    }

    @Override
    public void stop() {
        motor_m.set(ControlMode.PercentOutput, 0.0);
    }

    @Override
    public void setVelocity(double velocityRadPerSec) {
        double velocityFalconUnits = Units.radiansToRotations(velocityRadPerSec);
        motor_m.set(ControlMode.Velocity, velocityFalconUnits);
  }
}
