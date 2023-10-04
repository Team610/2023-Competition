package frc.robot.states;

import static frc.robot.Constants.Cascade.*;

public enum CascadeState {
    //! Values are (coneHeight, cubeHeight)
    MID (VAL_MID_CONE_PRESET, VAL_MID_CUBE_PRESET),
    HIGH (VAL_HIGH_CONE_PRESET, VAL_HIGH_CUBE_PRESET),
    RAMP (VAL_RAMP_CONE_PRESET, VAL_RAMP_CUBE_PRESET),
    TRANSPORT (VAL_TRANSPORT_CONE_PRESET, VAL_TRANSPORT_CUBE_PRESET),
    LINEUP (VAL_LINEUP_CONE_PRESET, VAL_LINEUP_CUBE_PRESET),
    AUTO (VAL_AUTO_CONE_PRESET, VAL_AUTO_CUBE_PRESET),
    GROUND (VAL_GROUND_CONE_PRESET, VAL_GROUND_CUBE_PRESET);

    private double conePreset_m, cubePreset_m;

    private CascadeState(double conePreset, double cubePreset) {
        conePreset_m = conePreset;
        cubePreset_m = cubePreset;
    }

    public double getConePreset() {
        return conePreset_m;
    }

    public double getCubePreset() {
        return cubePreset_m;
    }
}
