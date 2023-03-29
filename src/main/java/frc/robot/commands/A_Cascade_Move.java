package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class A_Cascade_Move extends CommandBase {
    private boolean down_m;
    private double preset_m;
    private int timer_m;

    public A_Cascade_Move(double preset, int timer) {
        preset_m = preset;
        timer_m = timer;
        addRequirements(RobotContainer.cascadeInst_s);
    }

    @Override
    public void initialize() {
        RobotContainer.cascadeInst_s.setTargetPos(preset_m);
        RobotContainer.cascadeInst_s.resetLoopCount();
        down_m = RobotContainer.cascadeInst_s.getTicks() - RobotContainer.cascadeInst_s.getTargetPos() > 0 ? true : false;
    }

    @Override
    public void execute() {
        RobotContainer.cascadeInst_s.incrementLoopCount();
        if(!RobotContainer.cascadeInst_s.getSafety()){
            RobotContainer.cascadeInst_s.spinMagic(down_m);
        }
    }

    @Override
    public boolean isFinished(){
        return RobotContainer.cascadeInst_s.getLoopCount() >= timer_m;
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.cascadeInst_s.stop();
    }
}
