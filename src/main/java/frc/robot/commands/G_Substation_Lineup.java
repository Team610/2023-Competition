package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class G_Substation_Lineup extends SequentialCommandGroup{

    public G_Substation_Lineup() {
        addCommands(
                new T_Vision_Lineup(),
                new T_Vision_Aim_Substation()
        );
}
    
}
