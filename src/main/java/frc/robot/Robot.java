// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.A_Test_Path;

public class Robot extends TimedRobot {
  private RobotContainer robotContainer_m;

  @Override
  public void robotInit() {
    robotContainer_m = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
    RobotContainer.drivetrainInst_s.setCoast();
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
    new A_Test_Path().schedule();
  }

  @Override
  public void autonomousPeriodic() {
    
  }

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
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
