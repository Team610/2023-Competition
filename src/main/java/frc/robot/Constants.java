package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

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

        // units are in meters for Ramsete

        public static final double VAL_KS = 0.063267;
        public static final double VAL_KV = 3.4115;
        public static final double VAL_KA = 0.20518;
        public static final double VAL_KP = 1.4449;
        public static final double VAL_KD = 0.0;
        public static final double VAL_KI = 0.0;
        

        public static final double VAL_TRACK_WIDTH = 0.59492;
        public static final DifferentialDriveKinematics DRIVE_KINEMATICS = new DifferentialDriveKinematics(VAL_TRACK_WIDTH);

        public static final double VAL_WHEEL_DIA = 0.1524;
        public static final double VAL_MAX_VELO = 1.93;
        public static final double VAL_MAX_ACCEL = VAL_MAX_VELO*0.5;
        public static final double VAL_MAX_VOLTS = 10; // TODO: change to 12 for later

        public static final double VAL_RAMSETE_B = 1;
        public static final double VAL_RAMSETE_ZETA = 0.7;
        
        public static final double UNIT_FEET_IN_METERS = 3.280;
        public static final double UNIT_METERS_IN_FEET = 0.305;
        public static final double UNIT_TICKS_PER_REV = 29500;
        public static final double UNIT_DIST_PER_REV = 0.47878;
        
        public static final double VAL_DEADBAND = 0.03;

        public static final double VAL_GRAVVECTOR[] = {0,0,1};
    }

    public static class Simulation {
        public static final double VAL_GEARING = 14.8809;
        public static final double VAL_INERTIA = 2.1; //kgm^2
        public static final double VAL_MASS = 56; //Kilograms
        public static final double VAL_WHEEL_RAD = 0.0762; //Metres
    }

    public static class XBoxConstants {
        public static final int BTN_X = 3;
        public static final int BTN_A = 1;
        public static final int BTN_B = 2;
        public static final int BTN_Y = 4;
        public static final int BTN_RB = 6;
        public static final int AXIS_RT = 3;
        public static final int BTN_LB = 5;
        public static final int AXIS_LT = 2;
        public static final int BTN_BACK = 7;
        public static final int BTN_START = 8;
        
        public static final int BTN_DOWN = 180;

        public static final int AXIS_LEFT_X = 0;
        public static final int AXIS_LEFT_Y = 1;
        public static final int AXIS_RIGHT_X = 4;
        public static final int AXIS_RIGHT_Y = 5;

        public static final int BTN_LEFT_JOYSTICK = 9;
        public static final int BTN_RIGHT_JOYSTICK = 10;
    }

    public static class exampleSubsystem {
        public static final int CAN_EXAMPLE_MOTOR = 0;
    }
}