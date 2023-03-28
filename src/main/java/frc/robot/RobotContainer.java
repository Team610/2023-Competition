// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.T_Cascade_Home;
import frc.robot.commands.T_Cascade_Move;
import frc.robot.commands.T_Cascade_Preset;
import frc.robot.commands.T_Cone_Position;
import frc.robot.commands.B_LCube_2;
import frc.robot.commands.G_Preload;
import frc.robot.commands.G_PreloadBalance;
import frc.robot.commands.RB_CCone_1_Bal;
import frc.robot.commands.RB_CCube_1_Bal;
import frc.robot.commands.R_LConeL_1half_Bal;
import frc.robot.commands.R_LCube_1half_Bal;
import frc.robot.commands.R_LCube_2;
import frc.robot.commands.B_RConeR_1half_Bal;
import frc.robot.commands.B_RCube_1half_Bal;
import frc.robot.commands.B_RCube_2;
import frc.robot.commands.T_Drivetrain_ArcadeDrive;
import frc.robot.subsystems.Cascade;
import frc.robot.commands.T_Intake_In;
import frc.robot.commands.T_Intake_Out;
import frc.robot.commands.T_Subsystem_Manual;
import frc.robot.commands.T_TronWheel_Home;
import frc.robot.commands.T_TronWheel_Move;
import frc.robot.commands.T_TronWheel_Preset;
import frc.robot.commands.T_Vision_Light;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.TronWheel;
import frc.robot.subsystems.Vision;
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
import frc.robot.states.CascadeState;
import frc.robot.states.TronWheelState;
import static frc.robot.Constants.Drivetrain.*;

import com.ctre.phoenix.sensors.WPI_Pigeon2;

import static frc.robot.Constants.Intake.*;

public class RobotContainer {
  SendableChooser<Command> autoChooser_m = new SendableChooser<>();
  public static CommandXboxController driver_s;
  public static CommandXboxController operator_s;
  public static XboxController driverRumble_s;
  public static XboxController operatorRumble_s;
  
  public static boolean driverDPadLeft_s, driverDPadRight_s;

  public static Drivetrain drivetrainInst_s;
  public static Cascade cascadeInst_s;
  public static TronWheel tronWheelInst_s;
  public static Intake intakeInst_s;
  public static Vision visionInst_s;

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
    visionInst_s = Vision.getInstance();
    pdb_s = new PowerDistribution();
    pidgey_s = new WPI_Pigeon2(CAN_PIDGEY, CAN_BUS_NAME);
    
    // autoChooser_m.setDefaultOption("Preload Balance", new G_PreloadBalance());
    // autoChooser_m.addOption("Preload", new G_Preload());
    autoChooser_m.setDefaultOption("RB CCube 1 Bal", new RB_CCube_1_Bal());
    autoChooser_m.addOption("RB CCone 1 Bal", new RB_CCone_1_Bal());
    autoChooser_m.addOption("B LCube 2", new B_LCube_2());
    autoChooser_m.addOption("B RConeR 1.5 Bal", new B_RConeR_1half_Bal());
    autoChooser_m.addOption("B RCube 1.5 Bal", new B_RCube_1half_Bal());
    autoChooser_m.addOption("B RCube 2", new B_RCube_2());
    autoChooser_m.addOption("R LConeL 1.5 Bal", new R_LConeL_1half_Bal());
    autoChooser_m.addOption("R LCube 1.5 Bal", new R_LCube_1half_Bal());
    autoChooser_m.addOption("R LCube 2", new R_LCube_2());
    SmartDashboard.putData("Auto Chooser", autoChooser_m);

    driver_s = new CommandXboxController(PORT_DRIVER);
    operator_s = new CommandXboxController(PORT_OPERATOR);

    driverRumble_s = new XboxController(PORT_DRIVER);
    operatorRumble_s = new XboxController(PORT_OPERATOR);

    driverDPadLeft_s = driverDPadRight_s = false;

