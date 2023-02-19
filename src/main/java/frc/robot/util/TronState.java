package frc.robot.util;

import static frc.robot.Constants.TronWheel.*;

public enum TronState {
     
    GROUND (VAL_ANGLE_GROUND),  // Tron wheel angled to intake from floor
    RAMP (VAL_ANGLE_RAMP),  // Tron wheel angled to intake from ramp
    TRANSPORT (VAL_ANGLE_TRANSPORT),  // Tron wheel angled to carry game piece
    SCORE (VAL_ANGLE_SCORE);  // Tron wheel angled to score

    private double angle_m;

    private TronState(double angle) {
        angle_m = angle;
    }

    public double getAngle() {
        return angle_m;
    }

    public void setAngle(double angle) {
        angle_m = angle;
    }

    // /**
    //  * @param ticks The number of ticks the cascade is currently at
    //  * @return The current state of the tron wheel
    //  */
    // public static TronState getTronState(int ticks) {
    //     return ticks < 1000 ? GROUND : (ticks < 2000 ? RAMP : (ticks < 3000 ? TRANSPORT : SCORE));
    // }
}
