package frc.robot.states;

import static frc.robot.Constants.TronWheel.*;

public enum TronWheelState {
    //! Values are (coneAng, cubeAng)
    RAMP (VAL_ANG_CONE_RAMP, VAL_ANG_CUBE_RAMP),
    SCORE (VAL_ANG_CONE_SCORE, VAL_ANG_CUBE_SCORE),
    GROUND (VAL_ANG_CONE_GROUND, VAL_ANG_CUBE_GROUND),
    TRANSPORT (VAL_ANG_CONE_TRANSPORT, VAL_ANG_CUBE_TRANSPORT),
    HYBRID (VAL_ANG_CONE_HYBRID, VAL_ANG_CUBE_HYBRID);

    private double coneAng_m, cubeAng_m;

    private TronWheelState (double coneAng, double cubeAng){
        coneAng_m = coneAng;
        cubeAng_m = cubeAng;
    }

    public double getConeAng() {
        return coneAng_m;
    }

    public double getCubeAng() {
        return cubeAng_m;
    }
}
