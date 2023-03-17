package frc.robot.states;

import static frc.robot.Constants.Cascade.*;

public enum CascadeState {
    GROUND (VAL_GROUND_PRESET),
    RAMP (VAL_RAMP_PRESET),
    TRANSPORT (VAL_TRANSPORT_PRESET),
    LINEUP (VAL_LINEUP_PRESET),
    MID (VAL_MID_PRESET),
    HIGH (VAL_HIGH_PRESET),
    AUTO (VAL_AUTO_PRESET);

    private double m_preset;

    private CascadeState(double preset){
        m_preset = preset;
    }

    public double getPreset(){
        return m_preset;
    }

    public void setPreset(double preset){
        m_preset = preset;
    }
}
