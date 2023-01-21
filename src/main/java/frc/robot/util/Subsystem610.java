package frc.robot.util;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Subsystem;

public abstract class Subsystem610 implements Subsystem, Sendable {
    //! Class Members
    private int loopCounter_m;
    private boolean isManual_m;
    /**
     * Register the given subsystem on the command scheduler using the name provided
     * @param subsytemName Name to use to register the subsystem
     */
    public Subsystem610(String subsytemName) {
        SendableRegistry.addLW(this, subsytemName, subsytemName);
        CommandScheduler.getInstance().registerSubsystem(this);
        loopCounter_m = 0;
        isManual_m = false;
    }

    //! Methods all subsystems inherit
    /**
     * Increment the loop counter for this subsystem, should be done once per code cycle
     */
    public synchronized void incrementLoopCount() {
        loopCounter_m++;
    }

    //! Accessors/Mutators
    /**
     * Get the current loop counter value for this subsystem
     * @return The loop counter value, should be the number of code cycles iterated
     */
    public synchronized int getLoopCount() {
        return loopCounter_m;
    }

    /**
     * Reset the loop counter to 0 for this subsystem
     */
    public synchronized void resetLoopCount() {
        loopCounter_m = 0;
    }

    /**
     * @return Current manual state of this subsystem
     */
    public boolean getManual() {
        return isManual_m;
    }

    /**
     * @param manual Desired manual state of this subsystem
     */
    public void setManual(boolean manual) {
        isManual_m = manual;
    }

    public abstract void addToDriveTab(ShuffleboardTab tab);
}