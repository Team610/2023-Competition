package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.states.TronWheelState;

public class G_IntakeTronWheel_Automatic extends SequentialCommandGroup{

    public G_IntakeTronWheel_Automatic() {
        addRequirements(RobotContainer.intakeInst_s, RobotContainer.tronWheelInst_s);

        addCommands(
            new T_Intake_In(),
            new T_TronWheel_Preset(TronWheelState.TRANSPORT)
        );
    }
}
