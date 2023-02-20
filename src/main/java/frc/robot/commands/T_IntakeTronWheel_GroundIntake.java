package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.TronWheel;

import static frc.robot.Constants.TronWheel.*;

public class T_IntakeTronWheel_GroundIntake extends CommandBase {
    private TronWheel tronWheelInst_m;
    private Intake intakeInst_m;

    public T_IntakeTronWheel_GroundIntake() {
        tronWheelInst_m = TronWheel.getInstance();
        intakeInst_m = Intake.getInstance();
        addRequirements(tronWheelInst_m, intakeInst_m);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(!intakeInst_m.getHasGamePiece()) {
            tronWheelInst_m.setTargetPos(VAL_ANGLE_GROUND_INIT);
        } else {
            tronWheelInst_m.setTargetPos(VAL_ANGLE_GROUND_FINAL);
        }

    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return tronWheelInst_m.getTargetPos() == VAL_ANGLE_GROUND_FINAL;
    }
}
    
