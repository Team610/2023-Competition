package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class T_Vision_Drive extends CommandBase{

    public T_Vision_Drive(){
        addRequirements(RobotContainer.visionInst_s);
    }

    /**
     * Turns limelight on
     */
    @Override
    public void initialize() {
        RobotContainer.visionInst_s.setCamMode(0);
        RobotContainer.visionInst_s.setLedMode(0);
    }

    /**
     * Aims limelight
     */
    @Override
    public void execute() {
        RobotContainer.visionInst_s.drive();
    }

    /**
     * Turns limelight off
     */
    @Override
    public void end(boolean interrupted) {
        RobotContainer.visionInst_s.setCamMode(1);
        RobotContainer.visionInst_s.setLedMode(1);
    }

    @Override
    public boolean isFinished() {
        return RobotContainer.visionInst_s.checkDistance();
    }
}
