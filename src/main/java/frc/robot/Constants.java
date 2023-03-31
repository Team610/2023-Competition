package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

public final class Constants {
    // Common
    public static final int PORT_DRIVER = 0;
    public static final int PORT_OPERATOR = 1;
    public static final String CAN_BUS_NAME = "Vulture";
    public static final int VAL_CONFIG_TIMEOUT = 10;
    public static final double VAL_DEADBAND = 0.03;
    
    public static class Drivetrain {
        public static final int CAN_LEFT_BATMAN = 5;
        public static final int CAN_LEFT_ROBIN = 6;
        public static final int CAN_RIGHT_BATMAN = 3;
        public static final int CAN_RIGHT_ROBIN = 2;
        public static final int CAN_PIDGEY = 1;

        public static final double VAL_PIDGEY_RANGE = 2.0;

        // units are in meters for Ramsete
        public static final double VAL_RAMSETE_B = 1;
        public static final double VAL_RAMSETE_ZETA = 0.7;
        
        public static final double UNIT_FEET_IN_METERS = 3.280;
        public static final double UNIT_METERS_IN_FEET = 0.305;
        public static final double UNIT_TICKS_PER_REV = 29500;
        public static final double UNIT_DIST_PER_REV = 0.47878;
        
        public static final double CALC_TICKS_TO_METERS(double ticks) {
            return (ticks/UNIT_TICKS_PER_REV)*UNIT_DIST_PER_REV;
        }
        public static final double CALC_METERS_TO_TICKS(double meters) {
            return (meters/UNIT_DIST_PER_REV)*UNIT_TICKS_PER_REV;
        }
        
        public static final double VAL_KS = 0.076361;
        public static final double VAL_KV = 3.4151;
        public static final double VAL_KA = 0.40329;
        public static final double VAL_KP = 0.43731;
        public static final double VAL_KD = 0.0;
        public static final double VAL_KI = 0;

        public static final double VAL_TRACK_WIDTH = 0.5877;
        public static final DifferentialDriveKinematics DRIVE_KINEMATICS = new DifferentialDriveKinematics(VAL_TRACK_WIDTH);

        public static final double VAL_WHEEL_DIA = 0.1524;
        public static final double VAL_MAX_VELO = 1.93;
        public static final double VAL_MAX_ACCEL = VAL_MAX_VELO*0.5;
        public static final double VAL_MAX_VOLTS = 11;

        public static final double VAL_MAX_SPEED = 0.7;
        public static final double VAL_TURBO_SPEED = 1.0;
        public static final double VAL_TURN_SPEED = 0.6;
        public static final double VAL_TURBO_TURN_SPEED = 0.7;
    }

    public static class Cascade {
        public static final int CAN_CASCADE = 4;
        public static final double VAL_MAX_SPEED_MANUAL = 0.5;
        public static final double VAL_MAX_SPEED_OUT = 0.3;
        public static final double VAL_MAX_SPEED_IN = 0.2;

        public static final int VAL_MAX_TICKS = 145000;  // Max ticks for cascade to extend
        public static final double VAL_CRUISE_VELO_UP = 15000;  // Ticks per 100ms
        public static final double VAL_MAX_ACCEL_UP = 15000;
        public static final double VAL_CRUISE_VELO_DOWN = 9666;  // Ticks per 100ms
        public static final double VAL_MAX_ACCEL_DOWN = 9666;
        public static final int VAL_CONVERT_TICKS = 1450; // Tick to percent conversion factor
        
        public static final double VAL_KP_DOWN = 0.153;  // Slot 0
        public static final double VAL_KI_DOWN = 0;
        public static final double VAL_KD_DOWN = 15;
        public static final double VAL_KF_DOWN = 0.3;
        public static final double VAL_KP_UP = 0.14;  // Slot 1
        public static final double VAL_KI_UP = 0;
        public static final double VAL_KD_UP = 0;
        public static final double VAL_KF_UP = 0.08;

        public static final double VAL_FEEDFORWARD = 0.08;
        public static final double VAL_CLOSEDLOOP_ERR = 300;

        public static final double UNIT_TICKS_PER_REV = 20480;
        public static final double UNIT_DIAM_PULLEY = 3.36; // average diameter of pulley, accounting for piling strings
        public static final double UNIT_DIST_PER_REV = UNIT_DIAM_PULLEY * Math.PI;                    // in inches
        public static final double UNIT_TICKS_TO_INCHES = UNIT_DIST_PER_REV / UNIT_TICKS_PER_REV;
        public static final double UNIT_INCHES_TO_TICKS =  UNIT_TICKS_PER_REV / UNIT_DIST_PER_REV;
        public static final double UNIT_ANGLE = Math.toRadians(37.5); // Angle of cascade arm to ground

