package frc.robot.commands;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.util.RamseteSetup;

public class A_Test_Path extends SequentialCommandGroup {
    /**
         * Add all the commands you would like to happen in auto to this, in order of
         * occurence
         */
        public A_Test_Path() {
                String testPath = "paths/output/Unnamed.wpilib.json";
                Path test = Filesystem.getDeployDirectory().toPath().resolve(testPath);

                Trajectory testTraj = null;
                try {
                        testTraj = TrajectoryUtil.fromPathweaverJson(test);
                } catch (IOException e) {
                        e.printStackTrace();
                }
                                
                addCommands(
                    RamseteSetup.initializeRamseteCommand(testTraj)
                );
        }
}