package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.exampleSubsystem;

public class T_exampleSubsystem_setSpeed extends CommandBase {
    private exampleSubsystem exampleSubsystemInst_m;
    private double speed_m;

    public T_exampleSubsystem_setSpeed(double speed) {
        exampleSubsystemInst_m = exampleSubsystem.getInstance();
        addRequirements(exampleSubsystemInst_m);
        speed_m = speed >= 0 ? speed : 0;
    }


    @Override
    public void execute() {
        exampleSubsystemInst_m.exampleMethod(speed_m);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}