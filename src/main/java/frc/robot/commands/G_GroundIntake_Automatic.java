package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.TronWheel;

public class G_GroundIntake_Automatic extends ParallelCommandGroup{
    private Intake intakeInst_m;
    private TronWheel tronWheelInst_m;

    public G_GroundIntake_Automatic() {
        intakeInst_m = Intake.getInstance();
        tronWheelInst_m = TronWheel.getInstance();
        addRequirements(intakeInst_m, tronWheelInst_m);

        addCommands(
            new T_Intake_In(),
            new T_IntakeTronWheel_GroundIntake()
        );
    }
}
