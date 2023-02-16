package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.HamsterWheel;

import static frc.robot.Constants.*;
import static frc.robot.Constants.HamsterWheel.*;

public class T_HamsterWheel_Rotate extends CommandBase {
    private HamsterWheel hamsterWheelInst_m;

    public T_HamsterWheel_Rotate() {
        hamsterWheelInst_m = HamsterWheel.getInstance();
        addRequirements(hamsterWheelInst_m);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double speed = MathUtil.applyDeadband(RobotContainer.operator_s.getRightY() * VAL_MAX_SPEED, 0.02);
        hamsterWheelInst_m.spinWheel(speed);
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
