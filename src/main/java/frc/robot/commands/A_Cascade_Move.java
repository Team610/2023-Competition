package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Cascade;

public class A_Cascade_Move extends CommandBase {
    private Cascade cascadeInst_m;
    private boolean down_m;
    private double preset_m;
    private int timer_m;

    public A_Cascade_Move(double preset, int timer) {
        preset_m = preset;
        timer_m = timer;
        cascadeInst_m = Cascade.getInstance();
        addRequirements(cascadeInst_m);
    }

    @Override
    public void initialize() {
        cascadeInst_m.setTargetPos(preset_m);
        cascadeInst_m.resetLoopCount();
        down_m = cascadeInst_m.getTicks() - cascadeInst_m.getTargetPos() > 0 ? true : false;
    }

    @Override
    public void execute() {
        cascadeInst_m.incrementLoopCount();
        if(!cascadeInst_m.getSafety()){
            cascadeInst_m.spinMagic(down_m);
        }
    }

    @Override
    public boolean isFinished(){
        return cascadeInst_m.getLoopCount() >= timer_m;
    }

    @Override
    public void end(boolean interrupted) {
        cascadeInst_m.stop();
    }
}
