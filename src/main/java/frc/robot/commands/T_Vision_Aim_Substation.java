package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class T_Vision_Aim_Substation extends CommandBase{

    public T_Vision_Aim_Substation() {
        addRequirements(RobotContainer.drivetrainInst_s);
    }

    @Override
    public void initialize() {
        boolean redAllaince = DriverStation.getAlliance() == Alliance.Red;
        RobotContainer.drivetrainInst_s.setAimSetpoint_m(redAllaince ? 90 : 270);
    }

    @Override
    public void execute() {
        RobotContainer.drivetrainInst_s.aim();
    }

    @Override
    public boolean isFinished() {
        return RobotContainer.drivetrainInst_s.getAimed();
    }
    
}
