package frc.robot.subsystems;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.RobotContainer;
import frc.robot.util.Subsystem610;

public class Infrastructure extends Subsystem610 {
    private static Infrastructure infrastructure_s;

    private final PowerDistribution pdb_s;

    private Infrastructure() {
        super("Infrastructure");
        pdb_s = new PowerDistribution();
    }

    public static Infrastructure getInstance() {
        if (infrastructure_s == null) {
            infrastructure_s = new Infrastructure();
        }
        return infrastructure_s;
    }

    public void setSwitchable(boolean on) {
        pdb_s.setSwitchableChannel(on);
    }

    public double getVoltage(){
        return pdb_s.getVoltage();
    }

    @Override
    public void periodic() {
        setSwitchable(!RobotContainer.coneMode_s);
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
