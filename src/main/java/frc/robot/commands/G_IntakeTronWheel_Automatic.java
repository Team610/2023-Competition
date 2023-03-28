package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.subsystems.Intake;
import frc.robot.subsystems.TronWheel;

import static frc.robot.Constants.TronWheel.*;

public class G_IntakeTronWheel_Automatic extends SequentialCommandGroup{
    private Intake intakeInst_m;
    private TronWheel tronWheelInst_m;

    public G_IntakeTronWheel_Automatic() {
        intakeInst_m = Intake.getInstance();
        tronWheelInst_m = TronWheel.getInstance();
        addRequirements(intakeInst_m, tronWheelInst_m);

        addCommands(
            new T_Intake_In(),
            new T_TronWheel_Preset(VAL_ANG_CONE_TRANSPORT)
        );
    }
}
