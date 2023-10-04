package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

import static frc.robot.Constants.Intake.*;


public class A_Intake_Out extends CommandBase {
    private boolean coneMode_m;

    public A_Intake_Out(boolean coneMode) {
        coneMode_m = coneMode;
        addRequirements(RobotContainer.intakeInst_s);
    }

    @Override
    public void initialize() {
        RobotContainer.intakeInst_s.resetLoopCount();
    }

    @Override
    public void execute() {
        RobotContainer.intakeInst_s.incrementLoopCount();
        RobotContainer.intakeInst_s.intake(coneMode_m ? VAL_OUT_CONE_AUTO : VAL_OUT_TURBO);
    }

    @Override
    public boolean isFinished() {
        return RobotContainer.intakeInst_s.getLoopCount() >= 25;
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.intakeInst_s.stopIntake();
    }
}
