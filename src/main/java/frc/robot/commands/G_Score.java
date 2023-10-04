package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.states.CascadeState;
import frc.robot.states.TronWheelState;

public class G_Score extends SequentialCommandGroup {

    /**
     * @param coneMode
     * @param cascadeState
     * @param tronWheelState
     * @param timeout
     */    
    public G_Score(boolean coneMode, CascadeState cascadeState, TronWheelState tronWheelState, int timeout) {
            addCommands(
                    new A_Cascade_Move(cascadeState, coneMode, timeout)
                    .alongWith(new A_TronWheel_Move(tronWheelState, coneMode, timeout)
                    .alongWith(new A_Intake_In(coneMode ? timeout : 0))),
                    new WaitCommand(0.1),
                    new A_Intake_Out(coneMode)
            );
    }
}
