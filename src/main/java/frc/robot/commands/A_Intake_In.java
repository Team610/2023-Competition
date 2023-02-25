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
    private LinearFilter filter_m;

    public A_Intake_In() {
        intakeInst_m = Intake.getInstance();
        tronWheelInst_m = TronWheel.getInstance();
        filter_m = LinearFilter.singlePoleIIR(0.5, 0.02);
        addRequirements(intakeInst_m);
    }

    @Override
    public void initialize() {
        intakeInst_m.resetLoopCount();
        intakeInst_m.setIntaking(true);
        RobotContainer.driverRumble_s.setRumble(RumbleType.kBothRumble, 0.05);
    }

    @Override
    public void execute() {
        intakeInst_m.incrementLoopCount();
        intakeInst_m.intake(VAL_IN_PERCENT);
        if(tronWheelInst_m.getTargetPos() == VAL_ANGLE_GROUND_INIT && tronWheelInst_m.checkClosedLoop()){
            if(intakeInst_m.getHasGamePiece()) {
                CommandScheduler.getInstance().schedule(new T_TronWheel_Preset(VAL_ANGLE_GROUND_FINAL));
            }
        }
    }

    @Override
    public boolean isFinished() {
        return intakeInst_m.getLoopCount() >= 25;
    }

    @Override
    public void end(boolean interrupted) {
        filter_m.reset();
        intakeInst_m.stopIntake();
        intakeInst_m.setIntaking(false);
        RobotContainer.driverRumble_s.setRumble(RumbleType.kBothRumble, 0);

    }
}