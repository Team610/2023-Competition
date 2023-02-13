package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.util.MotorConfig;
import frc.robot.util.Subsystem610;

import static frc.robot.Constants.Cascade.*;

public class Cascade extends Subsystem610 {
    private static Cascade cascadeInst_s;
    private WPI_TalonFX cascadeFX_m;

    private Cascade() {
        super("Cascade");
        cascadeFX_m = MotorConfig.configCascadeMotor(CAN_CASCADE);
    }

    public static Cascade getInstance(){
        if(cascadeInst_s == null){
            cascadeInst_s = new Cascade();
        }
        return cascadeInst_s;
    }

    /**
     * Spins the Cascade motor, in terms of percentage.
     * @param spin The desired speed percentage
     */
    public void spin(double spin) {
        cascadeFX_m.set(ControlMode.PercentOutput, spin);
    }

    /**
     * Sets the cascade motor output to 0
     */
    public void stop() {
        cascadeFX_m.set(ControlMode.PercentOutput, 0);
    }

    public boolean isCascadeLimit() {
        return cascadeFX_m.isRevLimitSwitchClosed() == 1;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
    }

    @Override
    public void addToDriveTab(ShuffleboardTab tab) {
    }

    
}