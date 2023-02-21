package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.TronWheel;

import static frc.robot.Constants.TronWheel.*;

public class G_TronWheel_PresetGround extends SequentialCommandGroup {

    private TronWheel tronWheelInst_m;
    private Intake intakeInst_m;

    public G_TronWheel_PresetGround() {
        tronWheelInst_m = TronWheel.getInstance();
        intakeInst_m = Intake.getInstance();
        addRequirements(tronWheelInst_m, intakeInst_m);

        addCommands(
            new T_TronWheel_Preset(VAL_ANGLE_GROUND_INIT),
            new T_TronWheel_Ground()
        );
    }
}
