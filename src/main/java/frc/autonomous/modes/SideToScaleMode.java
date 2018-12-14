package main.java.frc.autonomous.modes;

import main.java.frc.autonomous.AutoBuilder;
import main.java.frc.autonomous.AutoCommand;
import main.java.frc.autonomous.AutoMode;
//import main.java.frc.autonomous.commands.CubeDrop;
import main.java.frc.autonomous.commands.CubeTilt;
import main.java.frc.autonomous.commands.DriveStraightTalon;
import main.java.frc.autonomous.commands.DriveTurn;
import main.java.frc.autonomous.commands.ElevatorLift;
import main.java.frc.autonomous.commands.KillOffBlockingTalon;
import main.java.frc.autonomous.commands.RunIntake;
import main.java.frc.autonomous.commands.Wait;
//import main.java.frc.robot.CubePickup;
//import main.java.frc.robot.DriveTrain;
//import main.java.frc.robot.DriveTrain;
import main.java.frc.robot.Robot;
import main.java.frc.robot.CubePickup.IntakeStatus;
//import edu.wpi.first.wpilibj.DriverStation;

public class SideToScaleMode extends AutoMode {
//	private DriveTrain driveTrain = DriveTrain.getInstance();

	private char startPosition;
	private boolean sameSideOnly = false;
    public SideToScaleMode() {
        this.startPosition = 'R';
    }
    public SideToScaleMode(char startPosition, boolean sameSideOnly) {
    	this.startPosition = startPosition;
    	this.sameSideOnly = sameSideOnly;
    }
    
	@Override
	protected AutoCommand[] initializeCommands() {
        String data = Robot.getInstance().getGameSpecificData();
		char scalePosition = data.charAt(1);
		if (!(scalePosition == 'L' || scalePosition == 'R')) {
			System.out.println("scalePosition is not 'L' nor 'R'; " + scalePosition);
		}
        
        AutoBuilder builder = new AutoBuilder();

		if (scalePosition == 'L' && startPosition == 'L') {
			builder.add(new DriveTurn(5));  // Compensate for drift. Less than 5 is not stable
			System.out.println("Selected left scale mode friendy side");
			builder.add(new DriveStraightTalon(52000, true, DriveStraightTalon.DriveProfile.FAST));
			builder.add(new DriveTurn(-40));
			builder.add(new ElevatorLift(420000,true));
			builder.add(new DriveStraightTalon(1200));
			builder.add(new CubeTilt('m'));
			builder.add(new Wait(0.5));
			builder.add(new RunIntake(IntakeStatus.OUT));
			builder.add(new Wait(2));
			builder.add(new RunIntake(IntakeStatus.STOP));
			builder.add(new Wait(3)); // Wait for referee to see
			builder.add(new DriveStraightTalon(-1200));
			builder.add(new ElevatorLift(0));
		}
		else if (scalePosition == 'R' && startPosition == 'R') {
			System.out.println("Selected right scale mode friendy side");
//			builder.add(new DriveTurn(-5));  // Compensate for drift. Less than 5 is not stable
			builder.add(new DriveStraightTalon(52000, true, DriveStraightTalon.DriveProfile.FAST));
			builder.add(new DriveTurn(40));
			builder.add(new ElevatorLift(420000,true));
			builder.add(new DriveStraightTalon(1200));
			builder.add(new CubeTilt('m'));
			builder.add(new Wait(0.5));
			builder.add(new RunIntake(IntakeStatus.OUT));
			builder.add(new Wait(1));
			builder.add(new RunIntake(IntakeStatus.STOP));
			builder.add(new Wait(3)); // Wait for referee to see
			builder.add(new DriveStraightTalon(-1200));
			builder.add(new ElevatorLift(0));
		}
		else if (scalePosition == 'R' && startPosition == 'L' && !sameSideOnly) {
			System.out.println("Selected right scale mode opposite side");
//			builder.add(new DriveTurn(-5));  // Compensate for drift. Less than 5 is not stable
			builder.add(new DriveStraightTalon(42000, true, DriveStraightTalon.DriveProfile.TURBO));
			builder.add(new DriveTurn(-95));
			builder.add(new DriveStraightTalon(39000));
			builder.add(new DriveTurn(85));
			builder.add(new DriveStraightTalon(3800));
			builder.add(new ElevatorLift(420000,true));
			builder.add(new DriveStraightTalon(3800,true));
			builder.add(new CubeTilt('m'));
			builder.add(new Wait(0.5));
			builder.add(new RunIntake(IntakeStatus.OUT));
			builder.add(new Wait(2));
			builder.add(new RunIntake(IntakeStatus.STOP));
			builder.add(new Wait(3)); // Wait for referee to see
			builder.add(new KillOffBlockingTalon());
			builder.add(new DriveStraightTalon(-10000));
			builder.add(new ElevatorLift(0));
		}
		else if (scalePosition == 'L' && startPosition == 'R'  && !sameSideOnly) {
			System.out.println("Selected left scale mode opposite side");
//			builder.add(new DriveTurn(-5));  // Compensate for drift. Less than 5 is not stable
			builder.add(new DriveStraightTalon(40500, true, DriveStraightTalon.DriveProfile.TURBO));
			builder.add(new Wait(0.5));
			builder.add(new DriveTurn(85));
			builder.add(new DriveStraightTalon(39000));
			builder.add(new DriveTurn(-85));
			builder.add(new DriveStraightTalon(3500));
			builder.add(new ElevatorLift(420000,true));
			builder.add(new DriveStraightTalon(3500,true));
			builder.add(new CubeTilt('m'));
			builder.add(new Wait(0.5));
			builder.add(new RunIntake(IntakeStatus.OUT));
			builder.add(new Wait(3)); // Wait for referee to see
			builder.add(new RunIntake(IntakeStatus.STOP));
			builder.add(new KillOffBlockingTalon());
			builder.add(new DriveStraightTalon(-3000));
			builder.add(new ElevatorLift(0));
		}
		else {
			// Shouldn't get here, but if we cannot determine which 
			// switch, then just drive forward a given amount of ticks
			// so we at least go over the line
			System.out.println("ERROR: Invalid spec: " + data);
			builder.add(new DriveStraightTalon(25000));
		}
		
		return builder.toArray();
	}

}
