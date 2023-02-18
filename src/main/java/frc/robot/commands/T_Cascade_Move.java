package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Cascade;

import static frc.robot.Constants.Cascade.*;

public class T_Cascade_Move extends CommandBase {
    private Cascade cascadeInst_m;

    public T_Cascade_Move() {
        cascadeInst_m = Cascade.getInstance();
        addRequirements(cascadeInst_m);
    }

    @Override
    public void execute() {
        if(!cascadeInst_m.getSafety()){
            cascadeInst_m.spinMagic();
        }
    }

    @Override
    public boolean isFinished(){
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        cascadeInst_m.stop();
    }
}
