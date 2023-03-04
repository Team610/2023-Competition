package frc.robot.commands;

import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.TronWheel;

import static frc.robot.Constants.Intake.*;
import static frc.robot.Constants.TronWheel.*;
import static frc.robot.Constants.Cascade.*;


public class A_Intake_In extends CommandBase {
    private Intake intakeInst_m;
    private TronWheel tronWheelInst_m;
    private int timer_m;
    private Trigger trigger_m;

    public A_Intake_In(int timer) {
        timer_m = timer;
        intakeInst_m = Intake.getInstance();
        tronWheelInst_m = TronWheel.getInstance();
        trigger_m = new Trigger(() -> intakeInst_m.getHasGamePiece());
        addRequirements(intakeInst_m);
    }

    @Override
    public void initialize() {
        intakeInst_m.resetLoopCount();
        intakeInst_m.setIntaking(true);
        RobotContainer.driverRumble_s.setRumble(RumbleType.kBothRumble, 0.02);
    }

    @Override
    public void execute() {
        intakeInst_m.incrementLoopCount();
        intakeInst_m.intake(VAL_IN_PERCENT);
        // trigger_m.and(() -> tronWheelInst_m.getTargetPos() == VAL_ANGLE_GROUND_INIT)
        //     .onTrue(new A_TronWheel_Move(VAL_ANGLE_GROUND_FINAL, 25));
    }

    @Override
    public boolean isFinished() {
        return intakeInst_m.getLoopCount() >= timer_m;
    }

    @Override
    public void end(boolean interrupted) {
        intakeInst_m.stopIntake();
        intakeInst_m.setIntaking(false);
        RobotContainer.driverRumble_s.setRumble(RumbleType.kBothRumble, 0);

    }
}
