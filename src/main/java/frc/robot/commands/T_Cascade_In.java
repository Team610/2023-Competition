package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Cascade;

import static frc.robot.Constants.Cascade.*;

public class T_Cascade_In extends CommandBase{

    private Cascade cascadeInst_m;

    public T_Cascade_In(){
        cascadeInst_m = Cascade.getInstance();
        addRequirements(cascadeInst_m);
    }

    /**
     * Move cascade out at a fixed speed based on scoring enum
     */
    @Override
    public void execute() {
        if(!cascadeInst_m.getSafety()){
            cascadeInst_m.spin(-VAL_MAX_SPEED);
        }
    }

    @Override
    public boolean isFinished(){
        return false;
    }

    @Override
    public void end(boolean interrupted){
        cascadeInst_m.spin(0);
    }
    
}
