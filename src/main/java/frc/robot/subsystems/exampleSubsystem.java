package frc.robot.subsystems;

import static frc.robot.Constants.*;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.motorcontrol.PWMTalonFX;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import frc.robot.util.Subsystem610;

public class exampleSubsystem extends Subsystem610{

    private static exampleSubsystem exampleSubsystemInst_s;
    private PWMTalonFX exampleMotor_m;

    public static exampleSubsystem getInstance() {
        if (exampleSubsystemInst_s == null) {
            exampleSubsystemInst_s = new exampleSubsystem();
        }
        return exampleSubsystemInst_s;
    }

    private exampleSubsystem() {
        super("exampleSubsystem");
        exampleMotor_m = new PWMTalonFX(CAN_EXAMPLE_MOTOR);
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
