package main.java.frc.autonomous.commands;

import main.java.frc.autonomous.AutoCommand;
import main.java.frc.robot.CubePickup;

public class CubeDrop implements AutoCommand {
	private boolean drop;
	
	public CubeDrop(boolean drop) {
		this.drop = drop;
	}
	
	@Override
	public void initialize() {
		CubePickup pickup = CubePickup.getInstance();
		if (drop) {
			pickup.tiltPickupDown();
		} else {
			pickup.tiltPickupUp();
		}
	}

	@Override
	public boolean tick() {
		return false;
	}

	@Override
	public void cleanup() { }
}
