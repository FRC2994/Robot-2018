package main.java.frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Ramp extends Subsystem {

	private VictorSPX motorLeft, motorRight;
	DoubleSolenoid dropper;
	DigitalInput pushButtonLeft = new DigitalInput(2);
	DigitalInput pushButtonRight = new DigitalInput(3);

	public Ramp() {
		// Initialize motors/pneumatics/etc

		// Set the motors to be controller with 0 percent voltage
		motorLeft.set(ControlMode.PercentOutput, 0);
		motorRight.set(ControlMode.PercentOutput, 0);
		dropper = new DoubleSolenoid(0, 0); // Bogus
	}

	@Override
	void initTeleop() {
		dropper.set(Value.kReverse);
	}

	@Override
	void tickTeleop() {

		// Enable and disable the motor
		// if (buttonpressed)
		{
			//Activate the left motor if the button is not pushed, otherwise turn the motor off
			if (pushButtonLeft.get()) {
				motorLeft.set(ControlMode.PercentOutput, 0);// Bogus value
			}
			else {
				motorLeft.set(ControlMode.PercentOutput, 0);
			}

			//Activate the right motor if the button is not pushed, otherwise turn the motor off
			if (pushButtonRight.get()) {
				motorRight.set(ControlMode.PercentOutput, 0);// Bogus value
			}
			else {
				motorRight.set(ControlMode.PercentOutput, 0);
			}

		}

		//Drop the ramp using the solenoid
		// if (buttonpressedSolenoid)
		{
			// Drop the ramp by enabling the Solenoid
			dropper.set(Value.kForward);
		}
		// else
		{
			//Set the Solenoid to reverse, resetting it
			dropper.set(Value.kReverse);
		}

	}

	@Override
	void tickTesting() {
		// TODO Auto-generated method stub
	}

	@Override
	void initTesting() {
		// TODO Auto-generated method stub
	}

}
