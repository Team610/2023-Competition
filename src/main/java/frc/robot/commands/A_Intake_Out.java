package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

import static frc.robot.Constants.Intake.*;


public class A_Intake_Out extends CommandBase {
    private Intake intakeInst_m;

    public A_Intake_Out() {
        intakeInst_m = Intake.getInstance();
        addRequirements(intakeInst_m);
    }

    @Override
    public void initialize() {
        intakeInst_m.resetLoopCount();
    }

    @Override
    public void execute() {
        intakeInst_m.incrementLoopCount();
        intakeInst_m.intake(VAL_OUT_NORMAL);
    }

    /**
     * Finish when the cascade limit switch is pressed
     */
    @Override
    public boolean isFinished() {
        return intakeInst_m.getLoopCount() >= 25;
    }

    @Override
    public void end(boolean interrupted) {
        intakeInst_m.stopIntake();
    }
}
