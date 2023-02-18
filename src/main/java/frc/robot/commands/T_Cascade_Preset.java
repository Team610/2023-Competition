package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Cascade;

import static frc.robot.Constants.Cascade.*;

public class T_Cascade_Preset extends CommandBase {
    private Cascade cascadeInst_m;
    private double target_m;
    
    public T_Cascade_Preset(double target) {
        cascadeInst_m = Cascade.getInstance();
        target_m = target;
        addRequirements(cascadeInst_m);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        cascadeInst_m.setTargetPos(target_m);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
    }
}
