package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class G_Drivetrain_Group extends SequentialCommandGroup{
    /**
         * Add all the commands you would like to happen in auto to this, in order of
         * occurence
        */
    public G_Drivetrain_Group() {
        addCommands(new T_DriveTo(1,0.3));
        //addCommands(new T_BalanceOnStat());
    }
}
