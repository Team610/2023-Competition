package frc.robot.commands;

import static frc.robot.Constants.Drivetrain.*;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Default teleop drive mode
 */
public class T_Drivetrain_ArcadeDrive extends CommandBase {
    private Drivetrain drivetrainInst_m;

    public T_Drivetrain_ArcadeDrive() {
        drivetrainInst_m = Drivetrain.getInstance();
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

        y *= turbo ? 1 : 0.8;
        x *= 0.7;
        double leftSpeed = -y + x;
        double rightSpeed = -y - x;
        SmartDashboard.putNumber("Pitch Value", drivetrainInst_m.getPitch());   
        drivetrainInst_m.setLeft(leftSpeed/2);
        drivetrainInst_m.setRight(rightSpeed/2);
    }

}