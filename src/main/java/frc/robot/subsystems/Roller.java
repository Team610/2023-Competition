package main.java.frc.robot.subsystems;

import static frc.robot.Constants.Roller.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.util.Subsystem610;

public class Roller extends Subsystem610 {
    private static Roller rollerInst_s;
    private WPI_TalonSRX talon_m;

    private Roller() {
        super("Roller");
        talon_m = new WPI_TalonSRX(CAN_ROLLER);
    }

    public static Roller getInstance() {
        if (rollerInst_s == null) {
            rollerInst_s = new Roller();
        }
        return rollerInst_s;
    }

    public double getCurrent() {
        return talon_m.getStatorCurrent();
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
