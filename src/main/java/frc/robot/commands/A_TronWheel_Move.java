package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.TronWheel;

import static frc.robot.Constants.TronWheel.*;
import static frc.robot.Constants.*;

public class A_TronWheel_Move extends CommandBase {
    private TronWheel tronWheelInst_m;
    private double preset_m;
    private int timer_m;

    public A_TronWheel_Move(double preset, int timer) {
        preset_m = preset;
        timer_m = timer;
        tronWheelInst_m = TronWheel.getInstance();
        addRequirements(tronWheelInst_m);
    }

    @Override
    public void initialize() {
        tronWheelInst_m.setTargetPos(preset_m);
        tronWheelInst_m.resetLoopCount();
    }

    @Override
    public void execute() {
        tronWheelInst_m.incrementLoopCount();
        if(!tronWheelInst_m.getSafety()){
            tronWheelInst_m.rotateMagic();
        }
    }

    @Override
    public boolean isFinished() {
        return tronWheelInst_m.getLoopCount() >= timer_m;
    }

    @Override
    public void end(boolean interrupted) {
        tronWheelInst_m.stopRotate();
    }
}
