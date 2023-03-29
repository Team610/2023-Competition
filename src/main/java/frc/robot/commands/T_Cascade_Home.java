package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

import static frc.robot.Constants.Cascade.*;

public class T_Cascade_Home extends CommandBase {

    public T_Cascade_Home() {
        addRequirements(RobotContainer.cascadeInst_s);
    }

    @Override
    public void initialize() {
        RobotContainer.cascadeInst_s.cascadeBotLimitCheck();
        RobotContainer.cascadeInst_s.spin(-0.2);
    }

    @Override
    public void execute() {
    }

    /**
     * Finish when the cascade limit switch is pressed
     */
    
    @Override
    public boolean isFinished() {
        return RobotContainer.cascadeInst_s.cascadeBotLimitCheck();
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.cascadeInst_s.stop();
        RobotContainer.cascadeInst_s.setTargetPos(VAL_GROUND_PRESET);
        RobotContainer.cascadeInst_s.setSafety(false);
    }
}
