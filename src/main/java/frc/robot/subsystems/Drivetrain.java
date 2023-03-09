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

import com.ctre.phoenix.sensors.WPI_Pigeon2;
import com.ctre.phoenixpro.controls.CoastOut;
import com.ctre.phoenixpro.controls.DutyCycleOut;
import com.ctre.phoenixpro.controls.StaticBrake;
import com.ctre.phoenixpro.hardware.TalonFX;

public class Drivetrain extends Subsystem610 {
    private static Drivetrain driveInst_s;

    private TalonFX leftBatman_m;
    private TalonFX rightBatman_m;
    private TalonFX leftRobin_m;
    private TalonFX rightRobin_m;

    private DutyCycleOut leftDutyCycle_m, rightDutyCycle_m;

    private DifferentialDriveOdometry odometry_m;
    private WPI_Pigeon2 pidgey_m;

    private Drivetrain() {
        super("Drivetrain");

        leftBatman_m = MotorConfig.configDrivePro(CAN_LEFT_BATMAN, true);
        rightBatman_m = MotorConfig.configDrivePro(CAN_RIGHT_BATMAN, false);
        leftRobin_m = MotorConfig.configFollowPro(CAN_LEFT_ROBIN, leftBatman_m);
        rightRobin_m = MotorConfig.configFollowPro(CAN_RIGHT_ROBIN, rightBatman_m);

        leftDutyCycle_m = new DutyCycleOut(0, true, false);
        rightDutyCycle_m = new DutyCycleOut(0, true, false);

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
        leftBatman_m.setControl(new CoastOut());
        leftRobin_m.setControl(new CoastOut());
        rightBatman_m.setControl(new CoastOut());
        rightRobin_m.setControl(new CoastOut());
    }

    /**
     * Sets all drivetrain motors to brake mode
     */
    public void setBrake() {
        leftBatman_m.setControl(new StaticBrake());
        leftRobin_m.setControl(new StaticBrake());
        rightBatman_m.setControl(new StaticBrake());
        rightRobin_m.setControl(new StaticBrake());
    }

    /**
     * Sets the left batman to a desired output percentage
     * 
     * @param output Desired left side output as a percentage
     */
    public void setProLeft(double speed) {
        leftDutyCycle_m.Output = speed;
        leftBatman_m.setControl(leftDutyCycle_m);
    }

    /**
     * Sets the right batman to a desired output percentage
     * 
     * @param output Desired right side output as a percentage
     */
    public void setProRight(double speed) {
        rightDutyCycle_m.Output = speed;
        rightBatman_m.setControl(rightDutyCycle_m);
    }

    /**
     * Sets the robot to drive based on a voltage number, uses setLeft and setRight
     * methods to do it
     * 
     * @param leftVolts  the desired voltage for the left motor
     * @param rightVolts the desired voltage for the right motor
     */
    public void tankDriveVolts(double leftVolts, double rightVolts) {
        setProLeft(leftVolts / 12.0);
        setProRight(rightVolts / 12.0);
    }

    /**
     * @return The number of meters the left batman has travelled
     */
    public double getLeftMeters() {
        leftBatman_m.getPosition().refresh();
        return leftBatman_m.getPosition().getValue() / VAL_GEAR_RATIO * VAL_WHEEL_DIA * Math.PI;
    }

    /**
     * @return The number of meters the right batman has travelled
     */
    public double getRightMeters() {
        rightBatman_m.getRotorPosition().refresh();
        return rightBatman_m.getRotorPosition().getValue() / VAL_GEAR_RATIO * VAL_WHEEL_DIA * Math.PI;
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
                leftBatman_m.getRotorVelocity().getValue() / VAL_GEAR_RATIO * VAL_WHEEL_DIA * Math.PI,
                rightBatman_m.getRotorVelocity().getValue() / VAL_GEAR_RATIO * VAL_WHEEL_DIA * Math.PI);
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