    configureBindings();
  }

  /**
   * Driver/Operator controls
   */
  private void configureBindings() {
    // ! Driver Controls

    //intake toggle
    driver_s.rightTrigger().onTrue(Commands.runOnce(() -> intakeInst_s.setIntaking(!intakeInst_s.getIntaking())));
    //outtake hold
    driver_s.leftTrigger(0.5).whileTrue(new T_Intake_Out(VAL_OUT_NORMAL));
    //turbo outtake hold
    driver_s.start().whileTrue(new T_Intake_Out(VAL_OUT_TURBO));
    //reverse hybrid
    driver_s.x().onTrue(Commands.parallel(new T_Cascade_Preset(CascadeState.TRANSPORT), new T_TronWheel_Preset(TronWheelState.HYBRID), 
      Commands.runOnce(() -> intakeInst_s.setIntaking(true))));
    //forward hybrid
    driver_s.rightBumper().onTrue(Commands.parallel(new T_Cascade_Preset(CascadeState.LINEUP), new T_TronWheel_Preset(TronWheelState.SCORE), 
      Commands.runOnce(() -> intakeInst_s.setIntaking(true))));
    //limelight toggle
    driver_s.a().toggleOnTrue(new T_Vision_Light());

    // ! Operator Controls

    //homing
    operator_s.back()
        .onTrue(Commands.parallel(new T_TronWheel_Home().withInterruptBehavior(InterruptionBehavior.kCancelIncoming),
            new T_Cascade_Home().withInterruptBehavior(InterruptionBehavior.kCancelIncoming)));

    new ComboButton(operator_s.start(), operator_s.a())
        //tronwheel manual toggle
        .whenShiftPressed(new T_Subsystem_Manual(tronWheelInst_s))
        //mid scoring preset
        .whenPressed(Commands.parallel(new T_Cascade_Preset(CascadeState.MID), new T_TronWheel_Preset(TronWheelState.SCORE)));

    new ComboButton(operator_s.start(), operator_s.b())
        //cascade manual toggle
        .whenShiftPressed(new T_Subsystem_Manual(cascadeInst_s))
        //high scoring preset
        .whenPressed(Commands.parallel(new T_Cascade_Preset(CascadeState.HIGH), new T_TronWheel_Preset(TronWheelState.SCORE)));

    new ComboButton(operator_s.start(), operator_s.x())
        //ramp pickup preset
        .whenShiftPressed(Commands.parallel(new T_Cascade_Preset(CascadeState.RAMP), new T_TronWheel_Preset(TronWheelState.RAMP)))
        //ground pickup preset
        .whenPressed(Commands.parallel(new T_Cascade_Preset(CascadeState.GROUND), new T_TronWheel_Preset(TronWheelState.GROUND)));
        

    //transport preset
    operator_s.y().onTrue(Commands.parallel(new T_Cascade_Preset(CascadeState.TRANSPORT), 
                          new T_TronWheel_Preset(TronWheelState.TRANSPORT)));

    //lineup preset
    operator_s.rightBumper().onTrue(Commands.parallel(new T_Cascade_Preset(CascadeState.LINEUP), 
                                    Commands.runOnce(() -> intakeInst_s.setIntaking(true))));
    
    //ring light toggle
    operator_s.leftBumper().onTrue(Commands.runOnce(() -> pdb_s.setSwitchableChannel(!pdb_s.getSwitchableChannel())));
    //intake toggle
    operator_s.rightTrigger().onTrue(Commands.runOnce(() -> intakeInst_s.setIntaking(!intakeInst_s.getIntaking())));
    
    // new POVButton(operator_s.getHID(), 0).onTrue(Commands.runOnce(()-> RobotContainer.drivetrainInst_s.setCoast()).ignoringDisable(true));
    // new POVButton(operator_s.getHID(), 180).onTrue(Commands.runOnce(()-> RobotContainer.drivetrainInst_s.setBrake()).ignoringDisable(true));
  }

  public Command getAutonomousCommand() {
    return autoChooser_m.getSelected();
  }
}
