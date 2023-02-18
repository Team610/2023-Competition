package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.util.Subsystem610;

public class T_Subsystem_Manual extends CommandBase{
    private Subsystem610 system_m;

    public T_Subsystem_Manual(Subsystem610 system) {
        system_m = system;
        addRequirements(system_m);
    }

    @Override
    public void execute() {
        system_m.setManual(!system_m.getManual());
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}