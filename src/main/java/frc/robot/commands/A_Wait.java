package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class A_Wait extends CommandBase{
    private double timeLim_m;

    public A_Wait(double timeLim) {
        timeLim_m = timeLim * 1000 / 20;
    }

    @Override
    public void initialize() {
        RobotContainer.drivetrainInst_s.resetLoopCount();
    }

    @Override
    public void execute() {
        RobotContainer.drivetrainInst_s.incrementLoopCount();
    }

    @Override
    public boolean isFinished() {
        return RobotContainer.drivetrainInst_s.getLoopCount() >= timeLim_m;
    }

}
