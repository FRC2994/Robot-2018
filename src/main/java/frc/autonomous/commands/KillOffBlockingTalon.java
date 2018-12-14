package main.java.frc.autonomous.commands;

import main.java.frc.autonomous.*;
import main.java.frc.robot.DriveTrain;

public class KillOffBlockingTalon implements AutoCommand {
	@Override
	public void initialize() {
		DriveTrain.getInstance().stopTalonPID();
	}

	@Override
	public boolean tick() {
		return false;
	}

	@Override
	public void cleanup() {
		
	}

}
