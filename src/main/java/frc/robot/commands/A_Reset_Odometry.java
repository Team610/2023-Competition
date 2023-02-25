package frc.robot.commands;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class A_Reset_Odometry extends CommandBase{
    private Drivetrain m_driveInst;
    private Trajectory m_traj;

    public A_Reset_Odometry(Trajectory traj) {
        m_driveInst = Drivetrain.getInstance();
        m_traj = traj;
        addRequirements(m_driveInst);
    }

    @Override
    public void execute() {
        m_driveInst.resetOdometry(m_traj.getInitialPose());
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}