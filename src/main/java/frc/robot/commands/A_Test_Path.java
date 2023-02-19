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
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.util.RamseteSetup;
import frc.robot.commands.T_TronWheel_Home;
import frc.robot.subsystems.Drivetrain;
import frc.robot.commands.T_Cascade_Home;

import static frc.robot.Constants.TronWheel.*;
import static frc.robot.Constants.Cascade.*;

public class A_Test_Path extends SequentialCommandGroup {
        private Drivetrain driveInst_m;
        private Trajectory testTraj_m;

        /**
         * Add all the commands you would like to happen in auto to this, in order of
         * occurence
         */
        public A_Test_Path() {
                String testPath = "paths/output/Unnamed.wpilib.json";
                Path test = Filesystem.getDeployDirectory().toPath().resolve(testPath);
                driveInst_m = Drivetrain.getInstance();
                addRequirements(driveInst_m);

                testTraj_m = null;
                try {
                        testTraj_m = TrajectoryUtil.fromPathweaverJson(test);
                } catch (IOException e) {
                        e.printStackTrace();
                }

                addCommands(
                //     Commands.parallel(RamseteSetup.initializeRamseteCommand(testTraj), new T_TronWheel_Home(), new T_Cascade_Home()),
                //     Commands.parallel(new T_Cascade_Preset(VAL_MID_PRESET), new T_TronWheel_Preset(VAL_ANGLE_SCORE)),
                //         new T_Intake_Out()
                        // new T_Cascade_Home(),
                        new InstantCommand(() -> {
                                driveInst_m.resetOdometry(testTraj_m.getInitialPose());
                        }),
                        RamseteSetup.initializeRamseteCommand(testTraj_m)
                );
        }
}
