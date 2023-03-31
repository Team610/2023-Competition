package frc.robot.commands;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.util.RamseteSetup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.RobotContainer;
import static frc.robot.Constants.TronWheel.*;
import static frc.robot.Constants.Cascade.*;

public class R_LCube_2 extends SequentialCommandGroup {
        private Trajectory preload_m, pickup_m;

        /**
         * Add all the commands you would like to happen in auto to this, in order of
         * occurence
         */
        public R_LCube_2() {
                String preloadHigh = "paths/output/RedLeftCubePreload.wpilib.json";
                Path preload = Filesystem.getDeployDirectory().toPath().resolve(preloadHigh);
                String pickupHigh = "paths/output/RedLeftPickupLeft.wpilib.json";
                Path pickup = Filesystem.getDeployDirectory().toPath().resolve(pickupHigh);
                RobotContainer.drivetrainInst_s = Drivetrain.getInstance();
                addRequirements(RobotContainer.drivetrainInst_s);
                RobotContainer.cascadeInst_s.setTicks(VAL_AUTO_CONE_PRESET);
                RobotContainer.tronWheelInst_s.setTicks(VAL_TRANSPORT_CONE_PRESET);

                preload_m = pickup_m = null;

                try {
                        preload_m = TrajectoryUtil.fromPathweaverJson(preload);
                        pickup_m = TrajectoryUtil.fromPathweaverJson(pickup);
                } catch (IOException e) {
                        e.printStackTrace();
                }

                addCommands(
                        new A_Disable_Safeties(),
                        Commands.parallel(new A_Cascade_Move(VAL_HIGH_CONE_PRESET, 100), new A_Intake_In(100)),
                        Commands.parallel(new A_TronWheel_Move(VAL_ANG_CONE_SCORE, 80), new A_Intake_Out()),
                        Commands.parallel(new A_Intake_In(550),
                                Commands.sequence(
                                        Commands.parallel(
                                                Commands.sequence(new A_Reset_Odometry(preload_m), RamseteSetup.initializeRamseteCommand(preload_m),
                                                        new A_Reset_Odometry(pickup_m), RamseteSetup.initializeRamseteCommand(pickup_m)),
                                                Commands.sequence(
                                                        Commands.parallel(
                                                                Commands.sequence(new A_Cascade_Move(VAL_RAMP_CONE_PRESET, 110), new T_Cascade_Home(), new A_Cascade_Move(VAL_GROUND_CONE_PRESET, 110)),
                                                                new A_TronWheel_Move(VAL_ANG_CONE_GROUND, 110)
                                                        ),
                                                        Commands.parallel(new A_TronWheel_Move(VAL_TRANSPORT_CONE_PRESET, 110), new A_Cascade_Move(VAL_LINEUP_CONE_PRESET, 110))
                                                )
                                        ),
                                        Commands.parallel(new A_Cascade_Move(VAL_HIGH_CONE_PRESET, 110), new A_TronWheel_Move(VAL_ANG_CONE_SCORE, 110))
                                )
                        ),
                        new A_Intake_Out()
                );
        }
}