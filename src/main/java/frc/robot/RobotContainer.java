// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.*;

import frc.robot.commands.T_Cascade_Move;
import frc.robot.commands.T_Drivetrain_ArcadeDrive;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Cascade;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  public static CommandXboxController driver_s;
  public static CommandXboxController operator_s;

  public static Drivetrain drivetrainInst_s;
  public static Cascade cascadeInst_s;

  public RobotContainer() {
    driver_s = new CommandXboxController(PORT_DRIVER);
    operator_s = new CommandXboxController(PORT_OPERATOR);

    drivetrainInst_s = Drivetrain.getInstance();
    cascadeInst_s = Cascade.getInstance();

    drivetrainInst_s.setDefaultCommand(new T_Drivetrain_ArcadeDrive());
    // cascadeInst_s.setDefaultCommand(new T_Cascade_Move());

    configureBindings();
  }

  private void configureBindings() {

  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
