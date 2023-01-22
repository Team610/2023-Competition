package frc.robot.util;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import static frc.robot.Constants.XBoxConstants.*;

public class ComboButton {
  private XboxController m_joystick;
  private JoystickButton m_button;
  private static JoystickButton s_shift;

  public ComboButton(XboxController controller, int button) {
    m_joystick = controller;
    m_button = new JoystickButton(m_joystick, button);
    s_shift = new JoystickButton(m_joystick, BTN_START);
  }

  public ComboButton whenPressed(final Command command) {
    (s_shift.negate().and(m_button)).onTrue(command);
    return this;
  }

  public ComboButton whenShiftPressed(final Command command) {
    (s_shift.and(m_button)).onTrue(command);
    return this;
  }
}