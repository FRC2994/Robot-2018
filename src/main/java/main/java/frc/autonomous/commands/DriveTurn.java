package main.java.frc.autonomous.commands;

import main.java.frc.autonomous.AutoCommand;
import main.java.frc.utils.SimLib;
import main.java.frc.utils.SimPID;
import main.java.frc.robot.DriveTrain;
//import main.java.frc.robot.Subsystems;

public class DriveTurn implements AutoCommand {

	private final int angle;
	private SimPID gyroPID;
	private DriveTrain driveTrain = DriveTrain.getInstance();
	
	public DriveTurn(int angle) {
		this.angle = angle;
	}
	
	@Override
	public void initialize() {
		driveTrain.reset();
		gyroPID = driveTrain.getTurnPID();
		gyroPID.setDesiredValue(angle);
		System.out.println("DriveTurn Init : angle " + angle + ".");
	}
	
	@Override
	public boolean tick() {
		if (!gyroPID.isDone()) {
			double driveVal = gyroPID.calcPID(-driveTrain.getHeading());
			System.out.println("Gyro: " + driveTrain.getHeading() + "L: " + driveVal);
			double limitVal = SimLib.limitValue(driveVal, 1);
			driveTrain.setMotors(limitVal, -limitVal);
			return true;
		}
		return false;
	}

	@Override
	public void cleanup() {
		System.out.println("DriveTurn Cleanup");
		driveTrain.setMotors(0.0, 0.0);
	}
	
}
