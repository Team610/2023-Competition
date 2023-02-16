package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.util.MotorConfig;
import frc.robot.util.Subsystem610;

import static frc.robot.Constants.*;
import static frc.robot.Constants.HamsterWheel.*;

public class HamsterWheel extends Subsystem610 {
    private static HamsterWheel hamsterWHeelInst_s;
    private WPI_TalonSRX intakeSRX_m;
    private WPI_TalonSRX spinSRX_m;

    private HamsterWheel() {
        super("HamsterWheel");

        intakeSRX_m = MotorConfig.configHamsterIntakeMotor(CAN_INTAKE_SRX);
        spinSRX_m = MotorConfig.configHamsterIntakeMotor(CAN_SPIN_SRX);
    }

    public static HamsterWheel getInstance() {
        if (hamsterWHeelInst_s == null) {
            hamsterWHeelInst_s = new HamsterWheel();
        }
        return hamsterWHeelInst_s;
    }

    /**
     * Sets the percent output of the motor used to intake game pieces
     * @param spin The desired percentage output for the motor
     */
    public void spinIntake(double spin) {
        intakeSRX_m.set(ControlMode.PercentOutput, spin);
    }

    /**
     * Sets the percent output of the motor used to rotate the hamster wheel
     * @param spin The desired percentage output for the motor
     */
    public void spinWheel(double spin) {
        spinSRX_m.set(ControlMode.PercentOutput, spin);
    }

    /**
     * Stops the hamster wheel from rotating
     */
    public void stopSpin() {
        spinSRX_m.set(ControlMode.PercentOutput, 0);
    }

    /**
     * Sets output percentage of intake motor to param
     * @param spin Output percentage
     */
    public void intake(double spin) {
        intakeSRX_m.set(ControlMode.PercentOutput, spin);
    }

    /**
     * @return If the reverse limit switch is pressed
     */
    public boolean isRevLimit() {
        return intakeSRX_m.isRevLimitSwitchClosed() == 1;
    }

    /**
     * @return If the forward limit switch is pressed
     */
    public boolean isFwdLimit() {
        return intakeSRX_m.isFwdLimitSwitchClosed() == 1;
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
