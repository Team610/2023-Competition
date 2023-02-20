package frc.robot.commands;

import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import static frc.robot.Constants.Intake.*;

public class T_Intake_In extends CommandBase {
    private Intake intakeInst_m;
    private LinearFilter filter_m;
    private double movingAverage_m;
    
    public T_Intake_In() {
        intakeInst_m = Intake.getInstance();
        filter_m = LinearFilter.movingAverage(VAL_SAMPLES);
        movingAverage_m = 0;
        addRequirements(intakeInst_m);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        intakeInst_m.intake(VAL_IN_PERCENT);
        movingAverage_m = filter_m.calculate(intakeInst_m.getSRXSupplyCurrent() - VAL_CONTINUOUS_CURRENT_LIMIT);
        SmartDashboard.putNumber("Moving Average", movingAverage_m);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(movingAverage_m) < 0.1;
    }

    @Override
    public void end(boolean interrupted) {
        intakeInst_m.stopIntake();
    }
}
