package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

import static frc.robot.Constants.PigeonBalance.*;

public class A_Pidgeon_Balance extends CommandBase {
    private Drivetrain driveInst_m;
    private PIDController pidBal_m;

    public A_Pidgeon_Balance() {
        driveInst_m = Drivetrain.getInstance();
        addRequirements(driveInst_m);
    }

    @Override
    public void initialize() {
        //TODO Tune PID
        pidBal_m = new PIDController(VAL_KP_BAL_PID, VAL_KI_BAL_PID, VAL_KD_BAL_PID);
        pidBal_m.setTolerance(VAL_BAL_TOLERANCE,VAL_VELO_TOLERANCE);
    }

    @Override
    public void execute() {
        driveInst_m.setLeft(pidBal_m.calculate(RobotContainer.pidgey_s.getPitch(),VAL_BAL_SETPOINT));
        driveInst_m.setRight(pidBal_m.calculate(RobotContainer.pidgey_s.getPitch(),VAL_BAL_SETPOINT));
        //curAng_s = RobotContainer.pidgey_s.getPitch();
        // if(curAng_s < -VAL_PIDGEY_RANGE){
        //     driveInst_m.setLeft(0.1);
        //     driveInst_m.setRight(0.1);
        // } else if (curAng_s > VAL_PIDGEY_RANGE){
        //     driveInst_m.setLeft(-0.1);
        //     driveInst_m.setRight(-0.1);
        // } else {
        //     driveInst_m.setLeft(0);
        //     driveInst_m.setRight(0);
        // }

    }

    @Override
    public boolean isFinished(){
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }
}