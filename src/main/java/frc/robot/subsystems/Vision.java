package frc.robot.subsystems;

import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.util.Subsystem610;

public class Vision extends Subsystem610{
    private static Vision visionInst_s;
    private int ledMode_m, camMode_m;
    private NetworkTable networkTable_m;
    private HttpCamera cam_m;

    private Vision() {
        super("Limelight");
        
        ledMode_m = camMode_m = 1;

        cam_m = new HttpCamera("limelight", "http://10.6.10.12:5800/stream.mjpg");
        
        networkTable_m = NetworkTableInstance.getDefault().getTable("limelight");
        networkTable_m.getEntry("ledMode").setNumber(ledMode_m);
        networkTable_m.getEntry("camMode").setNumber(camMode_m);
    }

    public static Vision getInstance() {
        if (visionInst_s == null)
            visionInst_s = new Vision();
        return visionInst_s;
    }

    public int getLedMode() {
        return ledMode_m;
    }

    public void setLedMode(int ledMode){
        ledMode_m = ledMode;
        networkTable_m.getEntry("ledMode").setNumber(getLedMode());
    }

    public int getCamMode() {
        return camMode_m;
    }
    
    public void setCamMode(int camMode) {
        camMode_m = camMode;
        networkTable_m.getEntry("camMode").setNumber(getCamMode());
    }

    @Override
    public void periodic() {
        networkTable_m = NetworkTableInstance.getDefault().getTable("limelight");
    }
    
    @Override
    public void initSendable(SendableBuilder builder) {
        // TODO Auto-generated method stub
    }

    @Override
    public void addToDriveTab(ShuffleboardTab tab) {
        tab.add(cam_m)
            .withWidget(BuiltInWidgets.kCameraStream)
            .withPosition(0, 0)
            .withSize(4, 4);
    }
    
}
