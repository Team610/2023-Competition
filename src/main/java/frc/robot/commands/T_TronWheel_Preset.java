package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import static frc.robot.Constants.TronWheel.*;

public class T_TronWheel_Preset extends CommandBase {
    private double target_m;

    public T_TronWheel_Preset(double target) {
        target_m = target;
        addRequirements(RobotContainer.tronWheelInst_s);
    }

    @Override
    public void initialize() {
        if(!RobotContainer.coneMode_s){
            if(target_m == VAL_ANG_CONE_RAMP){
                target_m = VAL_ANG_CUBE_RAMP;
            } else if(target_m == VAL_ANG_CONE_SCORE){
                target_m = VAL_ANG_CUBE_SCORE;
            } else if(target_m == VAL_ANG_CONE_GROUND){
                target_m = VAL_ANG_CUBE_GROUND;
            } else if(target_m == VAL_ANG_CONE_TRANSPORT){
                target_m = VAL_ANG_CUBE_TRANSPORT;
            } else if(target_m == VAL_ANG_CONE_HYBRID){
                target_m = VAL_ANG_CUBE_HYBRID;
            }
        } else {
            if(target_m == VAL_ANG_CUBE_RAMP){
                target_m = VAL_ANG_CONE_RAMP;
            } else if(target_m == VAL_ANG_CUBE_SCORE){
                target_m = VAL_ANG_CONE_SCORE;
            } else if(target_m == VAL_ANG_CUBE_GROUND){
                target_m = VAL_ANG_CONE_GROUND;
            } else if(target_m == VAL_ANG_CUBE_TRANSPORT){
                target_m = VAL_ANG_CONE_TRANSPORT;
            } else if(target_m == VAL_ANG_CUBE_HYBRID){
                target_m = VAL_ANG_CONE_HYBRID;
            }
        }
    }

    @Override
    public void execute() {
        RobotContainer.tronWheelInst_s.setTargetPos(target_m);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
