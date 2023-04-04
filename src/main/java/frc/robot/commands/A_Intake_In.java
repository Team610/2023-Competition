package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

import static frc.robot.Constants.Intake.*;


public class A_Intake_In extends CommandBase {
    private int timer_m;

    /**
     * @param timer 1 second is 50
     */
    public A_Intake_In(int timer) {
        timer_m = timer;
        addRequirements(RobotContainer.intakeInst_s);
    }

    @Override
    public void initialize() {
        RobotContainer.intakeInst_s.resetLoopCount();
        RobotContainer.intakeInst_s.setIntaking(true);
        RobotContainer.driverRumble_s.setRumble(RumbleType.kBothRumble, 0.02);
    }

    @Override
    public void execute() {
        RobotContainer.intakeInst_s.incrementLoopCount();
        RobotContainer.intakeInst_s.intake(VAL_IN_PERCENT);
        // trigger_m.and(() -> tronWheelInst_m.getTargetPos() == VAL_ANGLE_GROUND_INIT)
        //     .onTrue(new A_TronWheel_Move(VAL_ANGLE_GROUND_FINAL, 25));
    }

    @Override
    public boolean isFinished() {
        return RobotContainer.intakeInst_s.getLoopCount() >= timer_m;
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.intakeInst_s.stopIntake();
        RobotContainer.intakeInst_s.setIntaking(false);
        RobotContainer.driverRumble_s.setRumble(RumbleType.kBothRumble, 0);
    }
}
