package frc.robot.util;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * A trigger that checks if both these buttons are pressed.
 * Schedules 2 commands, one for with shift press and one for without shift press
 */
public class ComboButton {
  private Trigger button_m;
  private static Trigger shift_s;

  /**
   * Creates a new ComboButton
   * @param shift the button to serve as the shift button
   * @param button the button to be pressed
   */
  public ComboButton(Trigger shift, Trigger button) {
    button_m = button;
    shift_s = shift;
  }
  
  /**
   * When the button is pressed and shift is not, the command will be executed.
   * @param command The command to execute
   * @return
   */
  public ComboButton whenPressed(final Command command) {
    (shift_s.negate().and(button_m)).onTrue(command);
    return this;
  }

  /**
   * When the button and the shift button is pressed, the command will be executed.
   * @param command The command to execute
   * @return
   */
  public ComboButton whenShiftPressed(final Command command) {
    (shift_s.and(button_m)).onTrue(command);
    return this;
  }

  /**
   * While the button is held and shift is not, the command will be executed
   * @param command The command to execute
   * @return
   */
  public ComboButton whilePressed(final Command command){
    (shift_s.negate().and(button_m)).whileTrue(command);
    return this;
  }
}
