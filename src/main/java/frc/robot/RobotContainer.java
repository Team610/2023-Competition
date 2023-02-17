// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.*;

import frc.robot.commands.T_Arm_Stop;
import frc.robot.subsystems.Arm;
import frc.robot.commands.T_Arm_Run;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;


public class RobotContainer {
  SendableChooser<Command> autoChooser_m = new SendableChooser<>();
  public static CommandXboxController driver_s;
  private static Arm armInst_m;


  public RobotContainer() {
    driver_s = new CommandXboxController(PORT_DRIVER);
    armInst_m = Arm.getInstance();
    armInst_m.setDefaultCommand(new T_Arm_Stop());
    configureBindings();
  }

  private void configureBindings() {
    driver_s.x().onTrue(new T_Arm_Run());
    driver_s.y().onTrue(new T_Arm_Stop());
  }

  public Command getAutonomousCommand() {
    return autoChooser_m.getSelected();
  }
}
