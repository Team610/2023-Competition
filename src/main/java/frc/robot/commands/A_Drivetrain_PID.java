package frc.robot.commands;

import static frc.robot.Constants.Drivetrain.*;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class A_Drivetrain_PID extends CommandBase {
    private Drivetrain drivetrainInst_m;

    public A_Drivetrain_PID() {
        drivetrainInst_m = Drivetrain.getInstance();
        addRequirements(drivetrainInst_m);
    }

    @Override
    public void execute() {
        drivetrainInst_m.adjustPID();
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}