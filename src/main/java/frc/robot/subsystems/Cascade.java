package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.MotorConfig;
import frc.robot.util.Subsystem610;

import static frc.robot.Constants.Cascade.*;

public class Cascade extends Subsystem610 {
    private static Cascade cascadeInst_s;
    private WPI_TalonFX cascadeFX_m;
    private boolean isHomed_m;
    private boolean safety_m;

    private Cascade() {
        super("Cascade");
        isHomed_m = false;
        safety_m = true;
        cascadeFX_m = MotorConfig.configCascadeMotor(CAN_CASCADE, false, true);
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

    /**
     * Sets the cascadeFX encoder to zero position (ticks)
     */
    public void resetCascadeFX() {
        cascadeFX_m.setSelectedSensorPosition(0);
    }
    
    /**
     * @return If the bottom limit switch is pressed
     */
    public boolean cascadeBotLimitCheck() {
        return isHomed_m = cascadeFX_m.isRevLimitSwitchClosed() == 1;
    }

    /**
     * @return True when the ticks are greater than our soft limit
     */
    public boolean cascadeTopLimitCheck() {
        return cascadeFX_m.getSelectedSensorPosition() >= VAL_MAX_TICKS;
    }

    //? Accessors
    /**
     * Safety on means will NOT move
     * @param safety The value to set safety to
     */
    public void setSafety(boolean safety) {
        safety_m = safety;
        SmartDashboard.putBoolean("Safety", safety_m);
        SmartDashboard.setPersistent("Safety");
    }

    public boolean getSafety() {
        return safety_m;
    }

    @Override
    public void periodic() {
    }

    @Override
    public void initSendable(SendableBuilder builder) {
    }

    @Override
    public void addToDriveTab(ShuffleboardTab tab) {
    }
}
