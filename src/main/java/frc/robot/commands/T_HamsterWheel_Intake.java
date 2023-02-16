package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.HamsterWheel;

import static frc.robot.Constants.*;
import static frc.robot.Constants.HamsterWheel.*;

public class T_HamsterWheel_Intake extends CommandBase {
    private HamsterWheel hamsterWheelInst_m;

    public T_HamsterWheel_Intake() {
        hamsterWheelInst_m = HamsterWheel.getInstance();
        addRequirements(hamsterWheelInst_m);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        hamsterWheelInst_m.intake(1);
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
