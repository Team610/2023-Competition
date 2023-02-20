package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;


public class G_Drivetrain_Balance_Group extends SequentialCommandGroup{
    /**
         * Add all the commands you would like to happen in auto to this, in order of
         * occurence
        */

    private static double leftInit_s;
    private static double rightInit_s;
    private Drivetrain drivetrainInst_m;
    public G_Drivetrain_Balance_Group() {
        drivetrainInst_m = Drivetrain.getInstance();
        addCommands(new T_Drivetrain_Tilt());
        leftInit_s = drivetrainInst_m.getLeftMeters();
        rightInit_s = drivetrainInst_m.getRightMeters();
        addCommands(new T_Drivetrain_PID_Balance(leftInit_s, rightInit_s));
        //addCommands(new T_Test_Pitch());
    }
}
