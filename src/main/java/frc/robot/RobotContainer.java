// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.T_Cascade_Home;
import frc.robot.commands.T_Cascade_Move;
import frc.robot.commands.T_Cascade_Preset;

import frc.robot.commands.A_Test_Path;
import frc.robot.commands.G_GroundIntake_Automatic;
import frc.robot.commands.G_IntakeTronWheel_Automatic;
import frc.robot.commands.T_Drivetrain_ArcadeDrive;
import frc.robot.commands.T_IntakeTronWheel_GroundIntake;
import frc.robot.subsystems.Cascade;
import frc.robot.commands.T_Intake_In;
import frc.robot.commands.T_Intake_Out;
import frc.robot.commands.T_Subsystem_Manual;
import frc.robot.commands.T_TronWheel_Home;
import frc.robot.commands.T_TronWheel_Move;
import frc.robot.commands.T_TronWheel_Preset;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.TronWheel;
import frc.robot.util.ComboButton;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import static frc.robot.Constants.*;
import static frc.robot.Constants.Cascade.*;
import static frc.robot.Constants.TronWheel.*;

public class RobotContainer {
  SendableChooser<Command> autoChooser_m = new SendableChooser<>();
  public static CommandXboxController driver_s;
  public static CommandXboxController operator_s;

  public static Drivetrain drivetrainInst_s;
  public static Cascade cascadeInst_s;
  public static TronWheel tronWheelInst_s;
  public static Intake intakeInst_s;

  public RobotContainer() {
    autoChooser_m.setDefaultOption("Test Path", new A_Test_Path());
    SmartDashboard.putData("Auto Chooser", autoChooser_m);

    driver_s = new CommandXboxController(PORT_DRIVER);
    operator_s = new CommandXboxController(PORT_OPERATOR);

    drivetrainInst_s = Drivetrain.getInstance();
    drivetrainInst_s.setDefaultCommand(new T_Drivetrain_ArcadeDrive());
    cascadeInst_s = Cascade.getInstance();
    cascadeInst_s.setDefaultCommand(new T_Cascade_Move());
    tronWheelInst_s = TronWheel.getInstance();
    tronWheelInst_s.setDefaultCommand(new T_TronWheel_Move());
    intakeInst_s = Intake.getInstance();

    configureBindings();
  }

  /**
   * Driver/Operator controls
   */
  private void configureBindings() {
    // ! Driver Controls
    // driver_s.rightTrigger(0.5).onTrue(new T_Intake_In());
    driver_s.rightTrigger(0.5).toggleOnTrue(new T_Intake_In());
    driver_s.leftTrigger(0.5).whileTrue(new T_Intake_Out());

    // ! Operator Controls
    operator_s.back().onTrue(Commands.parallel(new T_TronWheel_Home(), new T_Cascade_Home()));

    new ComboButton(operator_s.start(), operator_s.a())
        .whenShiftPressed(new T_Subsystem_Manual(tronWheelInst_s))
        .whenPressed(Commands.parallel(new T_Cascade_Preset(VAL_MID_PRESET), new T_TronWheel_Preset(VAL_ANGLE_SCORE)));

    new ComboButton(operator_s.start(), operator_s.b())
        .whenShiftPressed(new T_Subsystem_Manual(cascadeInst_s))
        .whenPressed(Commands.parallel(new T_Cascade_Preset(VAL_HIGH_PRESET), new T_TronWheel_Preset(VAL_ANGLE_SCORE)));

    operator_s.x().onTrue(
        Commands.parallel(new T_Cascade_Preset(VAL_RAMP_PRESET), new T_TronWheel_Preset(VAL_ANGLE_RAMP)));

    operator_s.y().onTrue(new T_IntakeTronWheel_GroundIntake());

    // new ComboButton(operator_s.start(), operator_s.y())
    //     .whenShiftPressed(new G_GroundIntake_Automatic())
    //     .whenPressed(new T_IntakeTronWheel_GroundIntake());
  }

  public Command getAutonomousCommand() {
    return autoChooser_m.getSelected();
  }
}
