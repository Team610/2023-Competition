package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;

public class T_Vision_Light extends CommandBase{
    private Vision visionInst_m;
    private Drivetrain driveTrainInst_m;

    public T_Vision_Light() {
        visionInst_m = Vision.getInstance();
        driveTrainInst_m = Drivetrain.getInstance();
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
        return visionInst_m.checkAim();
    }
}
