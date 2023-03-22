package frc.robot.commands;


import static frc.robot.Constants.*;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Cascade;
import frc.robot.subsystems.Drivetrain;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Default teleop drive mode
 */
public class T_Drivetrain_ArcadeDrive extends CommandBase {
    private Drivetrain drivetrainInst_m;
    private Cascade cascadeInt_m;

    public T_Drivetrain_ArcadeDrive() {
        drivetrainInst_m = Drivetrain.getInstance();
        cascadeInt_m = Cascade.getInstance();
        addRequirements(drivetrainInst_m);
    }

    @Override
    public void execute() {
        //left joystick for up/down movement
        double y = MathUtil.applyDeadband(RobotContainer.driver_s.getLeftY(), VAL_DEADBAND);
        //right joystick for left/right movement
        double x = MathUtil.applyDeadband(RobotContainer.driver_s.getRightX(), VAL_DEADBAND);
        //left bumper for turbo mode when held
        boolean turbo = RobotContainer.driver_s.leftBumper().getAsBoolean();

        y = y * y * y;
        x = x * x * x;
        if(cascadeInt_m.cascadeTickPercent() >= .45){
        y *= turbo ? (1-(0.3*cascadeInt_m.cascadeTickPercent())) : (0.8-(0.7*cascadeInt_m.cascadeTickPercent()));
        }else{y *= turbo ? 1 : 0.8;}
        x *= 0.7;
        double leftSpeed = -y + x;
        double rightSpeed = -y - x;
        drivetrainInst_m.setLeft(leftSpeed);
        drivetrainInst_m.setRight(rightSpeed);
    }
}