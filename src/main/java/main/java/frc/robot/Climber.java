package main.java.frc.robot;

import static main.java.frc.utils.Constants.*;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.ControlMode;
//import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import main.java.frc.controls.ButtonEntry;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import main.java.frc.utils.Constants;
//import main.java.frc.utils.SimPID;
//import edu.wpi.first.wpilibj.Encoder;

public class Climber extends Subsystem {
	private static Climber instance;
	private Elevator elevator = Elevator.getInstance();
	TalonSRX motor = new TalonSRX(Constants.getConstantAsInt(CAN_CLIMBER));
    
	public Climber() {
		instance = this;
		motor.setSensorPhase(false);
		motor.selectProfileSlot(0, 0);
		
		motor.setSensorPhase(true);
		motor.setInverted(true);
	}

	public static final Climber getInstance() {
		return instance;
	}

	@Override
	void initTeleop() {
		stopMotor();
		motor.setSelectedSensorPosition(0, 0, 10);
		Subsystems.controlGamepad.enableButton(Constants.getConstantAsInt(GAMEPAD_CLIMBER_DOWN));
		Subsystems.controlGamepad.enableButton(Constants.getConstantAsInt(GAMEPAD_CLIMBER_UP));
	}

	public void setMotorOpenLoop(double percent) {
		System.out.println("Trying to climb  in Open Loop... percentVoltage " + percent + ".");
		motor.set(ControlMode.PercentOutput, percent);
	}

	@Override
	void tickTeleop() {
		if (Subsystems.controlGamepad.getState(Constants.getConstantAsInt(GAMEPAD_CLIMBER_UP)) == ButtonEntry.STATE_CLOSED) {
	        double percent = Subsystems.controlGamepad.getRawAxis(Constants.getConstantAsInt(GAMEPAD_CLIMBER_THROTTLE));
			System.out.println("CLIMBER percent " + percent + " voltage: " + motor.getMotorOutputVoltage());
	        climbUp(percent);
//	        climbUp();
		} else if (Subsystems.controlGamepad.getEvent(Constants.getConstantAsInt(GAMEPAD_CLIMBER_UP)) == ButtonEntry.EVENT_OPENED) {
			stopMotor();
		}
		if (Subsystems.controlGamepad.getState(Constants.getConstantAsInt(GAMEPAD_CLIMBER_DOWN)) == ButtonEntry.STATE_CLOSED) {
 			climbDown();
		} else if (Subsystems.controlGamepad.getEvent(Constants.getConstantAsInt(GAMEPAD_CLIMBER_DOWN)) == ButtonEntry.EVENT_OPENED) {
			stopMotor();
		}
		
	}

	public void stopMotor() {
    	setMotorOpenLoop(0);
	}

	public void climbUp() {
		if (elevator.limitElevatorBottom.get()) {
            double percent = -0.8;
        	setMotorOpenLoop(percent);
			System.out.println("CLIMBER percent " + percent + " voltage: " + motor.getMotorOutputVoltage());
		} else {
        	setMotorOpenLoop(0);
	    }
	}
	
	public void climbUp(double percent) {
		if (elevator.limitElevatorBottom.get()) {
        	setMotorOpenLoop(percent);
			System.out.println("CLIMBER percent " + percent + " voltage: " + motor.getMotorOutputVoltage());
		} else {
        	setMotorOpenLoop(0);
		}
	}
	
	public void climbDown() {
      	setMotorOpenLoop(0.4);
	}

	@Override
	void tickTesting() {
	}

	@Override
	void initTesting() {
		motor.set(ControlMode.PercentOutput, 0);
	}

	public boolean talonHappy() {
		// TODO Auto-generated method stub
		return motor.getClosedLoopError(0) < 500;
	}
}
