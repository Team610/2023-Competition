package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Cascade;

import static frc.robot.Constants.Cascade.*;

public class T_Cascade_Home extends CommandBase {
    private Cascade cascadeInst_m;

    public T_Cascade_Home() {
        cascadeInst_m = Cascade.getInstance();
        addRequirements(cascadeInst_m);
    }

    @Override
    public void initialize() {
        cascadeInst_m.cascadeBotLimitCheck();
        cascadeInst_m.spin(-0.2);
    }

    @Override
    public void execute() {
    }

    /**
     * Finish when the cascade limit switch is pressed
     */
    
    @Override
    public boolean isFinished() {
        return cascadeInst_m.cascadeBotLimitCheck();
    }

    @Override
    public void end(boolean interrupted) {
        cascadeInst_m.stop();
        cascadeInst_m.setTargetPos(VAL_GROUND_PRESET);
        cascadeInst_m.setSafety(false);
    }
}
