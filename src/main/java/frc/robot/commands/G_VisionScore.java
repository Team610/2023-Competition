package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.states.CascadeState;

public class G_VisionScore extends SequentialCommandGroup{

    public G_VisionScore(){
        addCommands(
            //aim and extend arm
            Commands.parallel(new A_Cascade_Move(CascadeState.HIGH, RobotContainer.coneMode_s, 110), new A_Intake_In(50), new T_Vision_Aim()),
            //score the cone
            new A_Intake_Out(RobotContainer.coneMode_s)
        );
    }
    
}
