package frc.robot.commands;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
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

public class RB_CCube_1_Bal extends SequentialCommandGroup {
        private Trajectory preload_m, balance_m;

        /**
         * Add all the commands you would like to happen in auto to this, in order of
         * occurence
         */
        public RB_CCube_1_Bal() {
                String preloadHigh = "paths/output/LeaveComm.wpilib.json";
                Path preload = Filesystem.getDeployDirectory().toPath().resolve(preloadHigh);
                String balance = "paths/output/BalanceOut.wpilib.json";
                Path balancePath = Filesystem.getDeployDirectory().toPath().resolve(balance);
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
                        Commands.parallel(new A_Cascade_Move(CascadeState.HIGH, false, 110), new A_TronWheel_Move(TronWheelState.SCORE, true, 70), new A_Intake_In(110)),
                        new WaitCommand(0.2),
                        new A_Intake_Out(false),
                        Commands.parallel(
                                Commands.sequence(
                                        Commands.sequence(new WaitCommand(2)),
                                        new A_Reset_Odometry(preload_m), RamseteSetup.initializeRamseteCommand(preload_m),
                                        Commands.sequence(new WaitCommand(2)),
                                        new A_Reset_Odometry(balance_m), RamseteSetup.initializeRamseteCommand(balance_m)
                                ),
                                Commands.sequence(
                                        Commands.parallel(new A_Cascade_Move(CascadeState.TRANSPORT, false, 110), 
                                            new A_TronWheel_Move(TronWheelState.TRANSPORT, false, 110)))
                        ),
                        new A_Pidgeon_Balance()
                );
        }
}
