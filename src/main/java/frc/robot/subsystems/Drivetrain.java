package frc.robot.subsystems;

import static frc.robot.Constants.*;
import static frc.robot.Constants.Drivetrain.*;
import static frc.robot.Constants.Simulation.*;
import edu.wpi.first.math.controller.PIDController;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.MotorConfig;
import frc.robot.util.Subsystem610;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXSimCollection;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.BasePigeonSimCollection;
import com.ctre.phoenix.sensors.WPI_Pigeon2;



public class Drivetrain extends Subsystem610 {
    private static Drivetrain driveInst_s;
    private WPI_TalonFX leftBatman_m, leftRobin_m, rightBatman_m, rightRobin_m;
    private DifferentialDriveOdometry odometry_m;
    private WPI_Pigeon2 pidgey_m;
    
    TalonFXSimCollection leftBatmanSim_m;
    TalonFXSimCollection rightBatmanSim_m;
    BasePigeonSimCollection pidgeySim_m;
    DifferentialDrivetrainSim driveSim_m;

    Field2d field_m = new Field2d();

    private PIDController pid_m;

    private Drivetrain() {
        super("Drivetrain");
        leftBatman_m = MotorConfig.configDriveMotor(CAN_LEFT_BATMAN, false, false);
        leftRobin_m = MotorConfig.configDriveFollower(CAN_LEFT_ROBIN, CAN_LEFT_BATMAN, false, false);
        rightBatman_m = MotorConfig.configDriveMotor(CAN_RIGHT_BATMAN, true, false);
        rightRobin_m = MotorConfig.configDriveFollower(CAN_RIGHT_ROBIN, CAN_RIGHT_BATMAN, true, false);

        leftBatmanSim_m = leftBatman_m.getSimCollection();
        rightBatmanSim_m = rightBatman_m.getSimCollection();

        pidgey_m = new WPI_Pigeon2(CAN_PIDGEY, CAN_BUS_NAME);
        pidgeySim_m = pidgey_m.getSimCollection();
        

        driveSim_m = new DifferentialDrivetrainSim(
            DCMotor.getFalcon500(2),  //2 Falcon 500s on each side of the drivetrain.
            VAL_GEARING,               //Standard AndyMark Gearing reduction.
            VAL_INERTIA,                      //MOI of 2.1 kg m^2 (from CAD model).
            VAL_MASS,                     // Mass of the robot.
            VAL_WHEEL_RAD,  // wheel radius in metres.
            VAL_TRACK_WIDTH,                    // distance between wheels is.
            /*
            * The standard deviations for measurement noise:
            * x and y:          0.001 m
            * heading:          0.001 rad
            * l and r velocity: 0.1   m/s
            * l and r position: 0.005 m
            */
            null //VecBuilder.fill(0.001, 0.001, 0.001, 0.1, 0.1, 0.005, 0.005) //Uncomment this line to add measurement noise.
        );
        
        field_m = new Field2d();
        SmartDashboard.putData("Field", field_m);

        pid_m  = new PIDController(VAL_KP, VAL_KI, VAL_KD);


        odometry_m = new DifferentialDriveOdometry(Rotation2d.fromDegrees(0), getLeftMeters(), getRightMeters());
    }

    @Override
    public void periodic(){
        // odometry_m.update(pidgey_m.getRotation2d(), getLeftMeters(), getRightMeters());
        odometry_m.update(pidgey_m.getRotation2d(),
                      nativeUnitsToDistanceMeters(leftBatman_m.getSelectedSensorPosition()),
                      nativeUnitsToDistanceMeters(rightBatman_m.getSelectedSensorPosition()));
        field_m.setRobotPose(odometry_m.getPoseMeters());
    }

    public void simulationPeriodic() {
        /* Pass the robot battery voltage to the simulated Talon FXs */
        leftBatmanSim_m.setBusVoltage(RobotController.getBatteryVoltage());
        rightBatmanSim_m.setBusVoltage(RobotController.getBatteryVoltage());
    

        driveSim_m.setInputs(leftBatmanSim_m.getMotorOutputLeadVoltage(),
                             -rightBatmanSim_m.getMotorOutputLeadVoltage());
    
        driveSim_m.update(0.02);

    
        leftBatmanSim_m.setIntegratedSensorRawPosition(
                        distanceToNativeUnits(
                            driveSim_m.getLeftPositionMeters()
                        ));
        leftBatmanSim_m.setIntegratedSensorVelocity(
                        velocityToNativeUnits(
                            driveSim_m.getLeftVelocityMetersPerSecond()
                        ));
        rightBatmanSim_m.setIntegratedSensorRawPosition(
                        distanceToNativeUnits(
                            -driveSim_m.getRightPositionMeters()
                        ));
        rightBatmanSim_m.setIntegratedSensorVelocity(
                        velocityToNativeUnits(
                            -driveSim_m.getRightVelocityMetersPerSecond()
                        ));
        pidgeySim_m.setRawHeading(driveSim_m.getHeading().getDegrees());
      }

    public static Drivetrain getInstance() {
        if (driveInst_s == null) {
            driveInst_s = new Drivetrain();
        }
        return driveInst_s;
    }

     /**
     * Calibrate Pigeon 2.0 
     */
    public void calibratePidgey(){
        pidgey_m.calibrate();
    }

    /**
     * Get gravity vector
     */
    public double getRoll(){
        return pidgey_m.getRoll();
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

    /**
     * @return The position of the robot on the field
     */
    public Pose2d getPose() {
        return odometry_m.getPoseMeters();
    }

    public void adjustPID(){
        pid_m.setTolerance(1.4, 0);
        driveInst_s.setLeft(-pid_m.calculate(pidgey_m.getYaw(), 0));
        driveInst_s.setRight(pid_m.calculate(pidgey_m.getYaw(), 0));
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        // TODO Auto-generated method stub
    }

    @Override
    public void addToDriveTab(ShuffleboardTab tab) {
        // TODO Auto-generated method stub
    }

    private int distanceToNativeUnits(double positionMeters){
        double wheelRotations = positionMeters/(2 * Math.PI * 0.076);
        double motorRotations = wheelRotations * 1;
        int sensorCounts = (int)(motorRotations * 2048);
        return sensorCounts;
    }

    private int velocityToNativeUnits(double velocityMetersPerSecond){
        double wheelRotationsPerSecond = velocityMetersPerSecond/(2 * Math.PI *0.076);
        double motorRotationsPerSecond = wheelRotationsPerSecond * 1;
        double motorRotationsPer100ms = motorRotationsPerSecond / 10;
        int sensorCountsPer100ms = (int)(motorRotationsPer100ms * 2048);
        return sensorCountsPer100ms;
    }

    private double nativeUnitsToDistanceMeters(double sensorCounts){
        double motorRotations = (double)sensorCounts / 2048;
        double wheelRotations = motorRotations / 1;
        double positionMeters = wheelRotations * (2 * Math.PI * 0.076);
        return positionMeters;
    }
}
