package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

import static frc.robot.Constants.Drivetrain.*;

public class A_Pidgeon_Balance extends CommandBase {
    private Drivetrain drivetrainInst_m;
    private static double curAng_s;

    public A_Pidgeon_Balance() {
        drivetrainInst_m = Drivetrain.getInstance();
        addRequirements(drivetrainInst_m);
    }

    @Override
    public void initialize() {
        curAng_s = RobotContainer.pidgey_s.getPitch();
    }

    @Override
    public void execute() {
        curAng_s = RobotContainer.pidgey_s.getPitch();
        if(curAng_s < -VAL_PIDGEY_RANGE){
            // drivetrainInst_m.leftBatman_m.set(0.1);
            // drivetrainInst_m.rightBatman_m.set(0.1);
        } else if (curAng_s > VAL_PIDGEY_RANGE){
            // drivetrainInst_m.leftBatman_m.set(-0.1);
            // drivetrainInst_m.rightBatman_m.set(-0.1);
        } else {
            // drivetrainInst_m.leftBatman_m.set(0);
            // drivetrainInst_m.rightBatman_m.set(0);
        }
    }

    @Override
    public boolean isFinished(){
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }
}
