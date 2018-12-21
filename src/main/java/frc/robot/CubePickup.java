package frc.robot;

//Was on the pickup class from 2017
import static frc.utils.Constants.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.controls.ButtonEntry;
import frc.utils.Constants;
//import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class CubePickup extends Subsystem {
	private VictorSPX pickupRight;
	private VictorSPX pickupLeft;
	private DoubleSolenoid pickupTilt_1;
	private DoubleSolenoid pickupTilt_2;
	private static CubePickup instance;
	private IntakeStatus intakeStatus = IntakeStatus.STOP;
	private PickupStatus pickupStatus = PickupStatus.UP;

	public enum PickupStatus {
		UP,
		DOWN,
		MID
	}
	
	public enum IntakeStatus {
		STOP,
		OUT,
		IN
	}
	
	public void setPickupStatus(PickupStatus status) {
		switch (status) {
		case UP:
			tiltPickupUp();
			break;
		case DOWN:
			tiltPickupDown();
			break;
		case MID:
			tiltPickupMid();
			break;
		}

		pickupStatus = status;
	}

	public void setIntakeStatus(IntakeStatus status) {
		switch (status) {
		case STOP:
			pickupStop();
			break;
		case OUT:
			pickupOut();
			break;
		case IN:
			pickupIn();
			break;
		}
		
		intakeStatus = status;
	}

	public CubePickup() {
		// Initialize the pickup motors/etc

		Subsystems.driveJoystick.enableButton(Constants.getConstantAsInt(JOYSTICK_PICKUP_DOWN));
		Subsystems.driveJoystick.enableButton(Constants.getConstantAsInt(JOYSTICK_PICKUP_MID));
		Subsystems.driveJoystick.enableButton(Constants.getConstantAsInt(JOYSTICK_PICKUP_UP));
		Subsystems.controlGamepad.enableButton(Constants.getConstantAsInt(GAMEPAD_PICKUP_IN));
		Subsystems.controlGamepad.enableButton(Constants.getConstantAsInt(GAMEPAD_PICKUP_OUT));

		pickupRight = new VictorSPX(Constants.getConstantAsInt(CAN_PICKUP_RIGHT));
		pickupLeft = new VictorSPX(Constants.getConstantAsInt(CAN_PICKUP_LEFT));
		//pushButton = new DigitalInput(getConstantAsInt(DIO_CUBE_PICKUP));
		pickupTilt_1 = new DoubleSolenoid(Constants.getConstantAsInt(PCM_CAN), Constants.getConstantAsInt(SOLENOID_PICKUP_1_CHANNEL1), 
				                          Constants.getConstantAsInt(SOLENOID_PICKUP_1_CHANNEL2));
		pickupTilt_2 = new DoubleSolenoid(Constants.getConstantAsInt(PCM_CAN), Constants.getConstantAsInt(SOLENOID_PICKUP_2_CHANNEL1), 
				                          Constants.getConstantAsInt(SOLENOID_PICKUP_2_CHANNEL2));

		setPickupStatus(pickupStatus);
		setIntakeStatus(intakeStatus);
		pickupStop();		
		instance = this;
	}
	
	public static final CubePickup getInstance() {
		return instance;
	}

	@Override
	void initTeleop() {
		tiltPickupMid();
	}
	
	public void pickupLeftRight(double value) {
		pickupLeft.set(ControlMode.PercentOutput, value);
		pickupRight.set(ControlMode.PercentOutput, value);
	}
	
	public void tiltPickupUp() {
		System.out.println("Trying to tilt pickup UP.");
		pickupTilt_1.set(Value.kReverse);
		pickupTilt_2.set(Value.kReverse);
	}

	public void tiltPickupDown() {
		System.out.println("Trying to tilt pickup DOWN.");
		pickupTilt_1.set(Value.kForward);
		pickupTilt_2.set(Value.kForward);
	}
	

	public void tiltPickupMid() {
		System.out.println("Trying to tilt pickup MID.");
    	pickupTilt_1.set(Value.kForward);
		pickupTilt_2.set(Value.kReverse);
	}

	public void pickupOut() {
		pickupLeftRight(-0.5);
	}

	public void pickupIn() {
		pickupLeftRight(0.5);
	}
	
	public void pickupInFast() {
		pickupLeftRight(0.5);
	}

	public void pickupStop() {
		pickupLeftRight(0);
	}

	@Override
	void tickTeleop() {
		// PICKUP_IN
		if (Subsystems.controlGamepad.getEvent(getConstantAsInt(GAMEPAD_PICKUP_IN)) == ButtonEntry.EVENT_CLOSED)
		{
			pickupIn();
		}
		else if (Subsystems.controlGamepad.getEvent(getConstantAsInt(GAMEPAD_PICKUP_IN)) == ButtonEntry.EVENT_OPENED) {
			pickupStop();
		}
        // PICKUP_OUT
		if (Subsystems.controlGamepad.getEvent(getConstantAsInt(GAMEPAD_PICKUP_OUT)) == ButtonEntry.EVENT_CLOSED) {
			pickupOut();
		}
		else if (Subsystems.controlGamepad.getEvent(getConstantAsInt(GAMEPAD_PICKUP_OUT)) == ButtonEntry.EVENT_OPENED) {
			pickupStop();
		}
        // PICKUP_UP
		if (Subsystems.driveJoystick.getEvent(getConstantAsInt(JOYSTICK_PICKUP_UP)) == ButtonEntry.EVENT_CLOSED) {
			tiltPickupUp();
		}
        // PICKUP_DOWN
		if (Subsystems.driveJoystick.getEvent(getConstantAsInt(JOYSTICK_PICKUP_DOWN)) == ButtonEntry.EVENT_CLOSED) {
			tiltPickupDown();
		}
		// PICKUP_MID
		if (Subsystems.driveJoystick.getEvent(getConstantAsInt(JOYSTICK_PICKUP_MID)) == ButtonEntry.EVENT_CLOSED) {
			tiltPickupMid();
		}
	}
		
	@Override
	void tickTesting() {
	}

	@Override
	void initTesting() {
	}

}
