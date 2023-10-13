// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.A_Disable_Safeties;

public class Robot extends TimedRobot {
  private RobotContainer robotContainer_m;
  private Command autonomousCommand_m;

  @Override
  public void robotInit() {
    robotContainer_m = new RobotContainer();
    RobotContainer.drivetrainInst_s.resetSensors();
    RobotContainer.cascadeInst_s.setSafety(true);
    RobotContainer.tronWheelInst_s.setSafety(true);
    RobotContainer.pdb_s.setSwitchableChannel(false);
    RobotContainer.visionInst_s.setLedMode(1);
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
    RobotContainer.drivetrainInst_s.setCoast();
    RobotContainer.cascadeInst_s.setSafety(true);
    RobotContainer.tronWheelInst_s.setSafety(true);
    RobotContainer.visionInst_s.setLedMode(1);
  }

  @Override
  public void disabledPeriodic() {
    RobotContainer.drivetrainInst_s.setBrake();
    RobotContainer.cascadeInst_s.setSafety(SmartDashboard.getBoolean("Cascade Safety", true));
    RobotContainer.tronWheelInst_s.setSafety(SmartDashboard.getBoolean("Tron Wheel Safety", true));
  }

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    RobotContainer.drivetrainInst_s.setBrake();
    autonomousCommand_m = robotContainer_m.getAutonomousCommand();

    if (autonomousCommand_m != null) {
      autonomousCommand_m.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    RobotContainer.drivetrainInst_s.setBrake();
    RobotContainer.cascadeInst_s.setManual(false);
    RobotContainer.tronWheelInst_s.setManual(false);
    new A_Disable_Safeties();

    if (autonomousCommand_m != null) {
      autonomousCommand_m.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}
}
