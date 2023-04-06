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
                RobotContainer.cascadeInst_s.setTicks(VAL_TRANSPORT_CONE_PRESET);
                RobotContainer.tronWheelInst_s.setTicks(VAL_ANG_CONE_TRANSPORT);

                preload_m = pickup_m = null;

                try {
                        preload_m = TrajectoryUtil.fromPathweaverJson(preload);
                        pickup_m = TrajectoryUtil.fromPathweaverJson(pickup);
                } catch (IOException e) {
                        e.printStackTrace();
                }

                addCommands(
                        new A_Disable_Safeties(),
                        new G_Score(false, CascadeState.HIGH, TronWheelState.SCORE, VAL_AUTO_TIMEOUT),
                        new A_Cascade_Move(CascadeState.GROUND, true, VAL_AUTO_TIMEOUT)
                        .alongWith(new A_TronWheel_Move(TronWheelState.GROUND, true, VAL_AUTO_TIMEOUT)),
                        new A_RamsetePath(preload_m)
                        .alongWith(new A_Intake_In(200))
                        .alongWith(new A_Cascade_Move(CascadeState.GROUND, true, 200)),
                        new A_Intake_In(110)
                        .alongWith(new A_Cascade_Move(CascadeState.TRANSPORT, true, VAL_AUTO_TIMEOUT)
                        .alongWith(new A_TronWheel_Move(TronWheelState.TRANSPORT, true, VAL_AUTO_TIMEOUT))
                        .alongWith(new A_RamsetePath(pickup_m))),
                        new G_Score(true, CascadeState.HIGH, TronWheelState.SCORE, VAL_AUTO_TIMEOUT),
                        new A_Cascade_Move(CascadeState.GROUND, true, VAL_AUTO_TIMEOUT)
                        .alongWith(new A_TronWheel_Move(TronWheelState.GROUND, true, VAL_AUTO_TIMEOUT))
                );
        }
}
