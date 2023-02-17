package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class T_Arm_Stop extends CommandBase{
    private Arm armInst_m;
    public T_Arm_Stop(){
        armInst_m = Arm.getInstance();
        addRequirements(armInst_m);
    }

    @Override
    public void initialize(){
        
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        armInst_m.stop();
    }

}
