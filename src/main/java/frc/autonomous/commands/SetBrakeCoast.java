package main.java.frc.autonomous.commands;

import main.java.frc.autonomous.AutoCommand;
import main.java.frc.robot.DriveTrain;

public class SetBrakeCoast implements AutoCommand {
	private DriveTrain.BrakeCoastStatus status;

	public SetBrakeCoast(DriveTrain.BrakeCoastStatus status) {
		this.status = status;
	}
	
	@Override
	public void initialize() {
		DriveTrain.getInstance().setBrakeCoast(status);
	}

	@Override
	public boolean tick() {
		return false;
	}

	@Override
	public void cleanup() {
	}

}
