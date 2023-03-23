// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.T_Cascade_Home;
import frc.robot.commands.T_Cascade_Move;
import frc.robot.commands.T_Cascade_Preset;
import frc.robot.commands.G_BlueLeftGrid;
import frc.robot.commands.G_Preload;
import frc.robot.commands.G_PreloadBalance;
import frc.robot.commands.G_PreloadLeave;
import frc.robot.commands.G_RedLeftGrid1Half;
import frc.robot.commands.G_BlueRightGrid1Half;
import frc.robot.commands.T_Drivetrain_ArcadeDrive;
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
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.XboxController;

import static frc.robot.Constants.*;
import static frc.robot.Constants.Cascade.*;
import static frc.robot.Constants.TronWheel.*;
import static frc.robot.Constants.Drivetrain.*;

import com.ctre.phoenix.sensors.WPI_Pigeon2;

import static frc.robot.Constants.Intake.*;

public class RobotContainer {
  SendableChooser<Command> autoChooser_m = new SendableChooser<>();
  public static CommandXboxController driver_s;
  public static CommandXboxController operator_s;
  public static XboxController driverRumble_s;
  public static XboxController operatorRumble_s;

  public static Drivetrain drivetrainInst_s;
  public static Cascade cascadeInst_s;
  public static TronWheel tronWheelInst_s;
  public static Intake intakeInst_s;

  public static WPI_Pigeon2 pidgey_s;
  public static PowerDistribution pdb_s;

  public RobotContainer() {
    drivetrainInst_s = Drivetrain.getInstance();
    drivetrainInst_s.setDefaultCommand(new T_Drivetrain_ArcadeDrive());
    cascadeInst_s = Cascade.getInstance();
    cascadeInst_s.setDefaultCommand(new T_Cascade_Move());
    tronWheelInst_s = TronWheel.getInstance();
    tronWheelInst_s.setDefaultCommand(new T_TronWheel_Move());
    intakeInst_s = Intake.getInstance();
    intakeInst_s.setDefaultCommand(new T_Intake_In());
    pdb_s = new PowerDistribution();
    pidgey_s = new WPI_Pigeon2(CAN_PIDGEY, CAN_BUS_NAME);
    
    autoChooser_m.setDefaultOption("Preload Balance", new G_PreloadBalance());
    autoChooser_m.addOption("Preload", new G_Preload());
    autoChooser_m.addOption("Leave Comm, Bal", new G_PreloadLeave());
    autoChooser_m.addOption("Blue LeftGrid 2 Bal", new G_BlueLeftGrid());
    autoChooser_m.addOption("Blue RightGrid 1.5 Bal", new G_BlueRightGrid1Half());
    autoChooser_m.addOption("Red LeftGrid 1.5 Bal", new G_RedLeftGrid1Half());
    SmartDashboard.putData("Auto Chooser", autoChooser_m);

    driver_s = new CommandXboxController(PORT_DRIVER);
    operator_s = new CommandXboxController(PORT_OPERATOR);

    driverRumble_s = new XboxController(PORT_DRIVER);
    operatorRumble_s = new XboxController(PORT_OPERATOR);

    configureBindings();
  }

  /**
   * Driver/Operator controls
   */
  private void configureBindings() {
    // ! Driver Controls
    driver_s.rightTrigger().onTrue(Commands.runOnce(() -> intakeInst_s.setIntaking(!intakeInst_s.getIntaking())));
    driver_s.leftTrigger(0.5).whileTrue(new T_Intake_Out(VAL_OUT_NORMAL));
    driver_s.start().whileTrue(new T_Intake_Out(VAL_OUT_TURBO));
    driver_s.x().onTrue(Commands.parallel(new T_Cascade_Preset(VAL_TRANSPORT_PRESET), new T_TronWheel_Preset(VAL_ANGLE_HYBRID)));
    driver_s.y().onTrue(Commands.parallel(new T_Cascade_Preset(VAL_LINEUP_PRESET), new T_TronWheel_Preset(VAL_ANGLE_SCORE)));

    // ! Operator Controls
    operator_s.back()
        .onTrue(Commands.parallel(new T_TronWheel_Home().withInterruptBehavior(InterruptionBehavior.kCancelIncoming),
            new T_Cascade_Home().withInterruptBehavior(InterruptionBehavior.kCancelIncoming)));

    new ComboButton(operator_s.start(), operator_s.a())
        .whenShiftPressed(new T_Subsystem_Manual(tronWheelInst_s))
        .whenPressed(Commands.parallel(new T_Cascade_Preset(VAL_MID_PRESET), new T_TronWheel_Preset(VAL_ANGLE_SCORE)));

    new ComboButton(operator_s.start(), operator_s.b())
        .whenShiftPressed(new T_Subsystem_Manual(cascadeInst_s))
        .whenPressed(Commands.parallel(new T_Cascade_Preset(VAL_HIGH_PRESET), new T_TronWheel_Preset(VAL_ANGLE_SCORE)));

    new ComboButton(operator_s.start(), operator_s.x())
      .whenShiftPressed(Commands.parallel(new T_Cascade_Preset(VAL_RAMP_PRESET), new T_TronWheel_Preset(VAL_ANGLE_RAMP)))
      .whenPressed(
          Commands.parallel(new T_Cascade_Preset(VAL_GROUND_PRESET), new T_TronWheel_Preset(VAL_ANGLE_GROUND_INIT)));

    operator_s.y().onTrue(
        Commands.parallel(new T_TronWheel_Preset(VAL_ANGLE_TRANSPORT), new T_Cascade_Preset(VAL_TRANSPORT_PRESET)));

    operator_s.rightBumper().onTrue(
        Commands.parallel(new T_Cascade_Preset(VAL_LINEUP_PRESET),
            Commands.runOnce(() -> intakeInst_s.setIntaking(true))));
    operator_s.leftBumper().onTrue(Commands.runOnce(() -> pdb_s.setSwitchableChannel(!pdb_s.getSwitchableChannel())));
    operator_s.rightTrigger().onTrue(Commands.runOnce(() -> intakeInst_s.setIntaking(!intakeInst_s.getIntaking())));
  }

  public Command getAutonomousCommand() {
    return autoChooser_m.getSelected();
  }
}