        // Preset units are in ticks
        public static final double VAL_MID_CONE_PRESET = 109000;
        public static final double VAL_HIGH_CONE_PRESET = 145000;
        public static final double VAL_RAMP_CONE_PRESET = 8060;
        public static final double VAL_TRANSPORT_CONE_PRESET = 14000;
        public static final double VAL_LINEUP_CONE_PRESET = 50000;
        public static final double VAL_AUTO_CONE_PRESET = 12180;
        public static final double VAL_GROUND_CONE_PRESET = 5500;

        public static final double VAL_MID_CUBE_PRESET = 109000;
        public static final double VAL_HIGH_CUBE_PRESET = 145000;
        public static final double VAL_RAMP_CUBE_PRESET = 8060;
        public static final double VAL_TRANSPORT_CUBE_PRESET = 14000;
        public static final double VAL_LINEUP_CUBE_PRESET = 50000;
        public static final double VAL_AUTO_CUBE_PRESET = 12180;
        public static final double VAL_GROUND_CUBE_PRESET = 5500;
        

        /**
         * Converts vertical height to ticks for cascade arm to travel
         * @param inches Vertical height to input
         * @return
         */
        public static final double CALC_TICKS(double inches) {
            return ((inches-2)/Math.sin(UNIT_ANGLE)) * (UNIT_INCHES_TO_TICKS);
        }
    }

    public static class TronWheel {
        public static final int CAN_ROTATE_SRX = 2;

        public static final double VAL_MAX_SPEED = 0.5;
        public static final double VAL_DEGREES_TO_TICKS = 93.75;  // Ticks/degree

        public static final double VAL_FWD_LIM = 30000;
        
        public static final double VAL_MAX_ACCEL = 12000;
        public static final double VAL_CRUISE_VELO = 3000;  // Ticks per 100 ms

        public static final double VAL_KP = 0.45;
        public static final double VAL_KI = 0;
        public static final double VAL_KD = 12;
        public static final double VAL_KF = 0.341;

        public static final double VAL_ANG_CONE_RAMP = 14000;  // ticks
        public static final double VAL_ANG_CONE_SCORE = 0;
        public static final double VAL_ANG_CONE_GROUND = 25600;   // angle to pickup from ground start
        public static final double VAL_ANG_CONE_TRANSPORT = 10000;
        public static final double VAL_ANG_CONE_HYBRID = 18000;

        // TODO: Redo values
        public static final double VAL_ANG_CUBE_RAMP = 14000;  // ticks
        public static final double VAL_ANG_CUBE_SCORE = 5000;
        public static final double VAL_ANG_CUBE_GROUND = 0;   // angle to pickup from ground start
        public static final double VAL_ANG_CUBE_TRANSPORT = 15000;
        public static final double VAL_ANG_CUBE_HYBRID = 18000;
    }

    public static class Intake {
        public static final int CAN_KICKER_SRX = 1;
        public static final int CAN_INTAKE_SRX = 3;

        public static final double VAL_IN_PERCENT = 1.0;
        public static final double VAL_OUT_NORMAL = -0.3;
        public static final double VAL_OUT_TURBO = -0.7;
        public static final double VAL_HOLD_PERCENT = 0.3;

        public static final int VAL_CONTINUOUS_CURRENT_LIMIT = 5;
        
        public static final double VAL_STALL_TIME = 0.5 *1000; // ms
        public static final int VAL_SAMPLES = (int) VAL_STALL_TIME/20;
    }

    public static class Vision {
        public static final double VAL_ANGLE_KP = 0.02;
        public static final double VAL_ANGLE_KI = 0;
        public static final double VAL_ANGLE_KD = 0;
        public static final double VAL_DRIVE_KP = 0.005;
        public static final double VAL_DRIVE_KI = 0;
        public static final double VAL_DRIVE_KD = 0.0005;
        public static final double VAL_LEFT_ANGLE_OFSET = 2;
        public static final double VAL_RIGHT_ANGLE_OFSET = 2;
        public static final double VAL_MIN_POWER = 0.05;
    }

    public static class PigeonBalance{
        public static final double VAL_KP_BAL_PID = 0.007;
        public static final double VAL_KI_BAL_PID = 0.00;
        public static final double VAL_KD_BAL_PID = 0.0005;
        public static final double VAL_BAL_TOLERANCE = 0.4;
        public static final double VAL_VELO_TOLERANCE = 0;
        public static final double VAL_BAL_SETPOINT = 0;
    }
}
