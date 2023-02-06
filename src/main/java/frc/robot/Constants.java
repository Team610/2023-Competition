package frc.robot;

public final class Constants {
    public static final int PORT_DRIVER = 0;
    public static final int PORT_OPERATOR = 1;
    public static final String CAN_BUS_NAME = "Vulture";
    
    public static class Drivetrain {
        public static final int CAN_LEFT_BATMAN = 4;
        public static final int CAN_LEFT_ROBIN = 5;
        public static final int CAN_RIGHT_BATMAN = 3;
        public static final int CAN_RIGHT_ROBIN = 2;
        public static final int CAN_PIDGEY = 1;

        public static final double UNIT_TICKS_PER_REV = 21580;
        public static final double UNIT_DIST_PER_REV = 0.4930;

        public static final double VAL_DEADBAND = 0.03;
    }

    public static class Roller{
        public static final int CAN_ROLLER = 6; //TODO: Change to correct CAN ID
    }
}
