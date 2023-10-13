package frc.robot.commands;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class A_Reset_Odometry extends CommandBase{
    private Drivetrain drivetrainInst_m;
    private Trajectory traj_m;

    public A_Reset_Odometry(Trajectory traj) {
        drivetrainInst_m = Drivetrain.getInstance();
        traj_m = traj;
        addRequirements(drivetrainInst_m);
    }

    @Override
    public void execute() {
        drivetrainInst_m.resetOdometry(traj_m.getInitialPose());
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}