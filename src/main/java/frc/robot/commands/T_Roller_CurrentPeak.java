package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Roller;

public class T_Roller_CurrentPeak extends CommandBase{
    private Roller rollerInst_s;
    private double current_m;

    private T_Roller_CurrentPeak() {
        rollerInst_s = Roller.getInstance();
        addRequirements(rollerInst_s);

        current_m = 0;
    }
    
    @Override
    public void execute() {
        current_m = rollerInst_s.getCurrent();

        
    }
}
