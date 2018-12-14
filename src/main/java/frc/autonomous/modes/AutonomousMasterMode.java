package main.java.frc.autonomous.modes;

//import main.java.frc.utils.Constants;
//import main.java.frc.utils.Constants.*;
//import main.java.frc.robot.Robot;
//import main.java.frc.robot.Subsystems;
//import main.java.frc.autonomous.AutoBuilder;
import main.java.frc.autonomous.AutoCommand;
import main.java.frc.autonomous.AutoMode;
//import main.java.frc.autonomous.commands.DriveStraightTalon;
//import main.java.frc.autonomous.modes.CentreAndPickSwitchMode;
//import main.java.frc.autonomous.modes.SideToScaleMode;
//import main.java.frc.autonomous.modes.DoNothingMode;
//import main.java.frc.autonomous.modes.CrossTheLineMode;
//import main.java.frc.autonomous.modes.JackTestMode;

public class AutonomousMasterMode extends AutoMode {
    private static AutoMode mode;

	public AutonomousMasterMode() {
	}
	
	@Override
	protected AutoCommand[] initializeCommands() {
		mode.initialize();
		return null;
//		return mode.initializeCommands();  // TODO: Is this ok?
	};
}

