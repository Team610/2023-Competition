package frc.robot.commands;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.util.RamseteSetup;

public class A_RamsetePath extends SequentialCommandGroup {

    public A_RamsetePath (Trajectory traj) {
        addCommands(
            new A_Reset_Odometry(traj),
            RamseteSetup.initializeRamseteCommand(traj),
            Commands.runOnce(() -> RobotContainer.drivetrainInst_s.stop(), RobotContainer.drivetrainInst_s)
        );
    }
    
}
