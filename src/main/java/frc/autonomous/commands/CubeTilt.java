package frc.autonomous.commands;

import frc.autonomous.AutoCommand;
//import frc.utils.Constants;
import frc.robot.CubePickup;

public class CubeTilt implements AutoCommand {
	private char height = 'd';
	
	public CubeTilt(char height) {
		this.height = height;
	}
	
	@Override
	public void initialize() {
		CubePickup pickup = CubePickup.getInstance();
		if (height == 'd') {
			pickup.tiltPickupDown();
		} else if (height == 'u') {
			pickup.tiltPickupUp();
		} else if (height == 'm') {
			pickup.tiltPickupMid();
		} else {
			System.out.println("Cub to be 'd', 'u' or 'm', got :" + height);
		}
	}

	@Override
	public boolean tick() {
		return false;
	}

	@Override
	public void cleanup() { }
}
