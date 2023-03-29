package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;

import static frc.robot.Constants.TronWheel.*;

public class G_IntakeTronWheel_Automatic extends SequentialCommandGroup{

    public G_IntakeTronWheel_Automatic() {
        addRequirements(RobotContainer.intakeInst_s, RobotContainer.tronWheelInst_s);

        addCommands(
            new T_Intake_In(),
            new T_TronWheel_Preset(VAL_ANG_CONE_TRANSPORT)
        );
    }
}
