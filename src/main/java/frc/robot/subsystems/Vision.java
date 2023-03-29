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
    private PIDController pidAngle_m;
    private PIDController pidDrive_m;
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
        distanceSetPoint_m = 372;
        drivetrain_m = Drivetrain.getInstance();

        ShuffleboardTab visionTab = Shuffleboard.getTab("test");

        visionTab.add("Limelight", new HttpCamera("limelight", "http://10.6.10.12:5800/stream.mjpg"))
            .withWidget(BuiltInWidgets.kCameraStream)
            .withPosition(0, 0)
            .withSize(3, 3);


        //make sure the pipeline team number is set to 610
        networkTable_m = NetworkTableInstance.getDefault().getTable("limelight");

        networkTable_m.getEntry("ledMode").setNumber(ledMode_m);
        pidAngle_m = new PIDController(VAL_ANGLE_KP, VAL_ANGLE_KI, VAL_ANGLE_KD);
        pidDrive_m = new PIDController(VAL_DRIVE_KP, VAL_DRIVE_KI, VAL_DRIVE_KD);

        }

    public int getLedMode() {
        return ledMode_m;
    }

    public void setCamMode(int camMode){
        networkTable_m.getEntry("camMode").setNumber(camMode);
    }

    /**
     * 0 for on, 1 for off
     * @param ledMode
     */
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

    public boolean checkDistance(){
        return calcDistance() > 0 ? calcDistance() - distanceSetPoint_m < 10 : false ;
    }

    /**
     * @return Boolean if the limelight is within the set threshold range
     */
    public boolean checkAim(){
        return Math.abs(calcTx()) > 0 ? isAimed_m = Math.abs(calcTx()) < 2 && tv_m > 0 && calcDistance() - distanceSetPoint_m < 10 : false;
    }

    /**
     * Use WPILib PID to turn and drive the robot to the desired direction
     */
    public double[] aim() {
        double steeringAdjust = 0;
        double headingError = calcTx() - angleSetPoint_m;
        double distanceError = calcTy();

        if (headingError > 1){
            steeringAdjust = -pidAngle_m.calculate(headingError) - VAL_MIN_POWER;
        }
        else if (headingError < 1){
            steeringAdjust = pidAngle_m.calculate(headingError) + VAL_MIN_POWER;
        }

        // drivetrain_m.setLeft(steeringAdjust + pidDrive_m.calculate(distanceError));
        // drivetrain_m.setRight(steeringAdjust + pidDrive_m.calculate(distanceError));

        return new double[]{steeringAdjust + pidDrive_m.calculate(distanceError), steeringAdjust + pidDrive_m.calculate(distanceError)};

    }

    /**
     * Use WPILib PID to move the robot to the desired distance
     */
    public void drive(){
        drivetrain_m.setLeft(pidDrive_m.calculate(calcDistance(), distanceSetPoint_m));
        drivetrain_m.setLeft(pidDrive_m.calculate(calcDistance(), distanceSetPoint_m));
    }

    public void writeDashboard(){
        SmartDashboard.putNumber("Angle", Math.round(calcTx() * 1e5) / 1e5);
        SmartDashboard.putBoolean("Aimed", isAimed_m);
        SmartDashboard.putNumber("Cone Poisition", conePosition_m);
        SmartDashboard.putNumber("distance", Math.round(calcDistance() * 1e5) / 1e5);
        SmartDashboard.putNumber("PID LEFT", aim()[0]);
        SmartDashboard.putNumber("PID RIGHT", aim()[1]);
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