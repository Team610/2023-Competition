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

public class RB_CCone_1Half_Bal extends SequentialCommandGroup {
        private Trajectory preload_m, balance_m;

        /**
         * Add all the commands you would like to happen in auto to this, in order of
         * occurence
         */
        public RB_CCone_1Half_Bal() {
                String preloadHigh = "paths/output/PreloadPickup.wpilib.json";
                Path preload = Filesystem.getDeployDirectory().toPath().resolve(preloadHigh);
                String balance = "paths/output/PickupBal.wpilib.json";
                Path balancePath = Filesystem.getDeployDirectory().toPath().resolve(balance);
                RobotContainer.drivetrainInst_s = Drivetrain.getInstance();
                addRequirements(RobotContainer.drivetrainInst_s);
                RobotContainer.cascadeInst_s.setTicks(VAL_AUTO_CONE_PRESET);
                RobotContainer.tronWheelInst_s.setTicks(VAL_ANG_CONE_TRANSPORT);

                preload_m = null;
                try {
                        preload_m = TrajectoryUtil.fromPathweaverJson(preload);
                        balance_m = TrajectoryUtil.fromPathweaverJson(balancePath);
                } catch (IOException e) {
                        e.printStackTrace();
                }

                addCommands(
                        new A_Disable_Safeties(),
                        new G_Score(true, CascadeState.HIGH, TronWheelState.SCORE, VAL_AUTO_TIMEOUT),
                        new A_Cascade_Move(CascadeState.TRANSPORT, true, VAL_AUTO_TIMEOUT)
                        .alongWith(new A_TronWheel_Move(TronWheelState.TRANSPORT, true, VAL_AUTO_TIMEOUT)),
                        new A_RamsetePath(preload_m)
                        .alongWith(new A_Cascade_Move(CascadeState.GROUND, true, 200))
                        .alongWith(new A_TronWheel_Move(TronWheelState.GROUND, true, 200))
                        .alongWith(new A_Intake_In(200)),
                        new A_Intake_In(25),
                        new A_Intake_In(VAL_AUTO_TIMEOUT)
                        .alongWith(new A_Cascade_Move(CascadeState.TRANSPORT, true, VAL_AUTO_TIMEOUT))
                        .alongWith(new A_TronWheel_Move(TronWheelState.TRANSPORT, true, VAL_AUTO_TIMEOUT))
                        .alongWith(new A_RamsetePath(balance_m)),
                        new A_Pidgeon_Balance()
                );
        }
}
