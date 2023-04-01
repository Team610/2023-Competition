package frc.robot.commands;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.util.RamseteSetup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.RobotContainer;
import frc.robot.states.CascadeState;
import frc.robot.states.TronWheelState;

import static frc.robot.Constants.TronWheel.*;
import static frc.robot.Constants.Cascade.*;

public class G_PreloadBalance extends SequentialCommandGroup {
        private Trajectory preload_m;

        /**
         * Add all the commands you would like to happen in auto to this, in order of
         * occurence
         */
        public G_PreloadBalance() {
                String preloadHigh = "paths/output/PreloadBalance.wpilib.json";
                Path preload = Filesystem.getDeployDirectory().toPath().resolve(preloadHigh);
                RobotContainer.drivetrainInst_s = Drivetrain.getInstance();
                addRequirements(RobotContainer.drivetrainInst_s);
                RobotContainer.cascadeInst_s.setTicks(VAL_AUTO_CONE_PRESET);

                preload_m = null;
                try {
                        preload_m = TrajectoryUtil.fromPathweaverJson(preload);
                } catch (IOException e) {
                        e.printStackTrace();
                }

                addCommands(
                        new A_Disable_Safeties(),
                        Commands.parallel(new A_Cascade_Move(CascadeState.HIGH, true, 110), new A_Intake_In(50)),
                        new WaitCommand(0.5),
                        new A_Intake_Out(true),
                        Commands.parallel(
                                Commands.sequence(new A_Reset_Odometry(preload_m), RamseteSetup.initializeRamseteCommand(preload_m)),
                                Commands.sequence(
                                        Commands.parallel(new A_Cascade_Move(CascadeState.TRANSPORT, true, 110), 
                                            new A_TronWheel_Move(TronWheelState.TRANSPORT, true, 70)))
                        ),
                        new A_Pidgeon_Balance()
                );
        }
}
