// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private RobotContainer robotContainer_m;

  @Override
  public void robotInit() {
    robotContainer_m = new RobotContainer();
    RobotContainer.cascadeInst_s.setSafety(true);
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
    RobotContainer.drivetrainInst_s.setCoast();
    RobotContainer.cascadeInst_s.setSafety(true);
  }

  @Override
  public void disabledPeriodic() {
    RobotContainer.drivetrainInst_s.setCoast();
  }

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    RobotContainer.drivetrainInst_s.setBrake();
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    RobotContainer.drivetrainInst_s.setBrake();
    RobotContainer.cascadeInst_s.setSafety(SmartDashboard.getBoolean("Cascade Safety", true));
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
