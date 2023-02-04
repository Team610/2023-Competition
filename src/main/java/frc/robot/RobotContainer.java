// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.*;

import frc.robot.commands.T_Drivetrain_ArcadeDrive;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.ComboButton;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
// import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
  public static CommandXboxController driver_s;
  public static CommandXboxController operator_s;

  // private static final Trigger driverShift_s = driver_s.start();
  // private static final Trigger operatorShift_s = operator_s.start();

  public static Drivetrain drivetrainInst_s;

  public RobotContainer() {
    driver_s = new CommandXboxController(PORT_DRIVER);
    operator_s = new CommandXboxController(PORT_OPERATOR);

    drivetrainInst_s = Drivetrain.getInstance();
    drivetrainInst_s.setDefaultCommand(new T_Drivetrain_ArcadeDrive());

    configureBindings();
  }

  private void configureBindings() {
    new ComboButton(driver_s.x(), driver_s.start())
      .whenShiftPressed(Commands.print("X button pressed with shift"))
      .whenPressed(Commands.print("X button pressed")); 
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
