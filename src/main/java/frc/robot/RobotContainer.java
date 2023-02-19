// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.*;

import frc.robot.commands.T_Drivetrain_ArcadeDrive;
import frc.robot.commands.G_Drivetrain_Group;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class RobotContainer {
  //SendableChooser<Command> autoChooser_m = new SendableChooser<>();
  public static CommandXboxController driver_s;

  public static Drivetrain drivetrainInst_s;

  public RobotContainer() {
    // autoChooser_m.setDefaultOption("DriveForward & Balance", new G_Drivetrain_Group());
    // SmartDashboard.putData("Auto Chooser", autoChooser_m);

    driver_s = new CommandXboxController(PORT_DRIVER);

    drivetrainInst_s = Drivetrain.getInstance();
    drivetrainInst_s.setDefaultCommand(new T_Drivetrain_ArcadeDrive());

    configureBindings();
  }

  private void configureBindings() {
    driver_s.a().whileTrue(new G_Drivetrain_Group());
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
    //return autoChooser_m.getSelected();
  }
}
