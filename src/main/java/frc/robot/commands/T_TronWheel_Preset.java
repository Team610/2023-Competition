package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.states.TronWheelState;

public class T_TronWheel_Preset extends CommandBase {
    private TronWheelState state_m;

    public T_TronWheel_Preset(TronWheelState state) {
        state_m = state;
        addRequirements(RobotContainer.tronWheelInst_s);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        RobotContainer.tronWheelInst_s.setTargetPos(RobotContainer.coneMode_s ? state_m.getConeAng() : state_m.getCubeAng());
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
