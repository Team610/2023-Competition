package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class T_Drivetrain_PID_Balance extends CommandBase {

    private Drivetrain drivetrainInst_m;


    public T_Drivetrain_PID_Balance() {
        drivetrainInst_m = Drivetrain.getInstance();
        addRequirements(drivetrainInst_m);
    }

    @Override
    public void initialize() {
    }


    @Override
    /**
     * Call to get current error and adjust speed accordingly
     */
    public void execute() {
        drivetrainInst_m.adjustPIDStation();
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
        return false;
    }
}