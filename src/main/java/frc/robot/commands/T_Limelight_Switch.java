package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Vision;

public class T_Limelight_Switch extends CommandBase{
    private Vision visionInst_m;

    public T_Limelight_Switch() {
        visionInst_m = Vision.getInstance();
        addRequirements(visionInst_m);
    }

    @Override
    public void execute() {
        if(visionInst_m.getPipeLine() == 1){
            visionInst_m.setPipeLine(2);
        }else{
            visionInst_m.setPipeLine(1);
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}