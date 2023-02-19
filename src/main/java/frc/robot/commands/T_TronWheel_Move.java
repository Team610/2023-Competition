package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.TronWheel;

import static frc.robot.Constants.TronWheel.*;
import static frc.robot.Constants.*;

public class T_TronWheel_Move extends CommandBase {
    private TronWheel tronWheelInst_m;

    public T_TronWheel_Move() {
        tronWheelInst_m = TronWheel.getInstance();
        addRequirements(tronWheelInst_m);
    }

    @Override
    public void execute() {
        if(!tronWheelInst_m.getSafety()){
            if(tronWheelInst_m.getManual()) {
                double speed = MathUtil.applyDeadband(-RobotContainer.operator_s.getRightY() * VAL_MAX_SPEED, VAL_DEADBAND);
                tronWheelInst_m.rotate(speed);
            } else {
                tronWheelInst_m.rotateMagic();
            }
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        tronWheelInst_m.stopRotate();
    }
}
