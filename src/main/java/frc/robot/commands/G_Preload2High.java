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
import frc.robot.RobotContainer;
import frc.robot.commands.T_Cascade_Home;

import static frc.robot.Constants.TronWheel.*;
import static frc.robot.Constants.Cascade.*;

public class G_Preload2High extends SequentialCommandGroup {
        private Drivetrain driveInst_m;
        private Trajectory testTraj_m;

        /**
         * Add all the commands you would like to happen in auto to this, in order of
         * occurence
         */
        public G_Preload2High() {
                String testPath = "paths/output/Unnamed.wpilib.json";
                Path test = Filesystem.getDeployDirectory().toPath().resolve(testPath);
                driveInst_m = Drivetrain.getInstance();
                addRequirements(driveInst_m);
                RobotContainer.cascadeInst_s.setTicks(VAL_RAMP_PRESET);

                testTraj_m = null;
                try {
                        testTraj_m = TrajectoryUtil.fromPathweaverJson(test);
                } catch (IOException e) {
                        e.printStackTrace();
                }

                addCommands(
                        new A_Disable_Safeties(),
                        Commands.parallel(new A_Cascade_Move(VAL_HIGH_PRESET), new A_Intake_In()),
                        new A_Intake_Out()

                        // Commands.parallel(
                        //         Commands.sequence(new A_Reset_Odometry(testTraj_m), RamseteSetup.initializeRamseteCommand(testTraj_m)),
                        //         new A_Intake_Auto())
                );
        }
}
