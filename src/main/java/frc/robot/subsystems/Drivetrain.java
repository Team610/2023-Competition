package frc.robot.subsystems;

import java.lang.Math;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import static frc.robot.Constants.Drivetrain.*;
import static frc.robot.Constants.Simulation.*;
import static frc.robot.Constants.StationPID.*;


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
    private PIDController pidBal_m;
    private PIDController pidTilt_m;

    private static Deque<Double> dq_s;
    private static double prevErr;
    private static double curErr;
    
    TalonFXSimCollection leftBatmanSim_m;
    TalonFXSimCollection rightBatmanSim_m;
    BasePigeonSimCollection pidgeySim_m;
    DifferentialDrivetrainSim driveSim_m;
    Field2d field_m = new Field2d();

    private Drivetrain() {
        super("Drivetrain");
        leftBatman_m = MotorConfig.configDriveMotor(CAN_LEFT_BATMAN, false, false);
        leftRobin_m = MotorConfig.configDriveFollower(CAN_LEFT_ROBIN, CAN_LEFT_BATMAN, false, false);
        rightBatman_m = MotorConfig.configDriveMotor(CAN_RIGHT_BATMAN, true, false);
        rightRobin_m = MotorConfig.configDriveFollower(CAN_RIGHT_ROBIN, CAN_RIGHT_BATMAN, true, false);

        dq_s = new ArrayDeque<>();

        leftBatmanSim_m = leftBatman_m.getSimCollection();
        rightBatmanSim_m = rightBatman_m.getSimCollection();

        pidgey_m = new WPI_Pigeon2(CAN_PIDGEY);
        pidgeySim_m = pidgey_m.getSimCollection();
        //feedforward_m = new SimpleMotorFeedforward(VAL_KS, VAL_KV, VAL_KA);

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
        // kPPID_s = Math.sin(Math.toRadians(pidgey_m.getPitch()))*0.65;
        //System.out.print(kPPID_s);
        // SmartDashboard.putNumber("kP", kPPID_s);
        pidBal_m = new PIDController(VAL_KP_BAL_PID, VAL_KI_BAL_PID, VAL_KD_BAL_PID);
        pidTilt_m = new PIDController(VAL_KP_TILT_PID, VAL_KI_TILT_PID, VAL_KD_TILT_PID);
        SmartDashboard.putNumber("P", pidBal_m.getP());
        SmartDashboard.putNumber("I", pidBal_m.getI());
        SmartDashboard.putNumber("D", pidBal_m.getD());
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
     * Get pitch on gyro
     */
    public double getPitch(){
        return pidgey_m.getPitch();
    }

    /**
     * Get roll on gyro
     */
    public double getRoll(){
        return pidgey_m.getRoll();
    }

    /**
     * Get yaw on gyro
     */
    public double getYaw(){
        return pidgey_m.getYaw();
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

    /**
     * Method to adjust the speed and direction of the motor based on the speed of the charging station (Uses PID)
    */

    public void adjustPIDStation(){
        SmartDashboard.putNumber("Motor Speed", pidBal_m.calculate(pidgey_m.getPitch(),0));   
        pidBal_m.setTolerance(VAL_BAL_TOLERANCE,0);
        driveInst_s.setLeft(pidBal_m.calculate(pidgey_m.getPitch(),0));
        driveInst_s.setRight(pidBal_m.calculate(pidgey_m.getPitch(),0));
    }

    /**
     * method to drive to charging station and tilted it until optimal degrees
    */

    public void toTilt(){
        pidTilt_m.setTolerance(VAL_TILT_TOLERANCE, 0);

        SmartDashboard.putNumber("Current Pitch", pidgey_m.getPitch());
        SmartDashboard.putNumber("Error", pidTilt_m.getPositionError());
        SmartDashboard.putNumber("Motor Velocity", -1*pidTilt_m.calculate(pidgey_m.getPitch(),-11));

        driveInst_s.setLeft(-1*pidTilt_m.calculate(pidgey_m.getPitch(),-11));
        driveInst_s.setRight(-1*pidTilt_m.calculate(pidgey_m.getPitch(),-11));
        dq_s.addFirst(pidTilt_m.getPositionError());
        if(dq_s.size()>=7){
            dq_s.removeLast();
        }
    }

    public boolean testTilt(){
        Iterator<Double> it = dq_s.iterator();
        curErr = dq_s.removeFirst();
        
        dq_s.addFirst(prevErr);
        while (it.hasNext()) {
            prevErr = curErr;
            curErr = it.next();
            if(Math.abs(curErr-prevErr)>=VAL_TEST_TILT_TOLERANCE){
                return false;
            }
        }
        return true;
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

class DrivetrainIOInputs{
    public double distanceTraveled = 0;
    public double speed = 0;
    public double pitch = 0;
    public double yaw;
}


