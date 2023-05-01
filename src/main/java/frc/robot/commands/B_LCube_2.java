package frc.robot.commands;

import java.io.IOException;
import java.nio.file.Path;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.RobotContainer;

import static frc.robot.Constants.Cascade.*;

public class B_LCube_2 extends SequentialCommandGroup {
        private Trajectory preload_m, pickup_m;

        /**
         * Add all the commands you would like to happen in auto to this, in order of
         * occurence
         */
        public B_LCube_2() {
                String preloadHigh = "paths/output/BlueLeftPreloadLeft.wpilib.json";
                Path preload = Filesystem.getDeployDirectory().toPath().resolve(preloadHigh);
                String pickupHigh = "paths/output/BlueLeftPickupRight.wpilib.json";
                Path pickup = Filesystem.getDeployDirectory().toPath().resolve(pickupHigh);
                RobotContainer.drivetrainInst_s = Drivetrain.getInstance();
                addRequirements(RobotContainer.drivetrainInst_s);
                RobotContainer.cascadeInst_s.setTicks(VAL_RAMP_CONE_PRESET);

                preload_m = pickup_m = null;
                try {
                        preload_m = TrajectoryUtil.fromPathweaverJson(preload);
                        pickup_m = TrajectoryUtil.fromPathweaverJson(pickup);
                } catch (IOException e) {
                        e.printStackTrace();
                }

                addCommands(
                        new A_RamsetePath(preload_m),
                        new WaitCommand(0.5),
                        new A_RamsetePath(pickup_m)


                        // new A_Disable_Safeties(),
                        // new G_Score(false, CascadeState.HIGH, TronWheelState.SCORE, VAL_AUTO_TIMEOUT),
                        // new A_Cascade_Move(CascadeState.GROUND, isFinished(), VAL_AUTO_TIMEOUT)
                        // .alongWith(new A_TronWheel_Move(TronWheelState.GROUND, true, VAL_AUTO_TIMEOUT)),
                        // new A_RamsetePath(preload_m)
                        // .alongWith(new A_Intake_In(200)),
                        // new A_Intake_In(110)
                        // .alongWith(new A_Cascade_Move(CascadeState.TRANSPORT, true, VAL_AUTO_TIMEOUT))
                        // .alongWith(new A_TronWheel_Move(TronWheelState.TRANSPORT, true, VAL_AUTO_TIMEOUT))
                        // .alongWith(new A_RamsetePath(pickup_m)),
                        // new G_Score(true, CascadeState.HIGH, TronWheelState.SCORE, VAL_AUTO_TIMEOUT)




                        // new A_Disable_Safeties(),
                        // Commands.parallel(new A_Cascade_Move(CascadeState.HIGH, false, 110), new A_Intake_In(110)),
                        // new WaitCommand(0.1),
                        // Commands.parallel(new A_TronWheel_Move(TronWheelState.SCORE, false, 70), new A_Intake_Out(false)),
                        // Commands.parallel(new A_Intake_In(550),
                        //         Commands.sequence(
                        //                 Commands.parallel(
                        //                         Commands.sequence(
                        //                                 new A_RamsetePath(preload_m),
                        //                                 new A_RamsetePath(pickup_m)
                        //                         ),
                        //                         Commands.sequence(
                        //                                 Commands.parallel(
                        //                                         Commands.sequence(new A_Cascade_Move(CascadeState.RAMP, true, 110), new T_Cascade_Home(), new A_Cascade_Move(CascadeState.GROUND, true, 110)),
                        //                                         new A_TronWheel_Move(TronWheelState.GROUND, true, 70)
                        //                                 ),
                        //                                 Commands.parallel(new A_TronWheel_Move(TronWheelState.TRANSPORT, true, 70), new A_Cascade_Move(CascadeState.LINEUP, true, 110))
                        //                         )
                        //                 ),
                        //                 Commands.parallel(new A_Cascade_Move(CascadeState.HIGH, true, 110), new A_TronWheel_Move(TronWheelState.SCORE, true, 70))
                        //         )
                        // ),
                        // new A_Intake_Out(true)
                );
        }
}
