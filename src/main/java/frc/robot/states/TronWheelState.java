package frc.robot.states;

import static frc.robot.Constants.TronWheel.*;

public enum TronWheelState {
    RAMP (VAL_ANGLE_RAMP),
    SCORE (VAL_ANGLE_SCORE),
    GROUND (VAL_ANGLE_GROUND_INIT),
    TRANSPORT (VAL_ANGLE_TRANSPORT),
    HYBRID (VAL_ANGLE_HYBRID);

    private double m_preset;

    private TronWheelState(double preset){
        m_preset = preset;
    }

    public double getPreset(){
        return m_preset;
    }

    public void setPreset(double preset){
        m_preset = preset;
    }
}
