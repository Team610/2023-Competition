package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

import static frc.robot.Constants.Cascade.*;
import static frc.robot.Constants.*;

public class T_Cascade_Move extends CommandBase {
    private boolean down;

    public T_Cascade_Move() {
        addRequirements(RobotContainer.cascadeInst_s);
    }

    @Override
    public void initialize() {
        down = RobotContainer.cascadeInst_s.getTicks() - RobotContainer.cascadeInst_s.getTargetPos() > 0 ? true : false;
    }

    @Override
    public void execute() {
        if(!RobotContainer.cascadeInst_s.getSafety()){
            if(RobotContainer.cascadeInst_s.getManual()){
                double speed = MathUtil.applyDeadband(-RobotContainer.operator_s.getLeftY() * VAL_MAX_SPEED_MANUAL, VAL_DEADBAND);
                RobotContainer.cascadeInst_s.spin(speed);           
            } else {
                RobotContainer.cascadeInst_s.spinMagic(down);
            }
        }
    }

    @Override
    public boolean isFinished(){
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.cascadeInst_s.stop();
    }
}
