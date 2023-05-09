package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.MotorConfig;
import frc.robot.util.Subsystem610;

import static frc.robot.Constants.*;
import static frc.robot.Constants.Cascade.*;

public class Cascade extends Subsystem610 {
    private static Cascade cascadeInst_s;
    private WPI_TalonFX cascadeFX_m;
    private boolean safety_m;
    private double targetPos_m;

    private Cascade() {
        super("Cascade");
        safety_m = true;
        targetPos_m = 0;
        cascadeFX_m = MotorConfig.configCascadeMotor(CAN_CASCADE, false, true);
    }

    public static Cascade getInstance() {
        if (cascadeInst_s == null) {
            cascadeInst_s = new Cascade();
        }
        return cascadeInst_s;
    }

    /**
     * Spins the Cascade motor, in terms of percentage.
     * @param spin The desired percent output
     */
    public void spin(double spin) {
        cascadeFX_m.set(ControlMode.PercentOutput, spin);
    }

    /**
     * Spin the cascade motor using Motion Magic with different PID slots based on direction to move
     * @param down True if cascade is retracting
     */
    public void spinMagic(boolean down) {
        if (down) {
            cascadeFX_m.configMotionAcceleration(VAL_MAX_ACCEL_DOWN, VAL_CONFIG_TIMEOUT);
            cascadeFX_m.configMotionCruiseVelocity(VAL_CRUISE_VELO_DOWN, VAL_CONFIG_TIMEOUT);
        } else {
            cascadeFX_m.configMotionAcceleration(VAL_MAX_ACCEL_UP, VAL_CONFIG_TIMEOUT);
            cascadeFX_m.configMotionCruiseVelocity(VAL_CRUISE_VELO_UP, VAL_CONFIG_TIMEOUT);
        }
        cascadeFX_m.set(ControlMode.MotionMagic, targetPos_m);
    }

    /**
     * Sets the cascade motor output to 0
     */
    public void stop() {
        cascadeFX_m.set(ControlMode.PercentOutput, 0);
    }

    /**
     * @return True only when the bottom limit switch is pressed
     */
    public boolean cascadeBotLimitCheck() {
        return cascadeFX_m.isRevLimitSwitchClosed() == 1;
    }

    /**
     * @return True only when the ticks are greater than our soft limit
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
        SmartDashboard.putBoolean("Cascade Safety", safety_m);
        SmartDashboard.setPersistent("Cascade Safety");
    }

    /**
     * @return True only when the safety for the cascade is on
     */
    public boolean getSafety() {
        return safety_m;
    }

    /**
     * @return The target position in ticks the cascade is moving to
     */
    public double getTargetPos() {
        return targetPos_m;
    }

    /**
     * Setting the target position for the cascade to move to
     * @param targetPos The target position in ticks
     */
    public void setTargetPos(double targetPos) {
        targetPos_m = targetPos;
    }

    /**
     * @return The current tick count of the cascade TalonFX
     */
    public double getTicks() {
        return cascadeFX_m.getSelectedSensorPosition();
    }

    /**
     * Manually specifying the current tick count of the cascade TalonFX
     * @param ticks The tick count to set
     */
    public void setTicks(double ticks) {
        cascadeFX_m.setSelectedSensorPosition(ticks);
    }

    /**
     * Converts ticks of cascade encoder to inches travelled
     * @param ticks: encoder ticks of distance travelled
     * @return inches of distance travelled
     */
    public double ticksToIn(double ticks) {
        return ticks * UNIT_TICKS_TO_INCHES;
    }

    /**
     * Converts inches of cascade travel to encoder ticks
     * @param in: inches of cascade travelled
     * @return encoder ticks travelled
     */
    public double inToTicks(double in) {
        return in * UNIT_INCHES_TO_TICKS;
    }

    /**
     * Converts ticks of cascade into percent travelled
     * @return The percent the cascade has travelled from the bottom
     */
    public double cascadeTickPercent() {
        return (cascadeFX_m.getSelectedSensorPosition() / VAL_CONVERT_TICKS) / 100;
    }

    private void writeSmartDashboard() {
        SmartDashboard.putString("Cascade Command",
                getCurrentCommand() != null ? getCurrentCommand().getName() : "null");
        SmartDashboard.putNumber("Cascade Preset", targetPos_m);
        SmartDashboard.putBoolean("Cascade Manual Mode", getManual());
        SmartDashboard.putNumber("Cascade Supply Current", cascadeFX_m.getSupplyCurrent());
    }

    @Override
    public void periodic() {
        writeSmartDashboard();
    }

    @Override
    public void initSendable(SendableBuilder builder) {
    }

    @Override
    public void addToDriveTab(ShuffleboardTab tab) {
    }
}
