package frc.robot.util;

public enum IntakeState {
     
    OFF,  // Intake not spinning
    HAS,  // Intake spinning with current limiting
    HASNT;  // Intake spinning with given speed

    /**
     * @param spinning If the intake is spinning or not
     * @param current If the current limiting is active or not
     * @return The current state of the intake
     */
    public static IntakeState getIntakeState(boolean spinning, boolean current) {
        return spinning == true ? (current == true ? HAS : HASNT) : OFF;
    }
}
