package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.states.CascadeState;
import frc.robot.subsystems.Cascade;

import static frc.robot.Constants.*;
import static frc.robot.Constants.Cascade.*;

public class T_Cascade_Move extends CommandBase {
    private Cascade cascadeInst_m;
    private boolean down;

    public T_Cascade_Move() {
        cascadeInst_m = Cascade.getInstance();
        addRequirements(cascadeInst_m);
    }

    @Override
    public void initialize() {
        down = cascadeInst_m.getTicks() - cascadeInst_m.getTargetPos() > 0 ? true : false;
    }

    @Override
    public void execute() {
        if(!cascadeInst_m.getSafety()){
            if(cascadeInst_m.getManual()){
                double speed = MathUtil.applyDeadband(RobotContainer.operator_s.getLeftY() * VAL_MAX_SPEED_MANUAL, VAL_DEADBAND);
                cascadeInst_m.spin(speed);
            } else {
                cascadeInst_m.spinMagic(down);
            }
        }
    }

    @Override
    public boolean isFinished(){
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        cascadeInst_m.stop();
    }
}
