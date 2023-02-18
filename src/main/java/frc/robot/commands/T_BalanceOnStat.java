package frc.robot.commands;

import static frc.robot.Constants.StationPID.*;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class T_BalanceOnStat extends CommandBase {

    private Drivetrain drivetrainInst_m;
    private static double error_s;
    private static double percPow_s;

    public T_BalanceOnStat() {
        drivetrainInst_m = Drivetrain.getInstance();
        addRequirements(drivetrainInst_m);
    }

    @Override
    public void initialize() {}


    @Override
    /**
     * Call to get current error and adjust speed accordingly
     */
    public void execute() {
        error_s = drivetrainInst_m.getPitch();
        percPow_s = -Math.sin(90-error_s) * VAL_MULTIPLIER;
        // Max drive power is 56.28%
        // Min drive power is 19.08%
        drivetrainInst_m.setLeft(percPow_s);
        drivetrainInst_m.setRight(percPow_s);
    }

    /**
     * Set robot to brake mode when on charging station
     */
    @Override
    public void end(boolean interrupted) {
        drivetrainInst_m.setBrake();
    }

    /**
     * End the command when the error is within the tolerance for it to balance
     */
    @Override
    public boolean isFinished() {
        //TODO change balance
        return Math.abs(error_s) < VAL_TOLERANCE; 
    }
}