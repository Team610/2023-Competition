package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

import static frc.robot.Constants.TronWheel.*;
import static frc.robot.Constants.*;

public class T_TronWheel_Move extends CommandBase {

    public T_TronWheel_Move() {
        addRequirements(RobotContainer.tronWheelInst_s);
    }

    @Override
    public void execute() {
        if(!RobotContainer.tronWheelInst_s.getSafety()){
            if(RobotContainer.tronWheelInst_s.getManual()) {
                double speed = MathUtil.applyDeadband(-RobotContainer.operator_s.getRightY() * VAL_MAX_SPEED, VAL_DEADBAND);
                RobotContainer.tronWheelInst_s.rotate(speed);
            } else {
                RobotContainer.tronWheelInst_s.rotateMagic();
            }
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.tronWheelInst_s.stopRotate();
    }
}
