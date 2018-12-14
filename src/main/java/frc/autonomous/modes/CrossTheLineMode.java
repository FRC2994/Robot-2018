package main.java.frc.autonomous.modes;

import main.java.frc.autonomous.AutoBuilder;
import main.java.frc.autonomous.AutoCommand;
import main.java.frc.autonomous.AutoMode;
import main.java.frc.autonomous.commands.DriveStraightTalon;

public class CrossTheLineMode extends AutoMode {

	@Override
	protected AutoCommand[] initializeCommands() {
		AutoBuilder builder = new AutoBuilder();
		builder.add(new DriveStraightTalon(25000, true));
//		builder.add(new RunIntake(IntakeStatus.IN));
//		builder.add(new CubeDrop(false));
		return builder.toArray();
	}
}
