package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.util.MotorConfig;
import frc.robot.util.Subsystem610;

import static frc.robot.Constants.Intake.*;
import static frc.robot.Constants.TronWheel.*;

public class Intake extends Subsystem610 {
    private static Intake intakeInst_s;
    private WPI_TalonSRX intakeSRX_m, kickerSRX_m;
    private static LinearFilter singlePoleIIR;
    private boolean intaking_m;

    private Intake() {
        super("Intake");
        intakeSRX_m = MotorConfig.configIntakeMotor(CAN_INTAKE_SRX);
        kickerSRX_m = MotorConfig.configKickerMotor(CAN_KICKER_SRX);
        singlePoleIIR = LinearFilter.singlePoleIIR(0.5, 0.02);
        intaking_m = false;
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
        spin = RobotContainer.coneMode_s ? spin : -spin;
        intakeSRX_m.set(ControlMode.PercentOutput, spin);
        if(RobotContainer.tronWheelInst_s.getTargetPos() == VAL_ANG_CUBE_GROUND && !RobotContainer.coneMode_s){
            kickerSRX_m.set(ControlMode.PercentOutput, -spin);
        } else if(RobotContainer.tronWheelInst_s.getTargetPos() == VAL_ANG_CONE_GROUND && RobotContainer.coneMode_s){
            kickerSRX_m.set(ControlMode.PercentOutput, -spin);
        }
    }

    /**
     * Stops the hamster wheel from rotating
     */
    public void stopIntake() {
        intakeSRX_m.set(ControlMode.PercentOutput, 0);
        kickerSRX_m.set(ControlMode.PercentOutput, 0);
    }

    /**
     * @return Whether or not there is a game piece in the bumper cutout (if the
     *         break beam is triggered)
     */
    public boolean getHasGamePiece() {
        return !intakeSRX_m.getSensorCollection().isFwdLimitSwitchClosed();
    }

    /**
     * @return The supply current of the intake motor
     */
    public double getSRXSupplyCurrent() {
        return intakeSRX_m.getSupplyCurrent();
    }

    /**
     * Sets current output of intake motor to param
     * 
     * @param current The desired current
     */
    public void setSRXSupplyCurrent(double current) {
        intakeSRX_m.set(ControlMode.Current, current);
    }

    /**
     * Used for the default command T_Intake_In. This boolean being true will cause the intaking to intake in
     * @return The state of the intaking boolean, true = intaking in
     */
    public boolean getIntaking() {
        return intaking_m;
    }

    /**
     * Sets the intaking boolean, which is what causes the intake to intake in
     * Primarily used in RobotContainer where the triggers change this boolean value
     * @param intaking The state to set it to
     */
    public void setIntaking(boolean intaking) {
        intaking_m = intaking;
    }

    public void writeSmartDashboard() {
        SmartDashboard.putString("Intake Command",
                getCurrentCommand() != null ? getCurrentCommand().getName() : "null");
        SmartDashboard.putNumber("Intake Supply Current", getSRXSupplyCurrent());
        SmartDashboard.putNumber("single pole", singlePoleIIR.calculate(getSRXSupplyCurrent()));
        SmartDashboard.putBoolean("Floor Lim", getHasGamePiece());
        SmartDashboard.putBoolean("Rollers Running", getIntaking());
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
}
