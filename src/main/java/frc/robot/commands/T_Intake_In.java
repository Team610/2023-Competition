package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.TronWheel;

import static frc.robot.Constants.Intake.*;
import static frc.robot.Constants.TronWheel.*;


public class T_Intake_In extends CommandBase {
    private Intake intakeInst_m;
    private TronWheel tronWheelInst_m;

    public T_Intake_In() {
        intakeInst_m = Intake.getInstance();
        tronWheelInst_m = TronWheel.getInstance();
        addRequirements(intakeInst_m);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(intakeInst_m.getIntaking()) {
            RobotContainer.driverRumble_s.setRumble(RumbleType.kBothRumble, 0.02);
            intakeInst_m.intake(VAL_IN_PERCENT);
        } else {
            RobotContainer.driverRumble_s.setRumble(RumbleType.kBothRumble, 0);
            intakeInst_m.stopIntake();
        }
        if(tronWheelInst_m.getTargetPos() == VAL_ANGLE_GROUND_INIT && tronWheelInst_m.checkClosedLoop()){
            if(intakeInst_m.getHasGamePiece()) {
                // CommandScheduler.getInstance().schedule(new T_TronWheel_Preset(VAL_ANGLE_GROUND_FINAL));
            }
        }
    }

    @Override
    public boolean isFinished() {
        // return filter_m.calculate(intakeInst_m.getSRXSupplyCurrent()) > (VAL_CONTINUOUS_CURRENT_LIMIT+1);
        return false;
    }
}
