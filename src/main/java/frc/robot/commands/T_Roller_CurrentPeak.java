package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Roller;

public class T_Roller_CurrentPeak extends CommandBase{
    private Roller rollerInst_s;
    private double current_m;
    private boolean wait;
    private int count;
    private int time;

    private T_Roller_CurrentPeak() {
        rollerInst_s = Roller.getInstance();
        addRequirements(rollerInst_s);

        current_m = 0;
        wait = false;
        time = 0;
    }
    
    @Override
    public void execute() {
        current_m = rollerInst_s.getCurrent(); 
        if(current_m > 12 && !wait) {
            count++;
            wait = true;
        }
        if(wait) {
            time++;
        }
        if(time == 100) {
            time = 0;
            wait = false;
        }
        if(count % 2 == 0 && !wait) rollerInst_s.stop(); 
    }


        /*while(on) {
            double currentStart, currentFinal;
            if(current_m > 12) currentStart = rollerInst_s.getCurrent();
            //wait a quarter of a second
            //while(//new time - getTime < 1/4 second) {nothing stalls out}
            currentFinal = rollerInst_s.getCurrent();
            if(currentFinal - currentStart > 0.5) {
                count++; //increase count (first time)
                if(count % 2 == 0) on = false; //motors stop  
            }
        }
        //motors stop
    }*/
    
}
