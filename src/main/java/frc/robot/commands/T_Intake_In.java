package frc.robot.commands;

import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import static frc.robot.Constants.Intake.*;

public class T_Intake_In extends CommandBase {
    private Intake intakeInst_m;
    private LinearFilter filter_m;

    public T_Intake_In() {
        intakeInst_m = Intake.getInstance();
        // filter_m = LinearFilter.movingAverage(100);
        filter_m = LinearFilter.singlePoleIIR(1, 0.02);
        addRequirements(intakeInst_m);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        intakeInst_m.intake(VAL_IN_PERCENT);
    }

    @Override
    public boolean isFinished() {
        return filter_m.calculate(intakeInst_m.getSRXSupplyCurrent()) > 10;
    }

    @Override
    public void end(boolean interrupted) {
        filter_m.reset();
        intakeInst_m.stopIntake();
    }
}
