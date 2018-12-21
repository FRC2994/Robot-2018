package frc.robot;

import static frc.utils.Constants.*;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import frc.controls.ButtonEntry;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.utils.Constants;
//import frc.utils.SimPID;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;

public class Elevator extends Subsystem {
	private static Elevator instance;
	public Encoder encoder;
	TalonSRX motor = new TalonSRX(Constants.getConstantAsInt(CAN_ELEVATOR));
	private int startPosition = 0;
	private int positionInTicks = 0;
	private boolean printedZeroing;
	public DigitalInput limitElevatorBottom = new DigitalInput(Constants.getConstantAsInt(DIO_ELEVATOR_LIMIT_BOTTOM));
//    private DigitalInput limitElevatorTop = new DigitalInput(getConstantAsInt(DIO_ELEVATOR_LIMIT_TOP));
    
	public Elevator() {
		/* choose the sensor and sensor direction */
		instance = this;
		motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		motor.setSensorPhase(false);
		motor.selectProfileSlot(0, 0);
		startPosition = getRealPosition();
		System.out.println("Elevator start position: " + startPosition);
		/* set the peak and nominal outputs, 12V means full */
		//motor.configNominalOutputForward(0, 0);
		//motor.configNominalOutputReverse(0, 0);
		//motor.configPeakOutputForward(1, 0);
		//motor.configPeakOutputReverse(-1, 0);
		/*
		 * set the allowable closed-loop error, Closed-Loop output will be
		 * neutral within this range. See Table in Section 17.2.1 for native
		 * units per rotation.
		 */
		//motor.configAllowableClosedloopError(0, 0, 0);
		
		motor.setSensorPhase(true);
		motor.setInverted(true);

		setPIDUp();
		
		setPosition(0);
	}

	public static final Elevator getInstance() {
		return instance;
	}

	@Override
	void initTeleop() {
		stopMotor();
//		motor.setSelectedSensorPosition(0, 0, 10);
//		setPosition(getRealPosition());
		Subsystems.controlGamepad.enableButton(Constants.getConstantAsInt(GAMEPAD_ELEVATOR_DOWN));
		Subsystems.controlGamepad.enableButton(Constants.getConstantAsInt(GAMEPAD_ELEVATOR_UP));
		Subsystems.controlGamepad.enableButton(Constants.getConstantAsInt(GAMEPAD_ELEVATOR_FIXED_HEIGHT));
		zero();
	}

	public void setPosition(int positionInTicks) {
//		if (positionInTicks < 50 && positionInTicks < this.positionInTicks) {
//			positionInTicks = 50;
//		}
//		System.out.println("Trying to move elevator in Closed Loop... currentPosition " + getDesiredPosition() 
//			+ " encPos "  + getDesiredPosition() + " positionInTicks " + positionInTicks + ".");
//		System.out.println("New desired: " + (positionInTicks + startPosition));
		motor.set(ControlMode.Position, positionInTicks + startPosition);
		this.positionInTicks = positionInTicks;
	}

	public void setMotorOpenLoop(double percent) {
		System.out.println("Trying to move elevator in Open Loop... currentPosition " + getDesiredPosition() 
			+ " encPos "  + getDesiredPosition() + " percent " + percent + ".");
		motor.set(ControlMode.PercentOutput, percent);
	}

	public int getDesiredPosition() {
		return positionInTicks;
	}
	
	public int getRealPosition() {
		return motor.getSelectedSensorPosition(0) - startPosition;
	}
//
//	public void resetEncoders() {
//		motor.setSelectedSensorPosition(0,0,10);
//		if (getDesiredPosition() != 0) {
//			System.out.println("ERROR - Could not reset Elevator encoder!! - encPos " + getDesiredPosition() + ".");
//		}
//		//TODO: Reverse encoders on talons - how do you do this
//	}

	@Override
	void tickTeleop() {
//		System.out.println("ELEVATOR : getDesiredPosition() " + getDesiredPosition() + " getRealPosition() " + getRealPosition() + " Encoder Position " + motor.getSelectedSensorPosition(0));
//		System.out.println("Voltage: " + motor.getMotorOutputVoltage() + " CL error: " + motor.getClosedLoopError(0) + " P: " + motor.configGetParameter(ParamEnum.eProfileParamSlot_P, 0, 10));
		if (Subsystems.controlGamepad.getState(Constants.getConstantAsInt(GAMEPAD_ELEVATOR_UP)) == ButtonEntry.STATE_CLOSED) {
	        moveUp();
		} else if (Subsystems.controlGamepad.getEvent(Constants.getConstantAsInt(GAMEPAD_ELEVATOR_UP)) == ButtonEntry.EVENT_OPENED) {
			stopMotor();
		}
		if (Subsystems.controlGamepad.getState(Constants.getConstantAsInt(GAMEPAD_ELEVATOR_DOWN)) == ButtonEntry.STATE_CLOSED) {
 			moveDown();
		} else if (Subsystems.controlGamepad.getEvent(Constants.getConstantAsInt(GAMEPAD_ELEVATOR_DOWN)) == ButtonEntry.EVENT_OPENED) {
			stopMotor();
		}
		
		if (Subsystems.controlGamepad.getEvent(Constants.getConstantAsInt(GAMEPAD_ELEVATOR_FIXED_HEIGHT)) == ButtonEntry.EVENT_CLOSED) {
			setPosition(370000);
		}

		if (!limitElevatorBottom.get()) {
			stopMotor();
		}
	}

