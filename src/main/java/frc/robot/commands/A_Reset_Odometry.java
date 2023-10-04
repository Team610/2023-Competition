package frc.robot.commands;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class A_Reset_Odometry extends CommandBase{
    private Trajectory traj_m;

    public A_Reset_Odometry(Trajectory traj) {
        traj_m = traj;
        addRequirements(RobotContainer.drivetrainInst_s);
    }

    @Override
    public void execute() {
        RobotContainer.drivetrainInst_s.resetOdometry(traj_m.getInitialPose());
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}