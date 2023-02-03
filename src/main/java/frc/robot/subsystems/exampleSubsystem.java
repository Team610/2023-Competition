package frc.robot.subsystems;

import static frc.robot.Constants.*;
import static frc.robot.Constants.exampleSubsystem.*;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.Subsystem610;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class exampleSubsystem extends Subsystem610{

    private static exampleSubsystem exampleSubsystemInst_s;
    private TalonFX exampleMotor_m;

    public static exampleSubsystem getInstance() {
        if (exampleSubsystemInst_s == null) {
            exampleSubsystemInst_s = new exampleSubsystem();
        }
        return exampleSubsystemInst_s;
    }

    private exampleSubsystem() {
        super("exampleSubsystem");
        exampleMotor_m = new TalonFX(CAN_EXAMPLE_MOTOR);
    }

    public void exampleMethod(double speed) {
        exampleMotor_m.set(ControlMode.PercentOutput,speed);
    }

    public void periodic() {
        writeSmartDashboard();
    }

    private void writeSmartDashboard() {
        SmartDashboard.putNumber("exampleSubsystem/Loop Count", getLoopCount());
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        
    }

    @Override
    public void addToDriveTab(ShuffleboardTab tab) {
        
    }
    
}
