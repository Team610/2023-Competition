package frc.robot.subsystems;

import org.littletonrobotics.junction.AutoLog;

public interface ArmIO {
    @AutoLog
    public static class ArmIOInputs{
        public double armPosition = 0.0;
        public double armVoltage = 0.0;
        public double armRPM = 0.0;
    }

  /** Updates the set of loggable inputs. */
  public default void updateInputs(ArmIOInputs inputs) {
}

/** Run closed loop at the specified velocity. */
public default void setVelocity(double velocityRadPerSec) {
}

/** Stop in open loop. */
public default void stop() {
}

/** Set velocity PID constants. */
public default void configurePID(double kP, double kI, double kD) {
}
}
