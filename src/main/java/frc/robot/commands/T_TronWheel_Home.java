package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TronWheel;

import static frc.robot.Constants.TronWheel.*;

public class T_TronWheel_Home extends CommandBase {
    private TronWheel tronWheelInst_m;

    public T_TronWheel_Home() {
        tronWheelInst_m = TronWheel.getInstance();
        addRequirements(tronWheelInst_m);
    }

    @Override
    public void initialize() {
        tronWheelInst_m.tronRevLimit();
        tronWheelInst_m.rotate(-0.5);
    }

    @Override
    public void execute() {
    }

    /**
     * Finish when the cascade limit switch is pressed
     */
    @Override
    public boolean isFinished() {
        return tronWheelInst_m.tronRevLimit();
    }

    @Override
    public void end(boolean interrupted) {
        tronWheelInst_m.stopRotate();
        tronWheelInst_m.setTargetPos(VAL_ANGLE_GROUND_INIT);
        tronWheelInst_m.setSafety(false);
    }
}
