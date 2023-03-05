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

public class G_PreloadLeave extends SequentialCommandGroup {
        private Drivetrain driveInst_m;
        private Trajectory preload_m, balance_m;

        /**
         * Add all the commands you would like to happen in auto to this, in order of
         * occurence
         */
        public G_PreloadLeave() {
                String preloadHigh = "paths/output/LeaveComm.wpilib.json";
                Path preload = Filesystem.getDeployDirectory().toPath().resolve(preloadHigh);
                String balance = "paths/output/BalanceOut.wpilib.json";
                Path balancePath = Filesystem.getDeployDirectory().toPath().resolve(balance);
                driveInst_m = Drivetrain.getInstance();
                addRequirements(driveInst_m);
                RobotContainer.cascadeInst_s.setTicks(VAL_AUTO_PRESET);

                preload_m = null;
                try {
                        preload_m = TrajectoryUtil.fromPathweaverJson(preload);
                        balance_m = TrajectoryUtil.fromPathweaverJson(balancePath);
                } catch (IOException e) {
                        e.printStackTrace();
                }

                addCommands(
                        new A_Disable_Safeties(),
                        Commands.parallel(new A_Cascade_Move(VAL_HIGH_PRESET, 110), new A_Intake_In(50)),
                        new WaitCommand(0.5),
                        new A_Intake_Out(),
                        Commands.parallel(
                                Commands.sequence(new A_Reset_Odometry(preload_m), RamseteSetup.initializeRamseteCommand(preload_m),
                                    new WaitCommand(0.5),
                                    new A_Reset_Odometry(balance_m), RamseteSetup.initializeRamseteCommand(balance_m)),
                                Commands.sequence(
                                        Commands.parallel(new A_Cascade_Move(VAL_TRANSPORT_PRESET, 110), 
                                            new A_TronWheel_Move(VAL_ANGLE_TRANSPORT, 110)))
                        ),
                        new A_Pidgeon_Balance()
                );
        }
}
