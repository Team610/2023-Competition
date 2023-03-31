package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

import static frc.robot.Constants.Intake.*;

public class T_Intake_Out extends CommandBase {
    private boolean turbo_m;

    public T_Intake_Out(boolean turbo) {
        turbo_m = turbo;
        addRequirements(RobotContainer.intakeInst_s);
    }

    @Override
    public void initialize() {
        RobotContainer.intakeInst_s.setIntaking(false);
    }

    @Override
    public void execute() {
        RobotContainer.intakeInst_s.intake(turbo_m ? VAL_OUT_TURBO : RobotContainer.coneMode_s ? VAL_OUT_CONE_NORMAL : VAL_OUT_CUBE_NORMAL);
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
        RobotContainer.intakeInst_s.stopIntake();
        //reset cone position
        RobotContainer.visionInst_s.setConePosition(0);
    }
}
