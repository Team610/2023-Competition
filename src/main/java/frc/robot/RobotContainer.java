// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.*;

import frc.robot.commands.T_Drivetrain_ArcadeDrive;
import frc.robot.commands.T_Intake_In;
import frc.robot.commands.T_Intake_Out;
import frc.robot.commands.T_Subsystem_Manual;
import frc.robot.commands.T_TronWheel_Rotate;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.TronWheel;
import frc.robot.util.ComboButton;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  public static CommandXboxController driver_s;
  public static CommandXboxController operator_s;

  public static Drivetrain drivetrainInst_s;
  public static TronWheel tronWheelInst_s;
  public static Intake intakeInst_s;

  public RobotContainer() {
    driver_s = new CommandXboxController(PORT_DRIVER);
    operator_s = new CommandXboxController(PORT_OPERATOR);

    drivetrainInst_s = Drivetrain.getInstance();
    tronWheelInst_s = TronWheel.getInstance();
    intakeInst_s = Intake.getInstance();

    drivetrainInst_s.setDefaultCommand(new T_Drivetrain_ArcadeDrive());
    tronWheelInst_s.setDefaultCommand(new T_TronWheel_Rotate());
    // intakeInst_s.setDefaultCommand(new T_Intake_Intake());

    configureBindings();
  }

  private void configureBindings() {
    operator_s.rightTrigger(0.5).toggleOnTrue(new T_Intake_In());
    operator_s.leftTrigger(0.5).toggleOnTrue(new T_Intake_Out());
    new ComboButton(operator_s.start(), operator_s.a()).whenShiftPressed(new T_Subsystem_Manual(tronWheelInst_s));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
