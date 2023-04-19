package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class T_Pidgey_Aim_Score extends CommandBase {
    
    public T_Pidgey_Aim_Score() {
        addRequirements(RobotContainer.drivetrainInst_s);
    }

    @Override
    public void initialize() {
        RobotContainer.drivetrainInst_s.setAimSetpoint_m(0);
    }

    @Override
    public void execute() {
        RobotContainer.drivetrainInst_s.aim();
        if(RobotContainer.drivetrainInst_s.getAimed()) {
            RobotContainer.driverRumble_s.setRumble(RumbleType.kBothRumble, 0.02);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
