package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Vision;

public class T_Vision_Drive extends CommandBase{
    private Vision visionInst_m;

    public T_Vision_Drive(){
        visionInst_m = Vision.getInstance();
        addRequirements(visionInst_m);
    }

    /**
     * Turns limelight on
     */
    @Override
    public void initialize() {
        visionInst_m.setCamMode(0);
        visionInst_m.setLedMode(0);
    }

    /**
     * Aims limelight
     */
    @Override
    public void execute() {
        visionInst_m.drive();
    }

    /**
     * Turns limelight off
     */
    @Override
    public void end(boolean interrupted) {
        visionInst_m.setCamMode(1);
        visionInst_m.setLedMode(1);
    }

    @Override
    public boolean isFinished() {
        return visionInst_m.checkDistance();
    }
}
