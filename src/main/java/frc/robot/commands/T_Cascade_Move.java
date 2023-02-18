package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Cascade;

import static frc.robot.Constants.Cascade.*;

public class T_Cascade_Move extends CommandBase {
    private Cascade cascadeInst_m;

    public T_Cascade_Move() {
        cascadeInst_m = Cascade.getInstance();
        addRequirements(cascadeInst_m);
    }

    /**
     * Move cascade based on operator input
     */
    @Override
    public void execute() {
        if(!cascadeInst_m.getSafety()){
            double speed = MathUtil.applyDeadband(RobotContainer.operator_s.getLeftY() * VAL_MAX_SPEED_MANUAL, 0.02);
            cascadeInst_m.spin(speed);
        }
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}
