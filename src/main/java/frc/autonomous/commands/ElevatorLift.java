package frc.autonomous.commands;

import frc.robot.Elevator;
import frc.autonomous.AutoCommand;

public final class ElevatorLift implements AutoCommand {

	private int elevatorHeight;
	private Elevator elevator; 
	private boolean blocking;
	private int talonsHappyCount;

	public ElevatorLift(int height, boolean blocking) {
		//Get a height from the called program
		elevatorHeight = height;
		elevator = Elevator.getInstance();
		this.blocking = blocking;
	}
	
	public ElevatorLift(int height) {
		this(height, false);
	}

	@Override
	public void initialize() {
		System.out.println("Elevator height: " + elevatorHeight);
		elevator.setPosition(elevatorHeight);
	}

	@Override
	public boolean tick() {
		System.out.println("Elevator height: " + elevator.getRealPosition());
		if (elevator.talonHappy()) {
	        talonsHappyCount++;
		} else {
	        talonsHappyCount = 0;
		}
        boolean isNotDone = blocking && (talonsHappyCount < 5);
        if (isNotDone) {
            System.out.println("Elevator is not done; blocking " + blocking + " elevator.talonHappy() " + elevator.talonHappy() + " talonsHappyCount " + talonsHappyCount);
        } else {
            System.out.println("Elevator IS DONE!!!; blocking " + blocking + " elevator.talonHappy() " + elevator.talonHappy() + " talonsHappyCount " + talonsHappyCount);
        }
        return isNotDone;
	}

	@Override
	public void cleanup() {
		System.out.println("ELEVATOR Cleanup.  Encoder " + elevator.getRealPosition());
	}

}