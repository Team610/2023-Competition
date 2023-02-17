package frc.robot.subsystems;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase{
    private final ArmIO io;
    private static Arm armTalon;
    private final ArmIOInputsAutoLogged inputs = new ArmIOInputsAutoLogged();

    private Arm(ArmIO armIO){
        io = armIO;
    }

    public static Arm getInstance(){
        if(armTalon==null){
            armTalon = new Arm(new ArmTalon());
        }
        return armTalon;
    }

    @Override
    public void periodic() {
      io.updateInputs(inputs);
      Logger.getInstance().processInputs("Flywheel", inputs);
  
      // Log flywheel speed in RPM
      Logger.getInstance().recordOutput("FlywheelSpeedRPM", getVelocityRPM());
    }

    /** Run closed loop at the specified velocity. */
  public void runVelocity(double velocityRPM) {
    var velocityRadPerSec = Units.rotationsPerMinuteToRadiansPerSecond(velocityRPM);
    io.setVelocity(velocityRadPerSec);

    // Log flywheel setpoint
    Logger.getInstance().recordOutput("FlywheelSetpointRPM", velocityRPM);
  }

    /** Stops the flywheel. */
    public void stop() {
        io.stop();
    }

    public double getVelocityRPM() {
        return Units.radiansPerSecondToRotationsPerMinute(inputs.armRPM);
    }
}
