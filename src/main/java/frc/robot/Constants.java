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
        public static final double VAL_MAX_VOLTS = 11; // TODO: change to 12 for later

        public static final double VAL_MAX_SPEED = 0.8;
        public static final double VAL_TURBO_SPEED = 1.0;
        public static final double VAL_TURN_SPEED = 0.7;
    }

    public static class Cascade {
        public static final int CAN_CASCADE = 4;
        public static final double VAL_MAX_SPEED_MANUAL = 0.5;
        public static final double VAL_MAX_SPEED_OUT = 0.3;
        public static final double VAL_MAX_SPEED_IN = 0.2;

        public static final int VAL_MAX_TICKS = 145000;  // Max ticks for cascade to extend
        public static final double VAL_CRUISE_VELO = 145000/15;  // Ticks per 100ms
        public static final double VAL_MAX_ACCEL = VAL_CRUISE_VELO*2;
        
        public static final double VAL_KP = 0.153;
        public static final double VAL_KI = 0;
        public static final double VAL_KD = 15;
        public static final double VAL_KF = 0.3;

        public static final double UNIT_TICKS_PER_REV = 20480;
        public static final double UNIT_DIAM_PULLEY = 3.36; // average diameter of pulley, accounting for piling strings
        public static final double UNIT_DIST_PER_REV = UNIT_DIAM_PULLEY * Math.PI;                    // in inches
        public static final double UNIT_TICKS_TO_INCHES = UNIT_DIST_PER_REV / UNIT_TICKS_PER_REV;
        public static final double UNIT_INCHES_TO_TICKS =  UNIT_TICKS_PER_REV / UNIT_DIST_PER_REV;
        public static final double UNIT_ANGLE = Math.toRadians(37.5); // Angle of cascade arm to ground

        // Preset units are in ticks
        public static final double VAL_MID_PRESET = CALC_TICKS(34)-5820;
        public static final double VAL_HIGH_PRESET = CALC_TICKS(46);
        public static final double VAL_RAMP_PRESET = 14000;
        public static final double VAL_GROUND_PRESET = 0;

        /**
         * Converts vertical height to ticks for cascade arm to travel
         * @param inches Vertical height to input
         * @return
         */
        public static final double CALC_TICKS(double inches) {
            return ((inches-0.5)/Math.sin(UNIT_ANGLE)) * (UNIT_INCHES_TO_TICKS);
        }
    }

    public static class TronWheel {
        public static final int CAN_ROTATE_SRX = 2;

        public static final double VAL_MAX_SPEED = 0.5;
        public static final double VAL_DEGREES_TO_TICKS = 93.75;  // Ticks/degree

        public static final double VAL_FWD_LIM = 30000;
        
        public static final double VAL_MAX_ACCEL = 4480;
        public static final double VAL_MAX_VELO = 2240;  // Ticks per 100 ms
        public static final double VAL_CRUISE_VELO = VAL_MAX_VELO * 0.7;

        public static final double VAL_KP = 0.4;
        public static final double VAL_KI = 0;
        public static final double VAL_KD = 0;
        public static final double VAL_KF = 0.4;

        public static final double VAL_ANGLE_RAMP = 14500;  // ticks
        public static final double VAL_ANGLE_SCORE = 0;
        public static final double VAL_ANGLE_GROUND = 27500;  // angle to pickup from ground
        public static final double VAL_ANGLE_TRANSPORT = 10000;
    }

    public static class Intake {
        public static final int CAN_INTAKE_SRX = 3;

        public static final double VAL_IN_PERCENT = 11/12.0;
        public static final double VAL_OUT_PERCENT = -0.5;
        public static final double VAL_HOLD_PERCENT = 0.3;
    }
}
