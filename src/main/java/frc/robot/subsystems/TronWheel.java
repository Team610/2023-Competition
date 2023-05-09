package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.MotorConfig;
import frc.robot.util.Subsystem610;

import static frc.robot.Constants.TronWheel.*;

public class TronWheel extends Subsystem610 {
    private static TronWheel tronWheelInst_s;
    private WPI_TalonSRX rotateSRX_m;
    private boolean safety_m;
    private double targetPos_m;

    private TronWheel() {
        super("TronWheel");
        safety_m = true;
        targetPos_m = VAL_ANG_CONE_TRANSPORT;
        rotateSRX_m = MotorConfig.configTronRotateMotor(CAN_ROTATE_SRX);
    }

    public static TronWheel getInstance() {
        if (tronWheelInst_s == null) {
            tronWheelInst_s = new TronWheel();
        }
        return tronWheelInst_s;
    }

    /**
     * Sets the percent output of the motor used to rotate the hamster wheel
     * @param spin The desired percentage output for the motor
     */
    public void rotate(double spin) {
        rotateSRX_m.set(ControlMode.PercentOutput, spin);
    }

     /**
     * Uses MotionMagic to rotate the tron wheel to a set position
     * @param ticks The position to rotate the tron wheel to
     */
    public void rotateMagic() {
        rotateSRX_m.set(ControlMode.MotionMagic, targetPos_m);
    }

    /**
     * Stops the tron wheel from rotating
     */
    public void stopRotate() {
        rotateSRX_m.set(ControlMode.PercentOutput, 0);
    }

    /**
     * @return True only when the reverse limit switch is pressed
     */
    public boolean tronRevLimit() {
        return rotateSRX_m.isRevLimitSwitchClosed() == 1;
    }

    /**
     * @return True only when the forward limit switch is pressed
     */
    public boolean tronFwdLimit() {
        return rotateSRX_m.isFwdLimitSwitchClosed() == 1;
    }

    /**
     * Safety on means will NOT move
     * @param safety The value to set safety to
     */
    public void setSafety(boolean safety) {
        safety_m = safety;
        SmartDashboard.putBoolean("Tron Safety", safety_m);
        SmartDashboard.setPersistent("Tron Safety");
    }

    /**
     * @return True only when the tron wheel safety is on
     */
    public boolean getSafety() {
        return safety_m;
    }
    
    /**
     * @return The target position the tron wheel is rotating to
     */
    public double getTargetPos() {
        return targetPos_m;
    }

    /**
     * @param targetPos The target for the tron wheel to rotate to
     */
    public void setTargetPos(double targetPos) {
        targetPos_m = targetPos;
    }

    /**
     * Manually specifying the current tick count of the tron wheel encoder
     * @param ticks The tick count to set
     */
    public void setTicks(double ticks) {
        rotateSRX_m.setSelectedSensorPosition(ticks);
    }

    /**
     * A helper method to see if the Tron Wheel is approximately at the position it's set to go to
     * @return True only when it is within 100 ticks of the target position
     */
    public boolean checkClosedLoop(){
        return Math.abs(rotateSRX_m.getClosedLoopError()) < 100;
    }

    public void writeSmartDashboard() {
        SmartDashboard.putString("Tron Command", getCurrentCommand() != null ? getCurrentCommand().getName() : "null");
        SmartDashboard.putNumber("Tron Preset", targetPos_m);
        SmartDashboard.putNumber("Tron Current Tick", rotateSRX_m.getSelectedSensorPosition());
        SmartDashboard.putBoolean("Tron Manual Mode", getManual());
    }

    @Override
    public void periodic() {
        writeSmartDashboard();
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        // TODO Auto-generated method stub
    }

    @Override
    public void addToDriveTab(ShuffleboardTab tab) {
    }
}
