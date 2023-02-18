package frc.robot.commands;

import static frc.robot.Constants.Drivetrain.*;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Default teleop drive mode
 */
public class T_Test_Pitch extends CommandBase {
    private Drivetrain drivetrainInst_m;

    public T_Test_Pitch() {
        drivetrainInst_m = Drivetrain.getInstance();
        addRequirements(drivetrainInst_m);
    }
    @Override
    public void execute() {
        SmartDashboard.putNumber("Pitch Value", drivetrainInst_m.getPitch());
        
    }

}