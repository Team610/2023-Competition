package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.states.TronWheelState;

public class A_TronWheel_Move extends CommandBase {
    private TronWheelState preset_m;
    boolean coneMode_m;
    private int timer_m;

    public A_TronWheel_Move(TronWheelState preset, boolean coneMode, int timer) {
        preset_m = preset;
        coneMode_m = coneMode;
        timer_m = timer;
        addRequirements(RobotContainer.tronWheelInst_s);
    }

    @Override
    public void initialize() {
        RobotContainer.tronWheelInst_s.setTargetPos(coneMode_m ? preset_m.getConeAng() : preset_m.getCubeAng());
        RobotContainer.tronWheelInst_s.resetLoopCount();
    }

    @Override
    public void execute() {
        RobotContainer.tronWheelInst_s.incrementLoopCount();
        if(!RobotContainer.tronWheelInst_s.getSafety()){
            RobotContainer.tronWheelInst_s.rotateMagic();
        }
    }

    @Override
    public boolean isFinished() {
        return RobotContainer.tronWheelInst_s.getLoopCount() >= timer_m;
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.tronWheelInst_s.stopRotate();
    }
}
