package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.RobotContainer;

import static frc.robot.Constants.TronWheel.*;
import static frc.robot.Constants.Cascade.*;

public class G_Preload extends SequentialCommandGroup {
    private Drivetrain drivetrainInst_m;

    /**
     * Add all the commands you would like to happen in auto to this, in order of
     * occurence
     */
    public G_Preload() {
        drivetrainInst_m = Drivetrain.getInstance();
        addRequirements(drivetrainInst_m);
        RobotContainer.cascadeInst_s.setTicks(VAL_AUTO_PRESET);

        addCommands(
                new A_Disable_Safeties(),
                Commands.parallel(new A_Cascade_Move(VAL_HIGH_PRESET, 110), new A_Intake_In(50)),
                new WaitCommand(0.5),
                new A_Intake_Out(),
                Commands.parallel(new A_Cascade_Move(VAL_TRANSPORT_PRESET, 110),
                        new A_TronWheel_Move(VAL_ANG_CONE_TRANSPORT, 110))

        );
    }
}
