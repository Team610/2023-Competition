package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class T_Intake_Out extends CommandBase {
    private double speed_m;

    public T_Intake_Out(double speed) {
        speed_m = speed;
        addRequirements(RobotContainer.intakeInst_s);
    }

    @Override
    public void initialize() {
        RobotContainer.intakeInst_s.setIntaking(false);
    }

    @Override
    public void execute() {
        RobotContainer.intakeInst_s.intake(speed_m);
    }

    /**
     * Finish when the cascade limit switch is pressed
     */
    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.intakeInst_s.stopIntake();
        //reset cone position
        RobotContainer.visionInst_s.setConePosition(0);
    }
}
