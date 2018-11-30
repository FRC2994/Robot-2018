package main.java.frc.autonomous;

import java.io.File;

import main.java.frc.controls.ButtonEntry;
import main.java.frc.utils.Constants;
import main.java.frc.robot.DriveTrain;
import main.java.frc.robot.Subsystems;

public class CalibrationManager {
	
	private boolean calibrationDone = false;
	
	private static DriveTrain driveTrain = DriveTrain.getInstance();

	public void calibrateInit() {
		driveTrain.reset();
    	
    	Subsystems.driveJoystick.enableButton(Constants.getConstantAsInt(Constants.CALIBRATION_BUTTON));
	}
	
	public void calibrateTick() {
		if(Subsystems.driveJoystick.getEvent(Constants.getConstantAsInt(Constants.JOYSTICK_CALIBRATE))
				!= ButtonEntry.EVENT_CLOSED) {
    		Subsystems.driveJoystick.update();
    		driveTrain.arcadeDrive(Subsystems.driveJoystick);
    	}
		else if(!calibrationDone) {
			driveTrain.setMotors(0, 0);
	    	
	    	int encoderAValue = driveTrain.getLeftEncoderValue();
	    	int encoderBValue = driveTrain.getRightEncoderValue();
	    	
	    	double encoderAConstant = 0;
	    	double encoderBConstant = 0;
	    	
	    	if(encoderAValue != 0) {
	    		encoderAConstant = 5.0 / encoderAValue;
	    	}
	    	
	    	if(encoderBValue != 0) {
	    		encoderBConstant = 5.0 / encoderBValue;
	    	}
	    	
	    	String value = "//Encoder A (Left), Distance Travelled: 5ft, Number of encoder ticks: " + encoderAValue
	    			+ ", Calibration constant: " + encoderAConstant;
	    	System.out.println(value);
			AutoHelper.writeLineToFile(value, new File(Constants.getConstant(Constants.CALIBRATION_FILE_LOC)));
	    	
			value = "//Encoder B (Right), Distance Travelled: 5ft, Number of encoder ticks: " + encoderBValue
	    			+ ", Calibration constant: " + encoderBConstant;
			System.out.println(value);
			AutoHelper.writeLineToFile(value, new File(Constants.getConstant(Constants.CALIBRATION_FILE_LOC)));
	    	
			AutoHelper.writeLineToFile(encoderAConstant + ", " + encoderBConstant, new File(Constants.getConstant(Constants.CALIBRATION_FILE_LOC)));
			
			calibrationDone = true;
		}
	}
}
