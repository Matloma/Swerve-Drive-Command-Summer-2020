/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.I2C;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {

    //Can Ports
    public static final int frontLeftMotorCanPort = 0;
    public static final int frontLeftAngleCanPort = 4;
	public static final int frontRightMotorCanPort = 1;
	public static final int frontRightAngleCanPort = 5;
	public static final int backLeftMotorCanPort = 2;
	public static final int backLeftAngleCanPort = 6;
	public static final int backRightMotorCanPort = 3;
    public static final int backRightAngleCanPort = 7;
	public static final int intakeCANPort = 8;
    
	//PWM Ports
	public static final int shooterPWMPort = 0;
	public static final int loaderPWMPort = 1;
	public static final int colorWheelMotorPWMPort = 2;
	public static final int aimingServoPWMPort = 3;

	//Other Ports
	public static final int xboxPort = 0;
	public static final I2C.Port i2cPort = I2C.Port.kOnboard;
	public static final int ultrasonicSensorPort = 0;

	//Motor Speeds
	public static final double throttle = 1;  //Teleop max speed
	public static final double autonomousSpeed = 1;   //autonomous drivetrain max speed
	public static final double intakeMaxSpeed = 1;   //overall Intake Max speed
	public static final double loaderMaxSpeed = 1;   //overall Loader Max speed
	public static final double shooterMaxSpeed = 1;   //overall Shooter Max speed
	public static final double colorWheelMotorMaxSpeed = 0.5;   //overall Color Wheel Motor Ma speed

    //Constants
    public static final double wheelPositionAbsoluteValue = 0.2643787403; //Distance of wheel from center of robot
	public static final double speedGearReduction = 10.33/0.3216990877;  //FOR TESTING PURPOSES --- reduction of drive motors and conversion to meters per second
	public static final double angleGearReduction = 1;   //FOR TESTING PURPOSES --- reduction of steering motors
	public static final double slowDownDistance = 0.4;   //distance around setPoint before motors turn off - safety measurement based on speed of motors at any given point
	public static final int pixyPrecisionInPixels = 20;
	public static final double heightOfShooterFromCarpet = 1;


	/*COMMENTS
	 * USING A 9.52 REDUCTION FOR DRIVING MOTORS, DEEP SPACE'S MAX SPEED IS APPROX.
	 * 1.5 METERS PER SECOND WITH 337 WATTS OF POWER PER MOTOR.
	 * USING A 9.52 REDUCTION FOR DRIVING MOTORS, GAME CHANGERS MAX SPEED IS APPROX. 
	 * 1.78 METERS PER SECOND WITH 400 WATTS OF POWER PER MOTOR.
	 * GOAL IS TO HAVE A REDUCTION BETWEEN 10 AND 10.5 FOR A MAX SPEED BETWEEN 1.596 MPS AND 1.675 MPS. 
	 * THIS ALLOWS FOR MORE POWER THAN DEEP SPACE AND A HIGHER MAX SPEED.
	*/
}
