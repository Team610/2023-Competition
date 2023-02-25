package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Cascade;

import static frc.robot.Constants.Cascade.*;

public class A_Cascade_Move extends CommandBase {
    private Cascade cascadeInst_m;
    private boolean down;
    private double preset_m;

    public A_Cascade_Move(double preset) {
        preset_m = preset;
        cascadeInst_m = Cascade.getInstance();
        addRequirements(cascadeInst_m);
    }

    @Override
    public void initialize() {
        cascadeInst_m.setTargetPos(preset_m);
        cascadeInst_m.resetLoopCount();
        down = cascadeInst_m.getTicks() - cascadeInst_m.getTargetPos() > 0 ? true : false;
    }

    @Override
    public void execute() {
        cascadeInst_m.incrementLoopCount();
        if(!cascadeInst_m.getSafety()){
            cascadeInst_m.spinMagic(down);
        }
    }

    @Override
    public boolean isFinished(){
        return cascadeInst_m.getLoopCount() >= 110;
    }

    @Override
    public void end(boolean interrupted) {
        cascadeInst_m.stop();
    }
}
