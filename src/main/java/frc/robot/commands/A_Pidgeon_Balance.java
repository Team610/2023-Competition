package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

import static frc.robot.Constants.*;
import static frc.robot.Constants.Drivetrain.*;

import com.ctre.phoenix.sensors.WPI_Pigeon2;

public class A_Pidgeon_Balance extends CommandBase {
    private Drivetrain driveInst_m;
    private static double curAng_s;

    public A_Pidgeon_Balance() {
        driveInst_m = Drivetrain.getInstance();
        addRequirements(driveInst_m);
    }

    @Override
    public void initialize() {
        curAng_s = RobotContainer.pidgey_s.getPitch();
    }

    @Override
    public void execute() {
        curAng_s = RobotContainer.pidgey_s.getPitch();
        if(curAng_s < -VAL_PIDGEY_RANGE){
            driveInst_m.setLeft(0.1);
            driveInst_m.setRight(0.1);
        } else if (curAng_s > VAL_PIDGEY_RANGE){
            driveInst_m.setLeft(-0.1);
            driveInst_m.setRight(-0.1);
        } else {
            driveInst_m.setLeft(0);
            driveInst_m.setRight(0);
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
