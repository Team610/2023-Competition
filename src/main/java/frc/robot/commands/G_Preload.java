package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.RobotContainer;
import frc.robot.states.CascadeState;
import frc.robot.states.TronWheelState;

import static frc.robot.Constants.TronWheel.*;
import static frc.robot.Constants.Cascade.*;

public class G_Preload extends SequentialCommandGroup {

    /**
     * Add all the commands you would like to happen in auto to this, in order of
     * occurence
     */
    public G_Preload() {
        RobotContainer.drivetrainInst_s = Drivetrain.getInstance();
        addRequirements(RobotContainer.drivetrainInst_s);
        RobotContainer.cascadeInst_s.setTicks(VAL_AUTO_CONE_PRESET);

        addCommands(
                new A_Disable_Safeties(),
                Commands.parallel(new A_Cascade_Move(CascadeState.HIGH, true, 110), new A_Intake_In(50)),
                new A_Wait(0.5),
                new A_Intake_Out(true),
                Commands.parallel(new A_Cascade_Move(CascadeState.TRANSPORT, true, 110),
                        new A_TronWheel_Move(TronWheelState.TRANSPORT, true, 70))

        );
    }
}
