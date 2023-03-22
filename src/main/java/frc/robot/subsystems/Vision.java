package frc.robot.subsystems;

import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.Subsystem610;

import static frc.robot.Constants.Vision.*;


public class Vision extends Subsystem610{
    private static Vision visionInst_s;
    private static Drivetrain drivetrain_m;

    private int ledMode_m;
    private int tv_m;
    private NetworkTable networkTable_m;
    private PIDController pid_m;
    private boolean isAimed_m;
    private int conePosition_m;
    private double angleSetPoint_m;
    private int distanceSetPoint_m;
    private Shuffleboard visionTab;

    public static Vision getInstance() {
        if (visionInst_s == null)
            visionInst_s = new Vision();
        return visionInst_s;
    }

    private Vision() {
        super("Limelight");
        
        ledMode_m = 0;
        conePosition_m = 0;
        angleSetPoint_m = 0;
        distanceSetPoint_m = 100;
        drivetrain_m = Drivetrain.getInstance();

        ShuffleboardTab visionTab = Shuffleboard.getTab("test");

        visionTab.add("Limelight", new HttpCamera("limelight", "http://10.6.10.12:5800/stream.mjpg"))
            .withWidget(BuiltInWidgets.kCameraStream)
            .withPosition(0, 0)
            .withSize(3, 3);


        //make sure the pipeline team number is set to 610
        networkTable_m = NetworkTableInstance.getDefault().getTable("limelight");
        networkTable_m.getEntry("ledMode").setNumber(ledMode_m);
        pid_m = new PIDController(VAL_KP, VAL_KI, VAL_KD);


        }

    public int getLedMode() {
        return ledMode_m;
    }

    public void setCamMode(int camMode){
        networkTable_m.getEntry("camMode").setNumber(camMode);
    }

    public void setLedMode(int ledMode){
        networkTable_m.getEntry("ledMode").setNumber(ledMode);
    }

    public int getConePosition(){
        return conePosition_m;
    }

    public void setConePosition(int newPosition){
        conePosition_m = newPosition;
        if (conePosition_m == 0){
            angleSetPoint_m = 0;
        }
        //cone is to the left
        else if (conePosition_m == 1){
            angleSetPoint_m = VAL_LEFT_ANGLE_OFSET;
        }
        //cone is to the right
        else{
            angleSetPoint_m = VAL_RIGHT_ANGLE_OFSET;
        }
    }

    /**
     * Fetch the horizontal offset from crosshair to target (tx)
     * @return tx
     */
    public double calcTx(){ 
        // ternary operator to return the horizontal angle if a valid target is detected
        return networkTable_m.getEntry("tx").getDouble(0.0);
    }

    /**
     * Fetch the vertical offset from crosshair to target (ty)
     * @return ty
     */
    public double calcTy(){
        return tv_m == 0 ? 0 : networkTable_m.getEntry("ty").getDouble(0.0);
    }

    /**
     * @return The distance from the Limelight to the target
     */
    public double calcDistance(){
        return tv_m == 0 ? 0 : 209.0 / Math.tan(Math.toRadians(21 + calcTy()));
    }

    /**
     * @return Boolean if the limelight is within the set threshold range
     */
    public boolean checkAim(){
        return isAimed_m = Math.abs(calcTx()) < 2 && tv_m > 0;
    }

    /**
     * Use WPILib PID to turn the robot to the desired direction
     */
    public void aim() {
        pid_m.setTolerance(1.4, 0);
        // drivetrain_m.setLeft(-pid_m.calculate(calcTx(), angleSetPoint)*0.3);
        // drivetrain_m.setRight(pid_m.calculate(calcTx(), angleSetPoint)*0.3);
    }

    /**
     * Use WPILib PID to move the robot to the desired distance
     */
    public void drive(){
        pid_m.setTolerance(1.4, 0);
        // drivetrain_m.setLeft(-pid_m.calculate(calcDistance(), distanceSetPoint)*0.3);
    }

    public void writeDashboard(){
        SmartDashboard.putNumber("Angle", Math.round(calcTx() * 1e5) / 1e5);
        SmartDashboard.putBoolean("Aimed", isAimed_m);
        SmartDashboard.putNumber("Cone Poisition", conePosition_m);
        SmartDashboard.putNumber("distance", Math.round(calcDistance() * 1e5) / 1e5);
        SmartDashboard.putNumber("PID LEFT ANGLE", -pid_m.calculate(calcTx(), angleSetPoint_m)*0.3);
        SmartDashboard.putNumber("PID LEFT DRIVE", -pid_m.calculate(calcDistance(), distanceSetPoint_m)*0.3);
    }

    @Override
    public void periodic() {
        tv_m = (int) networkTable_m.getEntry("tv").getDouble(0.0);
        writeDashboard();
    }
    
    @Override
    public void initSendable(SendableBuilder builder) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addToDriveTab(ShuffleboardTab tab) {
        // TODO Auto-generated method stub
        
    }
    
}