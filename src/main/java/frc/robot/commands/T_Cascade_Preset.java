package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.states.CascadeState;
import frc.robot.subsystems.Cascade;

import static frc.robot.Constants.Cascade.*;

public class T_Cascade_Preset extends CommandBase {
    private Cascade cascadeInst_m;
    private CascadeState cascadeState_m;
    
    public T_Cascade_Preset(CascadeState state) {
        cascadeInst_m = Cascade.getInstance();
        cascadeState_m = state;
        addRequirements(cascadeInst_m);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        cascadeInst_m.setTargetPos(cascadeState_m.getPreset());
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
    }
}
