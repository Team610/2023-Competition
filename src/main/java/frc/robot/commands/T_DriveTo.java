package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class T_DriveTo extends CommandBase {
    private Drivetrain drivetrainInst_m;
    private static double initDis_s;
    private static double toDis_s;
    private static double percPow_s;
    /** 
     * Creates a new DriveForDistanceCommand. 
     **/
    public T_DriveTo(double disPara_s, double percentP_s) {
        drivetrainInst_m = Drivetrain.getInstance();
        toDis_s = disPara_s;
        percPow_s = percentP_s;
        addRequirements(drivetrainInst_m);
    }
    /**
     * Called method when code is initialized
     */
    @Override
    public void initialize() {
        initDis_s = drivetrainInst_m.getRightMeters();
        drivetrainInst_m.setLeft(percPow_s);
        drivetrainInst_m.setRight(percPow_s);
    }

    @Override
    public void execute() {
    }
    /**
     * Returns when code is interrupted||ends (Set the robot to coast)
     */
    @Override
    public void end(boolean interrupted) {
        drivetrainInst_m.setCoast(); 
    }

  /**
   * Returns true when the distance fits the requirements
   */
  @Override
  public boolean isFinished() {
    return drivetrainInst_m.getRightMeters() >= initDis_s+toDis_s; 
  }
}