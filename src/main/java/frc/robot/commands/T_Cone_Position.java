package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class T_Cone_Position extends CommandBase{
    private int conePosition_m;
    public T_Cone_Position (int conePosition){
        conePosition_m = conePosition;
    }

    /**
     * Make sure cone position is set to 0
     */
    @Override
    public void initialize() {
        RobotContainer.visionInst_s.setConePosition(0);
    }

    /**
     * Aims limelight
     */
    @Override
    public void execute() {
        RobotContainer.visionInst_s.setConePosition(conePosition_m);
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
