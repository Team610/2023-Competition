package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Vision;

public class T_Intake_Out extends CommandBase {
    private Intake intakeInst_m;
    private Vision visionInst_m;
    private double speed_m;

    public T_Intake_Out(double speed) {
        speed_m = speed;
        intakeInst_m = Intake.getInstance();
        visionInst_m = Vision.getInstance();
        addRequirements(intakeInst_m);
    }

    @Override
    public void initialize() {
        intakeInst_m.setIntaking(false);
    }

    @Override
    public void execute() {
        intakeInst_m.intake(speed_m);
    }

    /**
     * Finish when the cascade limit switch is pressed
     */
    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        intakeInst_m.stopIntake();
        //reset cone position
        visionInst_m.setConePosition(0);
    }
}
