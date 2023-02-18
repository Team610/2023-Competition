package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.MotorConfig;
import frc.robot.util.Subsystem610;

import static frc.robot.Constants.*;
import static frc.robot.Constants.TronWheel.*;





// TODO: add new command that rotates tronwheel to enum state based on cascade length

// public void rotateMagic(double degrees){
//     rotateSRX_m.set(TalonSRXControlMode.MotionMagic, degrees*VAL_DEGREES_TO_TICKS);
// }













public class TronWheel extends Subsystem610 {
    private static TronWheel tronWHeelInst_s;
    private WPI_TalonSRX rotateSRX_m;
    // private GenericEntry rotateManual_m;

    private TronWheel() {
        super("TronWheel");
        rotateSRX_m = MotorConfig.configTronRotateMotor(CAN_ROTATE_SRX);
    }

    public static TronWheel getInstance() {
        if (tronWHeelInst_s == null) {
            tronWHeelInst_s = new TronWheel();
        }
        return tronWHeelInst_s;
    }

    /**
     * Sets the percent output of the motor used to rotate the hamster wheel
     * @param spin The desired percentage output for the motor
     */
    public void rotate(double spin) {
        rotateSRX_m.set(ControlMode.PercentOutput, spin);
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
    public boolean isRevLimit() {
        return rotateSRX_m.isRevLimitSwitchClosed() == 1;
    }

    /**
     * @return If the forward limit switch is pressed
     */
    public boolean isFwdLimit() {
        return rotateSRX_m.isFwdLimitSwitchClosed() == 1;
    }

    public void writeSmartDashboard() {
        SmartDashboard.putBoolean("Rotate Mode", getManual());
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
