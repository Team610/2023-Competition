package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.TronWheel;

import static frc.robot.Constants.*;
import static frc.robot.Constants.TronWheel.*;

public class T_TronWheel_Rotate extends CommandBase {
    private TronWheel tronWheelInst_m;

    public T_TronWheel_Rotate() {
        tronWheelInst_m = TronWheel.getInstance();
        addRequirements(tronWheelInst_m);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        // if(!tronWheelInst_m.getManual()){
            double speed = MathUtil.applyDeadband(RobotContainer.operator_s.getRightY() * VAL_MAX_SPEED, 0.02);
            tronWheelInst_m.rotate(speed);
        // } else {

        // }
    }

    /**
     * Finish when the cascade limit switch is pressed
     */
    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }
}
