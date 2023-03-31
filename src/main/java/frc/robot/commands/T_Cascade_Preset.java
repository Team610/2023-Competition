package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.states.CascadeState;

public class T_Cascade_Preset extends CommandBase {
    private CascadeState state_m;
    
    public T_Cascade_Preset(CascadeState state) {
        state_m = state;
        addRequirements(RobotContainer.cascadeInst_s);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        RobotContainer.cascadeInst_s.setTargetPos(!RobotContainer.coneMode_s ? state_m.getConePreset() : state_m.getCubePreset());
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
    }
}
