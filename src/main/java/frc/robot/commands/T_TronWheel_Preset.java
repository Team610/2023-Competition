package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TronWheel;

public class T_TronWheel_Preset extends CommandBase {
    private TronWheel tronWheelInst_m;
    private double target_m;
    
    public T_TronWheel_Preset(double target) {
        tronWheelInst_m = TronWheel.getInstance();
        target_m = target;
        addRequirements(tronWheelInst_m);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        tronWheelInst_m.setTargetPos(target_m);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
        
    }
}
