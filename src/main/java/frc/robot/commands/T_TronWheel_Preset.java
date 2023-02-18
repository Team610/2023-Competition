package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Cascade;
import frc.robot.subsystems.TronWheel;

import static frc.robot.Constants.Cascade.*;

public class T_TronWheel_Preset extends CommandBase {
    private TronWheel tronWheelInst_m;
    private double target_m;
    
    public T_TronWheel_Preset(double target) {
        tronWheelInst_m = TronWheel.getInstance();
        addRequirements(tronWheelInst_m);
        target_m = target;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(!tronWheelInst_m.getSafety()){
            tronWheelInst_m.rotateMagic(target_m);
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
    }
}
