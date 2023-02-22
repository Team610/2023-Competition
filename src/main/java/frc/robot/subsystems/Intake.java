package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.MotorConfig;
import frc.robot.util.Subsystem610;

import static frc.robot.Constants.*;
import static frc.robot.Constants.Intake.*;

public class Intake extends Subsystem610 {
    private static Intake intakeInst_s;
    private WPI_TalonSRX intakeSRX_m;
    private static LinearFilter singlePoleIIR;
    private static LinearFilter movingAverage;
    private static LinearFilter highPass;
    private static LinearFilter finiteDifference;

    private Intake() {
        super("Intake");
        intakeSRX_m = MotorConfig.configIntakeMotor(CAN_INTAKE_SRX);
        singlePoleIIR = LinearFilter.singlePoleIIR(0.5, 0.02);
        movingAverage = LinearFilter.movingAverage(50);
        highPass = LinearFilter.highPass(0.5, 0.02);
        int[] stencil = {0, -1, -2, -3, -4};
        finiteDifference = LinearFilter.finiteDifference(1, stencil, 0.02);
    }

    public static Intake getInstance() {
        if (intakeInst_s == null) {
            intakeInst_s = new Intake();
        }
        return intakeInst_s;
    }

    /**
     * Sets output percentage of intake motor to param
     * 
     * @param spin Output percentage
     */
    public void intake(double spin) {
        intakeSRX_m.set(ControlMode.PercentOutput, spin);
    }

    /**
     * Stops the hamster wheel from rotating
     */
    public void stopIntake() {
        intakeSRX_m.set(ControlMode.PercentOutput, 0);
    }

    public boolean getHasGamePiece() {
        return intakeSRX_m.getSensorCollection().isRevLimitSwitchClosed();
    }

    public void writeSmartDashboard() {
        SmartDashboard.putString("Intake Command",
                getCurrentCommand() != null ? getCurrentCommand().getName() : "null");
        SmartDashboard.putNumber("Intake Supply Current", getSRXSupplyCurrent());
        SmartDashboard.putNumber("single pole", singlePoleIIR.calculate(getSRXSupplyCurrent()));
        SmartDashboard.putNumber("highpass", highPass.calculate(getSRXSupplyCurrent()));
        SmartDashboard.putNumber("movingaverage", movingAverage.calculate(getSRXSupplyCurrent()));
        SmartDashboard.putNumber("backwardfinitedifference", finiteDifference.calculate(getSRXSupplyCurrent()));

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
        // TODO Auto-generated method stub
    }

    public double getSRXSupplyCurrent() {
        return intakeSRX_m.getSupplyCurrent();
    }
}
