package frc.robot.util;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import static frc.robot.Constants.XBoxConstants.*;

public class ComboButton {
  private XboxController joystick_m;
  private JoystickButton button_m;
  private static JoystickButton shift_s;

  public ComboButton(XboxController controller, int button) {
    joystick_m = controller;
    button_m = new JoystickButton(joystick_m, button);
    shift_s = new JoystickButton(joystick_m, BTN_START);
  }
  
  /**
   * When the button is pressed, the command will be executed. If the button is held, the command will be executed repeatedly.
   * @param command The command to execute
   * @return
   */
  public ComboButton whenPressed(final Command command) {
    (shift_s.negate().and(button_m)).onTrue(command);
    return this;
  }

  /**
   * When the button and the start button is pressed, the command will be executed. If the button is held, the command will be executed repeatedly.
   * @param command The command to execute
   * @return
   */
  public ComboButton whenShiftPressed(final Command command) {
    (shift_s.and(button_m)).onTrue(command);
    return this;
  }
}