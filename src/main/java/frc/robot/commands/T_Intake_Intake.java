package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.TronWheel;

import static frc.robot.Constants.*;
import static frc.robot.Constants.Intake.*;

public class T_Intake_Intake extends CommandBase {
    private Intake intakeInst_m;

    public T_Intake_Intake() {
        intakeInst_m = Intake.getInstance();
        addRequirements(intakeInst_m);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        intakeInst_m.intake(1);
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
        intakeInst_m.stopIntake();
    }
}
