package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

import static frc.robot.Constants.TronWheel.*;

public class T_TronWheel_Home extends CommandBase {

    public T_TronWheel_Home() {
        addRequirements(RobotContainer.tronWheelInst_s);
    }

    @Override
    public void initialize() {
        RobotContainer.tronWheelInst_s.tronRevLimit();
        RobotContainer.tronWheelInst_s.rotate(-0.5);
    }

    @Override
    public void execute() {
    }

    /**
     * Finish when the cascade limit switch is pressed
     */
    @Override
    public boolean isFinished() {
        return RobotContainer.tronWheelInst_s.tronRevLimit();
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.tronWheelInst_s.stopRotate();
        RobotContainer.tronWheelInst_s.setTargetPos(VAL_ANG_CONE_GROUND);
        RobotContainer.tronWheelInst_s.setSafety(false);
    }
}
