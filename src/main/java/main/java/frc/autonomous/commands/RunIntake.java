package main.java.frc.autonomous.commands;

import main.java.frc.autonomous.AutoCommand;
import main.java.frc.robot.CubePickup;
import main.java.frc.robot.CubePickup.IntakeStatus;

public class RunIntake implements AutoCommand {
	private IntakeStatus status;
	
	public RunIntake(IntakeStatus status) {
		this.status = status;
	}
	
	@Override
	public void initialize() {
		CubePickup pickup = CubePickup.getInstance();

		if (status == IntakeStatus.IN) {
			pickup.pickupInFast();
		}
		else {
			pickup.setIntakeStatus(status);
		}
		System.out.println("Running intake.");
	}

	@Override
	public boolean tick() {
		return false;
	}

	@Override
	public void cleanup() { }
}
