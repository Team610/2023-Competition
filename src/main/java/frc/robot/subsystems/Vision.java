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

public class Vision extends Subsystem610 {
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
    public ShuffleboardTab visionTab_m;

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

        visionTab_m = Shuffleboard.getTab("test");

        visionTab_m.add("Limelight", new HttpCamera("limelight", "http://10.6.10.12:5800/stream.mjpg"))
                .withWidget(BuiltInWidgets.kCameraStream)
                .withPosition(0, 0)
                .withSize(3, 3);

        // Make sure the pipeline team number is set to 610
        networkTable_m = NetworkTableInstance.getDefault().getTable("limelight");

        networkTable_m.getEntry("ledMode").setNumber(ledMode_m);
        setCamMode(0);
        pidAngle_m = new PIDController(VAL_ANGLE_KP, VAL_ANGLE_KI, VAL_ANGLE_KD);
        pidDrive_m = new PIDController(VAL_DRIVE_KP, VAL_DRIVE_KI, VAL_DRIVE_KD);

    }

    /**
     * @return The current state of the Limelight's LED's
     */
    public int getLedMode() {
        return ledMode_m;
    }

    /**
     * Turns the led's of the limelight on or off
     * 
     * @param ledMode 0 for on, 1 for off
     */
    public void setLedMode(int ledMode) {
        networkTable_m.getEntry("ledMode").setNumber(ledMode);
    }

    /**
     * Switches the camera mode of the limelight
     * 
     * @param camMode 0 for vision processor, 1 for driver cam
     */
    public void setCamMode(int camMode) {
        networkTable_m.getEntry("camMode").setNumber(camMode);
    }

    public int getConePosition() {
        return conePosition_m;
    }

    /**
     * Changes limelight offset to left, center, right
     * 
     * @param newPosition new cone position in intake
     */
    public void setConePosition(int newPosition) {
        conePosition_m = newPosition;
        if (conePosition_m == 0) {
            angleSetPoint_m = 0;
        }
        // cone is to the left
        else if (conePosition_m == 1) {
            angleSetPoint_m = VAL_LEFT_ANGLE_OFFSET;
        }
        // cone is to the right
        else {
            angleSetPoint_m = VAL_RIGHT_ANGLE_OFFSET;
        }
    }

    /**
     * @return The horizontal offset from crosshair to target (tx)
     */
    public double calcTx() {
        // ternary operator to return the horizontal angle if a valid target is detected
        return networkTable_m.getEntry("tx").getDouble(0.0);
    }

    /**
     * @return The vertical offset from crosshair to target (ty)
     */
    public double calcTy() {
        return tv_m == 0 ? 0 : networkTable_m.getEntry("ty").getDouble(0.0);
    }

    /**
     * @return The distance from the Limelight to the target
     */
    public double calcDistance() {
        return tv_m == 0 ? 0 : 209.0 / Math.tan(Math.toRadians(21 + calcTy()));
    }

    /**
     * @return True only when the Limelight is within a set distance setpoint
     */
    public boolean checkDistance() {
        return calcDistance() > 0 ? calcDistance() - distanceSetPoint_m < 10 : false;
    }

    /**
     * @return True only when the limelight is within the set threshold range
     */
    public boolean checkAim() {
        return Math.abs(calcTx()) > 0
                ? isAimed_m = Math.abs(calcTx()) < 2 && tv_m > 0 && calcDistance() - distanceSetPoint_m < 10
                : false;
    }

    /**
     * @return An array with left and right motor output values for drivetrain
     */
    public double[] getAimPID() {
        double steeringAdjust = pidAngle_m.calculate(calcTx(), angleSetPoint_m);

        return new double[] { -steeringAdjust, steeringAdjust };
    }

    /**
     * Use WPILib PID to rotate the robot to aim at the target
     */
    public void aim() {
        double[] steeringAdjust = getAimPID();
        drivetrain_m.setLeft(steeringAdjust[0]);
        drivetrain_m.setRight(steeringAdjust[1]);
    }

    /**
     * Use WPILib PID to move the robot to the desired distance
     */
    public void drive() {
        drivetrain_m.setLeft(pidDrive_m.calculate(calcDistance(), distanceSetPoint_m));
        drivetrain_m.setLeft(pidDrive_m.calculate(calcDistance(), distanceSetPoint_m));
    }

    /**
     * Contains all items put to SmartDashboard in the Vision class
     */
    public void writeSmartDashboard() {
        SmartDashboard.putNumber("Angle", Math.round(calcTx() * 1e5) / 1e5);
        SmartDashboard.putBoolean("Aimed", isAimed_m);
        SmartDashboard.putNumber("Cone Poisition", conePosition_m);
        SmartDashboard.putNumber("Heading Error", calcTx() - angleSetPoint_m);
        SmartDashboard.putNumber("Distance Error", calcDistance() - distanceSetPoint_m);
        SmartDashboard.putNumber("PID LEFT", getAimPID()[0]);
        SmartDashboard.putNumber("PID RIGHT", getAimPID()[1]);
        SmartDashboard.putNumber("PID", pidAngle_m.calculate(calcTx() - angleSetPoint_m));
    }

    @Override
    public void periodic() {
        tv_m = (int) networkTable_m.getEntry("tv").getDouble(0.0);
        writeSmartDashboard();
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