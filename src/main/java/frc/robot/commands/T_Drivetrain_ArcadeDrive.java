package frc.robot.commands;


import frc.robot.RobotContainer;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.POVButton;

import static frc.robot.Constants.*;
import static frc.robot.Constants.Drivetrain.*;

/**
 * Default teleop drive mode
 */

 
public class T_Drivetrain_ArcadeDrive extends CommandBase {

    public T_Drivetrain_ArcadeDrive() {
        addRequirements(RobotContainer.drivetrainInst_s);
    }

    @Override
    public void execute() {
        //left joystick for up/down movement
        double y = MathUtil.applyDeadband(RobotContainer.driver_s.getLeftY(), VAL_DEADBAND);
        //right joystick for left/right movement
        double x = MathUtil.applyDeadband(RobotContainer.driver_s.getLeftX(), VAL_DEADBAND);
        //left bumper for slow mode when held
        double offset = 0.0;
        boolean slow = RobotContainer.operator_s.leftTrigger().getAsBoolean();
        new POVButton(RobotContainer.driver_s.getHID(), 270).getAsBoolean();
        new POVButton(RobotContainer.driver_s.getHID(), 90).getAsBoolean();

        y = y * y * y;
        x = x * x * x;
        if(new POVButton(RobotContainer.driver_s.getHID(), 270).getAsBoolean()){
            offset -= 0.1;
        } else if(new POVButton(RobotContainer.driver_s.getHID(), 90).getAsBoolean()){
            offset += 0.1;
        } else if(RobotContainer.cascadeInst_s.cascadeTickPercent() >= .45){
            y *= slow ? (VAL_SLOW_SPEED-(0.7*RobotContainer.cascadeInst_s.cascadeTickPercent())) : (VAL_MAX_SPEED-(0.3*RobotContainer.cascadeInst_s.cascadeTickPercent()));
        } else {
            offset = 0;
            y *= slow ? VAL_SLOW_SPEED : VAL_MAX_SPEED;
        }
        x *= slow ? VAL_SLOW_TURN_SPEED : VAL_TURN_SPEED;

        double leftSpeed = -y + x + offset;
        double rightSpeed = -y - x - offset;
    
        RobotContainer.drivetrainInst_s.setLeft(leftSpeed);
        RobotContainer.drivetrainInst_s.setRight(rightSpeed);
    }
}