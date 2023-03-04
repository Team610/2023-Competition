package frc.robot.subsystems;

import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.util.Subsystem610;

public class Vision extends Subsystem610{
    private static Vision visionInst_s;
    private int ledMode_m;
    private NetworkTable networkTable_m;

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

    @Override
    public void periodic() {
        // m_tv = (int) m_networkTable.getEntry("tv").getDouble(0.0);
        // writeDashboard();
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
