package frc.robot.subsystems;

import static frc.robot.Constants.*;
import static frc.robot.Constants.Drivetrain.*;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.util.MotorConfig;
// import frc.robot.util.MotorConfig;
import frc.robot.util.Subsystem610;

import com.ctre.phoenix.sensors.WPI_Pigeon2;
import com.ctre.phoenixpro.configs.MotorOutputConfigs;
import com.ctre.phoenixpro.controls.DutyCycleOut;
import com.ctre.phoenixpro.controls.Follower;
import com.ctre.phoenixpro.hardware.TalonFX;
import com.ctre.phoenixpro.signals.InvertedValue;

public class Drivetrain extends Subsystem610 {
    private static Drivetrain driveInst_s;

    public TalonFX leftBatman_m;
    public TalonFX rightBatman_m;
    public TalonFX leftRobin_m;
    public TalonFX rightRobin_m;

    private DifferentialDriveOdometry odometry_m;
    private WPI_Pigeon2 pidgey_m;

    private Drivetrain() {
        super("Drivetrain");

        leftBatman_m = MotorConfig.configDrivePro(CAN_LEFT_BATMAN, true);
        rightBatman_m = MotorConfig.configDrivePro(CAN_RIGHT_BATMAN, false);
        // var currentConfigs = new MotorOutputConfigs();

        // // The left motor is CCW+
        // currentConfigs.Inverted = InvertedValue.CounterClockwise_Positive;
        // leftBatman_m.getConfigurator().apply(currentConfigs);

        // // The right motor is CW+
        // currentConfigs.Inverted = InvertedValue.Clockwise_Positive;
        // rightBatman_m.getConfigurator().apply(currentConfigs);

        // Ensure our followers are following their respective leader
        leftRobin_m = MotorConfig.configFollowPro(CAN_LEFT_ROBIN, leftBatman_m);
        rightRobin_m = MotorConfig.configFollowPro(CAN_RIGHT_ROBIN, rightBatman_m);
        // rightRobin_m.setControl(new Follower(rightBatman_m.getDeviceID(), false));

        pidgey_m = new WPI_Pigeon2(CAN_PIDGEY, CAN_BUS_NAME);

        odometry_m = new DifferentialDriveOdometry(Rotation2d.fromDegrees(0), getLeftMeters(), getRightMeters());
    }

    public static Drivetrain getInstance() {
        if (driveInst_s == null) {
            driveInst_s = new Drivetrain();
        }
        return driveInst_s;
    }

    /**
     * Sets all drivetrain motors to coast mode
     */
    public void setCoast() {
        // leftBatman_m.setNeutralMode(NeutralMode.Coast);
        // leftRobin_m.setNeutralMode(NeutralMode.Coast);
        // rightBatman_m.setNeutralMode(NeutralMode.Coast);
        // rightRobin_m.setNeutralMode(NeutralMode.Coast);
    }

    /**
     * Sets all drivetrain motors to brake mode
     */
    public void setBrake() {
        // leftBatman_m.setNeutralMode(NeutralMode.Brake);
        // leftRobin_m.setNeutralMode(NeutralMode.Brake);
        // rightBatman_m.setNeutralMode(NeutralMode.Brake);
        // rightRobin_m.setNeutralMode(NeutralMode.Brake);
    }

    /**
     * Sets the left batman to a desired output percentage
     * 
     * @param output Desired left side output as a percentage
     */
    public void setProLeft(DutyCycleOut output) {
        leftBatman_m.setControl(output);
    }

    /**
     * Sets the right batman to a desired output percentage
     * 
     * @param output Desired right side output as a percentage
     */
    public void setProRight(DutyCycleOut output) {
        rightBatman_m.setControl(output);
    }

    /**
     * Sets the robot to drive based on a voltage number, uses setLeft and setRight
     * methods to do it
     * 
     * @param leftVolts  the desired voltage for the left motor
     * @param rightVolts the desired voltage for the right motor
     */
    public void tankDriveVolts(double leftVolts, double rightVolts) {
        DutyCycleOut left = new DutyCycleOut(leftVolts / 12.0);
        DutyCycleOut right = new DutyCycleOut(rightVolts / 12.0);
        setProLeft(left);
        setProRight(right);
    }

    /**
     * @return The number of meters the left batman has travelled
     */
    public double getLeftMeters() {
        leftBatman_m.getPosition().refresh();
        return leftBatman_m.getPosition().getValue();
    }

    /**
     * @return The number of meters the right batman has travelled
     */
    public double getRightMeters() {
        rightBatman_m.getPosition().refresh();
        return rightBatman_m.getPosition().getValue();
    }

    /**
     * @return The position of the robot on the field
     */
    public Pose2d getPose() {
        return odometry_m.getPoseMeters();
    }

    /**
     * @return The number of meters the robot has travelled
     */
    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(
                leftBatman_m.getRotorVelocity().getValue() / UNIT_DIST_PER_REV,
                rightBatman_m.getRotorVelocity().getValue() / UNIT_DIST_PER_REV);
    }

    /**
     * Resets the drivetrain motor sensors to a value of 0
     */
    public void resetSensors() {
        leftBatman_m.setRotorPosition(0);
        rightBatman_m.setRotorPosition(0);
    }

    /**
     * Resets the odometry of the robot
     * 
     * @param pose The pose to reset the odometry to
     */
    public void resetOdometry(Pose2d pose) {
        odometry_m.resetPosition(pidgey_m.getRotation2d(), getLeftMeters(), getRightMeters(), pose);
    }

    @Override
    public void periodic() {
        odometry_m.update(pidgey_m.getRotation2d(), getLeftMeters(), getRightMeters());
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
