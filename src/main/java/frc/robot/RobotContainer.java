// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.T_Cascade_Home;
import frc.robot.commands.T_Cascade_In;
import frc.robot.commands.T_Cascade_Move;
import frc.robot.commands.T_Cascade_Out;
import frc.robot.commands.T_Cascade_Preset;
import frc.robot.commands.T_Drivetrain_ArcadeDrive;
import frc.robot.subsystems.Cascade;
import frc.robot.commands.T_Intake_In;
import frc.robot.commands.T_Intake_Out;
import frc.robot.commands.T_Subsystem_Manual;
import frc.robot.commands.T_TronWheel_Home;
import frc.robot.commands.T_TronWheel_Preset;
import frc.robot.commands.T_TronWheel_Rotate;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.TronWheel;
import frc.robot.util.ComboButton;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import static frc.robot.Constants.*;
import static frc.robot.Constants.Cascade.*;
import static frc.robot.Constants.TronWheel.*;

public class RobotContainer {
  public static CommandXboxController driver_s;
  public static CommandXboxController operator_s;

  public static Drivetrain drivetrainInst_s;
  public static Cascade cascadeInst_s;
  public static TronWheel tronWheelInst_s;
  public static Intake intakeInst_s;

  public RobotContainer() {
    driver_s = new CommandXboxController(PORT_DRIVER);
    operator_s = new CommandXboxController(PORT_OPERATOR);

    drivetrainInst_s = Drivetrain.getInstance();
    drivetrainInst_s.setDefaultCommand(new T_Drivetrain_ArcadeDrive());
    cascadeInst_s = Cascade.getInstance();
    cascadeInst_s.setDefaultCommand(new T_Cascade_Move());
    tronWheelInst_s = TronWheel.getInstance();
    tronWheelInst_s.setDefaultCommand(new T_TronWheel_Rotate());
    intakeInst_s = Intake.getInstance();


    configureBindings();
  }

  /**
   * Driver/Operator controls
   */
  private void configureBindings() {
    operator_s.back().onTrue(new T_Cascade_Home());
    operator_s.start().onTrue(new T_TronWheel_Home());
    
    operator_s.x().whileTrue(new T_Cascade_Out());
    operator_s.y().whileTrue(new T_Cascade_In());
    
    operator_s.rightTrigger(0.5).toggleOnTrue(new T_Intake_In());
    operator_s.leftTrigger(0.5).toggleOnTrue(new T_Intake_Out());
    
    new ComboButton(operator_s.start(), operator_s.a())
      .whenShiftPressed(new T_Subsystem_Manual(tronWheelInst_s));
      // .whenPressed(new ParallelCommandGroup(new T_Cascade_Preset(VAL_MID_PRESET), new T_TronWheel_Preset(VAL_ANGLE_SCORE)));
      // .whenPressed(new T_TronWheel_Preset(VAL_ANGLE_SCORE));

    operator_s.b().onTrue(new ParallelCommandGroup(new T_Cascade_Preset(VAL_MID_PRESET), new T_TronWheel_Preset(VAL_ANGLE_SCORE)));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
