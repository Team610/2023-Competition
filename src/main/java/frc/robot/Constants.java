package frc.robot;

public final class Constants {
    // Common
    public static final int PORT_DRIVER = 0;
    public static final int PORT_OPERATOR = 1;
    public static final String CAN_BUS_NAME = "Vulture";
    public static final int VAL_CONFIG_TIMEOUT = 20;
    
    public static class Drivetrain {
        public static final int CAN_LEFT_BATMAN = 5;
        public static final int CAN_LEFT_ROBIN = 6;
        public static final int CAN_RIGHT_BATMAN = 3;
        public static final int CAN_RIGHT_ROBIN = 2;
        public static final int CAN_PIDGEY = 1;

        public static final double UNIT_TICKS_PER_REV = 21580;
        public static final double UNIT_DIST_PER_REV = 0.4930;

        public static final double VAL_DEADBAND = 0.03;
    }

    public static class Cascade {
        public static final int CAN_CASCADE = 4;
        public static final double VAL_MAX_SPEED = 0.5;
        public static final double VAL_MAX_TICKS = 145000;  // Max ticks for cascade to extend

        public static final double VAL_TICKS_PER_REV = 20176;
        public static final double VAL_BELT_LENGTH = 9.42477796077;
        public static final double UNITS_INCH_TO_TICKS = VAL_TICKS_PER_REV / VAL_BELT_LENGTH;  //TODO: Placeholder value
    }
}
