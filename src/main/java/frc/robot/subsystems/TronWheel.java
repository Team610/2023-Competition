package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.MotorConfig;
import frc.robot.util.Subsystem610;

import static frc.robot.Constants.*;
import static frc.robot.Constants.TronWheel.*;

public class TronWheel extends Subsystem610 {
    private static TronWheel tronWheelInst_s;
    private WPI_TalonSRX rotateSRX_m;
    // private GenericEntry rotateManual_m;
    private boolean isHomed_m;
    private boolean safety_m;
    private double targetPos_m;

    private TronWheel() {
        super("TronWheel");
        isHomed_m = false;
        safety_m = true;
        targetPos_m = 0;
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
     * Uses MotionMagic to spin the motor to a set position
     * @param ticks The position to spin the motor to
     */
    public void rotateMagic() {
        rotateSRX_m.set(ControlMode.MotionMagic, targetPos_m);
    }

    /**
     * Stops the hamster wheel from spinning
     */
    public void stopRotate() {
        rotateSRX_m.set(ControlMode.PercentOutput, 0);
    }

    /**
     * @return If the reverse limit switch is pressed
     */
    public boolean tronRevLimit() {
        return isHomed_m = rotateSRX_m.isRevLimitSwitchClosed() == 1;
    }

    /**
     * @return If the forward limit switch is pressed
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

    public boolean getSafety() {
        return safety_m;
    }
    
    public double getTargetPos() {
        return targetPos_m;
    }

    public void setTargetPos(double targetPos) {
        targetPos_m = targetPos;
    }

    public boolean checkClosedLoop(){
        return Math.abs(rotateSRX_m.getClosedLoopError()) < 100;
    }

    public void writeSmartDashboard() {
        SmartDashboard.putString("Tron Command", getCurrentCommand() != null ? getCurrentCommand().getName() : "null");
        SmartDashboard.putNumber("Tron Preset", targetPos_m);
        SmartDashboard.putBoolean("Tron Manual Mode", getManual());
    }

    @Override
    public void periodic() {
        writeSmartDashboard();
        // rotateManual_m.setBoolean(getManual());
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        // TODO Auto-generated method stub
    }

    @Override
    public void addToDriveTab(ShuffleboardTab tab) {
        // rotateManual_m = tab.add("Rotate M", getManual())
        //     .withPosition(10, 0)
        //     .withSize(1, 1)
        //     .getEntry();
    }
}