	public void stopMotor() {
//    	setPosition(getDesiredPosition() + Constants.ELEVATOR_POSITION_INCREMENT);
//    	setMotorOpenLoop(0);
	}

	public void setPIDUp() {
		/* set closed loop gains in slot0, typically kF stays zero. */
		motor.config_kF(0, 0.0, 0);
		motor.config_kP(0, 0.2, 0);
		motor.config_kI(0, 0.0, 0);
		motor.config_kD(0, 0.0, 0);	
		motor.configPeakOutputForward(1.0, 0);
	}
	

	public void setPIDDown() {
		/* set closed loop gains in slot0, typically kF stays zero. */
		motor.config_kF(0, 0.0, 0);
		motor.config_kP(0, 0.1, 0);
		motor.config_kI(0, 0.0, 0);
		motor.config_kD(0, 0.0, 0);
		motor.configPeakOutputReverse(-0.5, 0);
	}
	
	public void moveUp() {
//		if ( getDesiredPosition() + Constants.ELEVATOR_POSITION_INCREMENT <  Constants.ELEVATOR_POSITION_MAXIMUM)
//		if ( ! limitElevatorTop.get()) {
//			int limittedVal = getDesiredPosition() + Constants.ELEVATOR_POSITION_INCREMENT;
   		    System.out.println("Elevator Moving Up to " + getDesiredPosition() + Constants.ELEVATOR_POSITION_INCREMENT );
		    System.out.println("ELEVATOR : getDesiredPosition() " + getDesiredPosition() + " getRealPosition() " + getRealPosition() + " Encoder Position " + motor.getSelectedSensorPosition(0));
			System.out.println("Voltage: " + motor.getMotorOutputVoltage() + " CL error: " + motor.getClosedLoopError(0) + " P: " + motor.configGetParameter(ParamEnum.eProfileParamSlot_P, 0, 10));
    		setPIDUp();
			setPosition(getRealPosition() + Constants.ELEVATOR_POSITION_INCREMENT);
			System.out.println("Moving UP getDesiredPosition() " + getDesiredPosition() + " getRealPosition() " + getRealPosition() + " Encoder Position " + motor.getSelectedSensorPosition(0));
//    		setMotorOpenLoop(-0.8);
//		}
	}
	
	public void zero() {
		if (!limitElevatorBottom.get()) {
            if (!printedZeroing) {
    			System.out.println("Elevator Zeroing!! Old startPosition " + startPosition + " New startPosition " +  getRealPosition());
    			printedZeroing = true;
            }
    		startPosition = getRealPosition();
			setPosition(0);
		}
		else {
			printedZeroing = false;
			setPosition(getRealPosition());
		}
	}

	public void moveDown() {
      if ( limitElevatorBottom.get()) {
			System.out.println("Elevator Moving Down to " + getDesiredPosition() + Constants.ELEVATOR_POSITION_DECREMENT );
			System.out.println("ELEVATOR : getDesiredPosition() " + getDesiredPosition() + " getRealPosition() " + getRealPosition() + " Encoder Position " + motor.getSelectedSensorPosition(0));
			System.out.println("Voltage: " + motor.getMotorOutputVoltage() + " CL error: " + motor.getClosedLoopError(0) + " P: " + motor.configGetParameter(ParamEnum.eProfileParamSlot_P, 0, 10));
			System.out.println("getErrorDerivative: " + motor.getErrorDerivative(0) );
			setPIDDown();
			setPosition(getRealPosition() + Constants.ELEVATOR_POSITION_DECREMENT);
      } else {
			System.out.println("Elevator Zeroing!!");
			startPosition = getRealPosition();
			setPosition(0);
      }
	}

	public int limitValue(int value) {
		if ( (value + getDesiredPosition()) < (startPosition + Constants.ELEVATOR_POSITION_MINIMUM)) {
			return startPosition + Constants.ELEVATOR_POSITION_MINIMUM;
		}
		else if ( (value + getDesiredPosition()) > ( startPosition + Constants.ELEVATOR_POSITION_MAXIMUM) ) { 
			return startPosition + Constants.ELEVATOR_POSITION_MAXIMUM;
		}
		else {
			return value;
		}
	}

	@Override
	void tickTesting() {
		System.out.println("Trying to move elevator in Closed Loop... currentPosition " + getDesiredPosition() 
		+ " encPos "  + getDesiredPosition() + " positionInTicks " + positionInTicks + ".");
		zero();
	}

	@Override
	void initTesting() {
		zero();
		motor.set(ControlMode.PercentOutput, 0);
	}

	public boolean talonHappy() {
		int cl_err = motor.getClosedLoopError(0);
		System.out.println("CL_ERR: " + Math.abs(cl_err) + " motorOut: " + motor.getMotorOutputPercent()
		+ " Enc: " + motor.getSelectedSensorPosition(0) + " startPosition " + startPosition + " getDesiredPosition " + getDesiredPosition());
		return motor.getClosedLoopError(0) < 2000;
	}
}
