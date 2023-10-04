package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

import static frc.robot.Constants.PigeonBalance.*;

public class A_Pidgeon_Balance extends CommandBase {
    private PIDController pidBal_m;

    public A_Pidgeon_Balance() {
        addRequirements(RobotContainer.drivetrainInst_s);
    }

    @Override
    public void initialize() {
        //TODO Tune PID
        pidBal_m = new PIDController(VAL_KP_BAL_PID, VAL_KI_BAL_PID, VAL_KD_BAL_PID);
        pidBal_m.setTolerance(VAL_BAL_TOLERANCE, VAL_VELO_TOLERANCE);
    }

    @Override
    public void execute() {
        RobotContainer.drivetrainInst_s.setLeft(pidBal_m.calculate(RobotContainer.pidgey_s.getPitch(), VAL_BAL_SETPOINT));
        RobotContainer.drivetrainInst_s.setRight(pidBal_m.calculate(RobotContainer.pidgey_s.getPitch(), VAL_BAL_SETPOINT));
    }

    @Override
    public boolean isFinished(){
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }
}