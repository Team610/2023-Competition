package frc.robot.subsystems;

import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.Subsystem610;

public class Vision extends Subsystem610{
    private static Vision visionInst_s;
    private int ledMode_m;
    private int tv_m;
    private NetworkTable networkTable_m;
    private double pipeLine = 1;
	

    public static Vision getInstance() {
        if (visionInst_s == null)
            visionInst_s = new Vision();
        return visionInst_s;
    }

    private Vision() {
        super("Limelight");
        
        ledMode_m = 1;

        new HttpCamera("limelight", "http://10.6.10.12:5800/stream.mjpg");
        
        networkTable_m = NetworkTableInstance.getDefault().getTable("limelight");
        networkTable_m.getEntry("ledMode").setNumber(ledMode_m);
    }

    public int getLedMode() {
        return ledMode_m;
    }

    /**
     * Fetch the horizontal offset from crosshair to target (tx)
     * @return tx
     */
    public double calcTx(){ 
        // ternary operator to return the horizontal angle if a valid target is detected
        return networkTable_m.getEntry("tx").getDouble(pipeLine);
    }

    /**
     * Fetch the vertical offset from crosshair to target (ty)
     * @return ty
     */
    public double calcTy(){
        return tv_m == 0 ? 0 : networkTable_m.getEntry("ty").getDouble(pipeLine);
    }

    /**
     * @return The distance from the Limelight to the target
     */
    public double calcDistance(){
        return tv_m == 0 ? 0 : 209.0 / Math.tan(Math.toRadians(21 + calcTy()));
    }

    public void setPipeLine(double pipeLine) {
        this.pipeLine = pipeLine;
    }
    public double getPipeLine() {
        return pipeLine;
    }

    public void writeDashboard(){
        SmartDashboard.putNumber("tx", Math.round(calcTx() * 1e5) / 1e5);
        SmartDashboard.putNumber("distance", Math.round(calcDistance() * 1e5) / 1e5);
    }

    @Override
    public void periodic() {
        tv_m = (int) networkTable_m.getEntry("tv").getDouble(pipeLine);
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
