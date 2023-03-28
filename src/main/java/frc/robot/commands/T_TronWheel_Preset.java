package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.states.TronWheelState;
import frc.robot.subsystems.TronWheel;

public class T_TronWheel_Preset extends CommandBase {
    private TronWheel tronWheelInst_m;
    private TronWheelState tronWheelState_m;
    
    public T_TronWheel_Preset(TronWheelState state) {
        tronWheelInst_m = TronWheel.getInstance();
        tronWheelState_m = state;
        addRequirements(tronWheelInst_m);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        tronWheelInst_m.setTargetPos(tronWheelState_m.getPreset());
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
        
    }
}
