package main.java.frc.autonomous.commands;

import main.java.frc.autonomous.AutoCommand;
//import main.java.frc.utils.SimLib;
//import main.java.frc.utils.SimPID;
import main.java.frc.robot.CubePickup;
import main.java.frc.robot.DriveTrain;
//import main.java.frc.robot.Subsystems;

public class PowerUp implements AutoCommand {

	private final int side;
	private DriveTrain driveTrain = DriveTrain.getInstance();
	private CubePickup pickup = CubePickup.getInstance();
	private int tickCnt;
	private int tickArrival = (int)(3*20*0.05);
	private double speed;
//	private double height;
	
	public PowerUp(int side) {
		this.side = side;
	}
	
	@Override
	public void initialize() {
		driveTrain.reset();
		System.out.println("PowerUp Init:" + side);
		tickCnt = 0;
		speed = 0;
	}
	
	@Override
	public boolean tick() {
        if (tickCnt < 20 ) {
           speed += 0.05;
        } else if (tickCnt > tickArrival) {
            speed -= 0.02;        	
        } else if (tickCnt <= tickArrival) {
            speed = 1;        	
        } else {
            speed = 0;
        }
        driveTrain.setMotors(speed, speed);

        if (tickCnt < 20 ) {
//        	height += 0.05;
         } else if (tickCnt > tickArrival) {
//        	 height -= 0.02;
         } else if (tickCnt <= tickArrival) {
//        	 height = 1;
         } else {
 			return true;
         }
//        Subsystems.elevator.moveUp(height);

        if (tickCnt > tickArrival ) {
    		pickup.pickupOut();
        }
        tickCnt++;
        if (tickCnt > tickArrival + 5) {
   		    return true;
        } else {
        	return false;
        }
	}

	@Override
	public void cleanup() {
		System.out.println("PowerUp Cleanup");
		driveTrain.setMotors(0.0, 0.0);
	}
	
}
