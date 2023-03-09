package frc.robot.subsystems;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.Subsystem610;

public class Vision extends Subsystem610{
    private static Vision visionInst_s;

    private int ledMode_m;
    private int tv_m;
    private NetworkTable networkTable_m;

    public static Vision getInstance() {
        if (visionInst_s == null)
            visionInst_s = new Vision();
        return visionInst_s;
    }

    private Vision() {
        super("Limelight");
        
        ledMode_m = 0;

        ShuffleboardTab visionTab = Shuffleboard.getTab("test");

        visionTab.add("Limelight", new HttpCamera("limelight", "http://10.6.10.12:5800/stream.mjpg"))
            .withWidget(BuiltInWidgets.kCameraStream)
            .withPosition(0, 0)
            .withSize(4, 4);


        //make sure the pipeline team number is set to 610
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

    public void writeDashboard(){
        
        SmartDashboard.putNumber("pipeline", (double) networkTable_m.getEntry("pipeline").getNumber(1));
        SmartDashboard.putNumber("tx", Math.round(calcTx() * 1e5) / 1e5);
        SmartDashboard.putNumber("distance", Math.round(calcDistance() * 1e5) / 1e5);
        SmartDashboard.putNumber("tv2", tv_m);
        SmartDashboard.putData(visionInst_s);
        SmartDashboard.putNumber("led1", ledMode_m);
        SmartDashboard.putNumber("led2", networkTable_m.getEntry("ledMode").getDouble(0));
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
