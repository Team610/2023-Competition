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
import frc.robot.util.Subsystem610;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.WPI_Pigeon2;

public class Drivetrain extends Subsystem610 {
    private static Drivetrain driveInst_s;
    private WPI_TalonFX leftBatman_m, leftRobin_m, rightBatman_m, rightRobin_m;
    private DifferentialDriveOdometry odometry_m;
    private WPI_Pigeon2 pidgey_m;

    private Drivetrain() {
        super("Drivetrain");
        leftBatman_m = MotorConfig.configDriveMotor(CAN_LEFT_BATMAN, false, false);
        leftRobin_m = MotorConfig.configDriveFollower(CAN_LEFT_ROBIN, CAN_LEFT_BATMAN, false, false);
        rightBatman_m = MotorConfig.configDriveMotor(CAN_RIGHT_BATMAN, true, false);
        rightRobin_m = MotorConfig.configDriveFollower(CAN_RIGHT_ROBIN, CAN_RIGHT_BATMAN, true, false);

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
        leftBatman_m.setNeutralMode(NeutralMode.Coast);
        leftRobin_m.setNeutralMode(NeutralMode.Coast);
        rightBatman_m.setNeutralMode(NeutralMode.Coast);
        rightRobin_m.setNeutralMode(NeutralMode.Coast);
    }

    /**
     * Sets all drivetrain motors to brake mode
     */
    public void setBrake() {

        leftBatman_m.setNeutralMode(NeutralMode.Brake);
        leftRobin_m.setNeutralMode(NeutralMode.Brake);
        rightBatman_m.setNeutralMode(NeutralMode.Brake);
        rightRobin_m.setNeutralMode(NeutralMode.Brake);
    }

    /**
     * Sets the left batman to a desired output percentage
     * @param output Desired left side output as a percentage
     */
    public void setLeft(double output) {
        leftBatman_m.set(ControlMode.PercentOutput, output);
    }

    /**
     * Sets the right batman to a desired output percentage
     * 
     * @param output Desired right side output as a percentage
     */
    public void setRight(double output) {
        rightBatman_m.set(ControlMode.PercentOutput, output);
    }

    /**
     * Sets the left batman to a desired output percentage, overloaded
     * with desired control mode
     * 
     * @param mode   Mode of output metric
     * @param output Desired output in percentage
     */
    public void setLeft(ControlMode mode, double output) {
        leftBatman_m.set(mode, output);
    }

    /**
     * Sets the right batman to a desired output percentage, overloaded
     * with desired control mode
     * 
     * @param mode Mode of output metric
     * @param output Desired output in percentage
     */
    public void setRight(ControlMode mode, double output) {
        rightBatman_m.set(mode, output);
    }

    /**
     * Sets the robot to drive based on a voltage number, uses setLeft and setRight methods to do it
     * @param leftVolts the desired voltage for the left motor
     * @param rightVolts the desired voltage for the right motor
     */
    public void tankDriveVolts(double leftVolts, double rightVolts) {
        setLeft(leftVolts / 12.0);
        setRight(rightVolts / 12.0);
    }

    /**
     * @return The number of meters the left batman has travelled
     */
    public double getLeftMeters() {
        return leftBatman_m.getSelectedSensorPosition() / UNIT_TICKS_PER_REV * UNIT_DIST_PER_REV;
    }

    /**
     * @return The number of meters the right batman has travelled
     */
    public double getRightMeters() {
        return rightBatman_m.getSelectedSensorPosition() / UNIT_TICKS_PER_REV * UNIT_DIST_PER_REV;
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
                leftBatman_m.getSelectedSensorVelocity() / UNIT_TICKS_PER_REV * UNIT_DIST_PER_REV * 10,
                rightBatman_m.getSelectedSensorVelocity() / UNIT_TICKS_PER_REV * UNIT_DIST_PER_REV * 10);
    }

    /**
     * Resets the drivetrain motor sensors to a value of 0
     */
    public void resetSensors() {
        leftBatman_m.setSelectedSensorPosition(0);
        rightBatman_m.setSelectedSensorPosition(0);
    }

    /**
     * Resets the odometry of the robot
     * @param pose The pose to reset the odometry to
     */
    public void resetOdometry(Pose2d pose) {
        resetSensors();
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
