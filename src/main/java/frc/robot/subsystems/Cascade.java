package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.MotorConfig;
import frc.robot.util.Subsystem610;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXSimCollection;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.BasePigeonSimCollection;
import com.ctre.phoenix.sensors.WPI_Pigeon2;

import static frc.robot.Constants.Cascade.*;

public class Cascade extends Subsystem610 {
    private static final int kMotorPort = 0;
    private static final int kEncoderAChannel = 0;
    private static final int kEncoderBChannel = 1;
    private static final int kJoystickPort = 0;

    private static final double kElevatorKp = 5;
    private static final double kElevatorKi = 0;
    private static final double kElevatorKd = 0;

    private static final double kElevatorkS = 0.0; // volts (V)
    private static final double kElevatorkG = 0.762; // volts (V)
    private static final double kElevatorkV = 0.762; // volt per velocity (V/(m/s))
    private static final double kElevatorkA = 0.0; // volt per acceleration (V/(m/s²))

    private static final double kElevatorGearing = 10.0;
    private static final double kElevatorDrumRadius = 0.0508;
    private static final double kCarriageMass = 4.8; // kg

    private static final double kSetpoint = 0.762;
    private static final double kMinElevatorHeight = 0.0508;
    private static final double kMaxElevatorHeight = 1.27;

    private static final double kElevatorEncoderDistPerPulse = 2.0 * Math.PI * kElevatorDrumRadius / 4096;

    private static Cascade cascadeInst_s;
    private WPI_TalonFX cascadeFX_m;
    TalonFXSimCollection cascadeFXSim_m;
    ElevatorSim elevatorSim_m;
    private Encoder m_encoder;

    private final EncoderSim m_encoderSim = new EncoderSim(m_encoder);
    private final Mechanism2d m_mech2d = new Mechanism2d(20, 50);
    private final MechanismRoot2d m_mech2dRoot = m_mech2d.getRoot("Elevator Root", 10, 0);
    private final MechanismLigament2d m_elevatorMech2d = m_mech2dRoot.append(new MechanismLigament2d("Elevator", elevatorSim_m.getPositionMeters() * 0.0254, 90));

    private Cascade() {
        super("Cascade");
        cascadeFX_m = MotorConfig.configCascadeMotor(CAN_CASCADE);
        System.out.println("created cascadeFX");
        cascadeFXSim_m = cascadeFX_m.getSimCollection();

        elevatorSim_m = new ElevatorSim(
                DCMotor.getFalcon500(1),
                kElevatorGearing,
                kCarriageMass,
                kElevatorDrumRadius,
                kMinElevatorHeight,
                kMaxElevatorHeight,
                true,
                null); // VecBuilder.fill(0.01));
    }

    public static Cascade getInstance() {
        if (cascadeInst_s == null) {
            cascadeInst_s = new Cascade();
        }
        return cascadeInst_s;
    }

    public void simulationPeriodic() {
        /* Pass the robot battery voltage to the simulated Talon FXs */
        cascadeFXSim_m.setBusVoltage(RobotController.getBatteryVoltage());

        elevatorSim_m.setInput(cascadeFXSim_m.getMotorOutputLeadVoltage());

        elevatorSim_m.update(0.02);

        m_encoderSim.setDistance(elevatorSim_m.getPositionMeters());

        m_elevatorMech2d.setLength(elevatorSim_m.getPositionMeters() * 0.0254);
    }

    /**
     * Spins the Cascade motor, in terms of percentage.
     * 
     * @param spin The desired speed percentage
     *
     */
    public void spin(double spin) {
        cascadeFX_m.set(ControlMode.PercentOutput, spin);
    }

    /**
     * Sets the cascade motor output to 0
     */
    public void stop() {
        cascadeFX_m.set(ControlMode.PercentOutput, 0);
    }

    public boolean isCascadeLimit() {
        return cascadeFX_m.isRevLimitSwitchClosed() == 1;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
    }

    @Override
    public void addToDriveTab(ShuffleboardTab tab) {
    }

}
