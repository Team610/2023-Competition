package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;


public class A_Disable_Safeties extends CommandBase {

    public A_Disable_Safeties() {
    }

    @Override
    public void initialize() {
        RobotContainer.cascadeInst_s.setSafety(false);
        RobotContainer.tronWheelInst_s.setSafety(false);
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
