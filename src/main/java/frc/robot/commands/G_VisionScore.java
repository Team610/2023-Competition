package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static frc.robot.Constants.Cascade.*;

public class G_VisionScore extends SequentialCommandGroup{

    public G_VisionScore(){
        addCommands(
            //aim and extend arm
            Commands.parallel(new A_Cascade_Move(VAL_HIGH_PRESET, 110), new A_Intake_In(50), new T_Vision_Aim()),
            //score the cone
            new A_Intake_Out()
        );
    }
    
}
