package main.java.frc.autonomous.modes;

import main.java.frc.autonomous.AutoBuilder;
import main.java.frc.autonomous.AutoCommand;
import main.java.frc.autonomous.AutoMode;
import main.java.frc.autonomous.commands.FollowMotionProfile;
import main.java.frc.autonomous.commands.DriveStraight;
import main.java.frc.autonomous.commands.DriveTurn;
import main.java.frc.autonomous.commands.Wait;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

//import java.io.File;
//import java.io.FileWriter;


public class FollowMotionProfileMode extends AutoMode {
	//TODO: Test this on practice field. These are bogus values.

	@Override
	protected AutoCommand[] initializeCommands() {
		// This assumes that we start in the staging zone parallel to the closest scoring platform
		AutoBuilder builder = new AutoBuilder();

		// Waypoint[] points = new Waypoint[] {
        //         new Waypoint(1, 13.5, Pathfinder.d2r(0)),
        //     	new Waypoint(11, 18, 0)
        //     };
		// builder.add(new FollowMotionProfile(points));
		
        //File myFile = new File("myfile.csv");
		//builder.add(new FollowMotionProfile(myFile));

		return builder.toArray();
	}
}
