package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class T_Cascade_Preset extends CommandBase {
    private double target_m;
    
    public T_Cascade_Preset(double target) {
        target_m = target;
        addRequirements(RobotContainer.cascadeInst_s);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        RobotContainer.cascadeInst_s.setTargetPos(target_m);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
    }
}
