package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

import static frc.robot.Constants.Intake.*;
import static frc.robot.Constants.TronWheel.*;


public class T_Intake_In extends CommandBase {

    public T_Intake_In() {
        addRequirements(RobotContainer.intakeInst_s);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(RobotContainer.intakeInst_s.getIntaking()) {
            RobotContainer.driverRumble_s.setRumble(RumbleType.kBothRumble, 0.02);
            RobotContainer.operatorRumble_s.setRumble(RumbleType.kBothRumble, 0.02);
            RobotContainer.intakeInst_s.intake(VAL_IN_PERCENT);
        } else {
            RobotContainer.driverRumble_s.setRumble(RumbleType.kBothRumble, 0);
            RobotContainer.operatorRumble_s.setRumble(RumbleType.kBothRumble, 0);
            RobotContainer.intakeInst_s.stopIntake();
        }
        if(RobotContainer.tronWheelInst_s.getTargetPos() == VAL_ANG_CONE_GROUND && RobotContainer.tronWheelInst_s.checkClosedLoop()){
            if(RobotContainer.intakeInst_s.getHasGamePiece()) {
                // CommandScheduler.getInstance().schedule(new T_TronWheel_Preset(VAL_ANGLE_GROUND_FINAL));
            }
        }
    }

    @Override
    public boolean isFinished() {
        // return filter_m.calculate(RobotContainer.intakeInst_s.getSRXSupplyCurrent()) > (VAL_CONTINUOUS_CURRENT_LIMIT+1);
        return false;
    }
}
