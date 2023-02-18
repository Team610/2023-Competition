package frc.robot;

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

        public static final double UNIT_TICKS_PER_REV = 21580;
        public static final double UNIT_DIST_PER_REV = 0.4930;

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

        public static final double VAL_TICKS_PER_REV = 20176;
        public static final double VAL_BELT_LENGTH = 9.42477796077;
        public static final double UNITS_INCH_TO_TICKS = VAL_TICKS_PER_REV / VAL_BELT_LENGTH;
    }

    public static class TronWheel {
        public static final int CAN_ROTATE_SRX = 2;

        public static final double VAL_MAX_SPEED = 0.5;
        public static final double VAL_DEGREES_TO_TICKS = 93.75;  // Ticks/degree

        public static final double VAL_ANGLE_GROUND = 0;  //TODO: Update with actual value
        public static final double VAL_ANGLE_RAMP = 1000;  //TODO: Update with actual value
        public static final double VAL_ANGLE_TRANSPORT = 2000;  //TODO: Update with actual value
        public static final double VAL_ANGLE_SCORE = 3000;  //TODO: Update with actual value

        public static final double VAL_FWD_LIM = 30000;
        
        public static final double VAL_MAX_ACCEL = 4480;
        public static final double VAL_MAX_VELO = 2240;  // Ticks per 100 ms
        public static final double VAL_CRUISE_VELO = VAL_MAX_VELO * 0.7;

        public static final double VAL_KP = 0.4;
        public static final double VAL_KI = 0;
        public static final double VAL_KD = 0;
        public static final double VAL_KF = 0.4;
    }

    public static class Intake {
        public static final int CAN_INTAKE_SRX = 3;

        public static final double VAL_IN_PERCENT = 11/12.0;
        public static final double VAL_OUT_PERCENT = -0.5;
        public static final double VAL_HOLD_PERCENT = 0.3;

        public static final double VAL_KP = 0;
        public static final double VAL_KI = 0;
        public static final double VAL_KD = 0;
        public static final double VAL_KF = 37;
    }
}
