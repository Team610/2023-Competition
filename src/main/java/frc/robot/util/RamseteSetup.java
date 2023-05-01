package frc.robot.util;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.RobotContainer;

import java.util.List;

import static frc.robot.Constants.Drivetrain.*;

public final class RamseteSetup {
    private static final RamseteController controller_s = new RamseteController(VAL_RAMSETE_B, VAL_RAMSETE_ZETA);
    private static final SimpleMotorFeedforward feedForward_s = new SimpleMotorFeedforward(VAL_KS, VAL_KV, VAL_KA);
    private static final PIDController PID_L_s = new PIDController(VAL_KP_L, VAL_KI, VAL_KD);
    private static final PIDController PID_R_s = new PIDController(VAL_KP_R, VAL_KI, VAL_KD);
    private static RamseteCommand ramseteCmd_m;

    /**
     * Creates a new Ramsete path for the robot to follow with the passed in
     * waypoints
     * 
     * @param waypoints The list of waypoints you would like to pass through
     * @param reversed  If you want to drive backwards, this should be true
     * @return A Trajectory, a.k.a the path for the robot to follow
     */
    public static Trajectory initializeTrajectory(List<Pose2d> waypoints, boolean reversed) {
        var autoVoltageConstraint = new DifferentialDriveVoltageConstraint(
                feedForward_s, DRIVE_KINEMATICS, VAL_MAX_VOLTS);

        // Create config for trajectory
        TrajectoryConfig config = new TrajectoryConfig(
                VAL_MAX_VELO,
                VAL_MAX_ACCEL)
                // Add kinematics to ensure max speed is actually obeyed
                .setKinematics(DRIVE_KINEMATICS)
                // Apply the voltage constraint
                .addConstraint(autoVoltageConstraint);
        config.setReversed(reversed);
        return TrajectoryGenerator.generateTrajectory(waypoints, config);
    }

    /**
     * initialize ramsete command by passing in trajectory to follow
     * 
     * @param traj trajectory parameter to follow
     * @return new ramsete command to be scheduled
     */
    public static RamseteCommand initializeRamseteCommand(Trajectory traj) {
        ramseteCmd_m = new RamseteCommand(
                traj,
                RobotContainer.drivetrainInst_s::getPose,
                controller_s,
                feedForward_s,
                DRIVE_KINEMATICS,
                RobotContainer.drivetrainInst_s::getWheelSpeeds,
                PID_L_s,
                PID_R_s,
                // RamseteCommand passes volts to the callback
                RobotContainer.drivetrainInst_s::tankDriveVolts,
                RobotContainer.drivetrainInst_s);
        
        // Reset odometry to the starting pose of the trajectory.
        RobotContainer.drivetrainInst_s.resetOdometry(traj.getInitialPose());

        return ramseteCmd_m;
    }
}
