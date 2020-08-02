/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.kauailabs.navx.frc.AHRS;
import frc.robot.Constants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveDrive extends SubsystemBase {
  XboxController xbox;
  AHRS gyro;
  
  Translation2d frontLeftLocation; 
  Translation2d frontRightLocation; 
  Translation2d backLeftLocation; 
  Translation2d backRightLocation; 

  SwerveDriveKinematics kinematics;

  ChassisSpeeds speeds;

  SwerveModuleState[] moduleStates;
  SwerveModuleState frontLeft;
  SwerveModuleState frontRight;
  SwerveModuleState backLeft;
  SwerveModuleState backRight;
  TalonFX FrontLeftSpeed;
  TalonFX FrontRightSpeed;
  TalonFX BackLeftSpeed;
  TalonFX BackRightSpeed;
  TalonFX FrontLeftAngle;
  TalonFX FrontRightAngle;
  TalonFX BackLeftAngle;
  TalonFX BackRightAngle;
  
  /**
   * Creates a new SwerveDrive.
   */
  public SwerveDrive(XboxController xbox, AHRS gyro) {
    this.xbox = xbox;
    this.gyro = gyro;

    frontLeftLocation = new Translation2d(Constants.wheelPositionAbsoluteValue, Constants.wheelPositionAbsoluteValue);
    frontRightLocation = new Translation2d(Constants.wheelPositionAbsoluteValue, -Constants.wheelPositionAbsoluteValue);
    backLeftLocation = new Translation2d(-Constants.wheelPositionAbsoluteValue, Constants.wheelPositionAbsoluteValue);
    backRightLocation = new Translation2d(-Constants.wheelPositionAbsoluteValue, -Constants.wheelPositionAbsoluteValue);

    kinematics = new SwerveDriveKinematics(frontLeftLocation, frontRightLocation, backLeftLocation, backRightLocation);

    speeds = ChassisSpeeds.fromFieldRelativeSpeeds(xbox.getY(Hand.kLeft)*-1, xbox.getX(Hand.kLeft)*-1, -1*xbox.getX(Hand.kRight), Rotation2d.fromDegrees(gyro.getAngle()));

    moduleStates = kinematics.toSwerveModuleStates(speeds);
    frontLeft = moduleStates[0];
    frontRight = moduleStates[1];
    backLeft = moduleStates[2];
    backRight = moduleStates[3];

    FrontLeftSpeed = new TalonFX(Constants.frontLeftMotorCanPort);
    FrontLeftAngle = new TalonFX(Constants.frontLeftAngleCanPort);
    FrontRightSpeed = new TalonFX(Constants.frontRightMotorCanPort);
    FrontRightAngle = new TalonFX(Constants.frontRightAngleCanPort);
    BackLeftSpeed = new TalonFX(Constants.backLeftMotorCanPort);
    BackLeftAngle = new TalonFX(Constants.backLeftAngleCanPort);
    BackRightSpeed = new TalonFX(Constants.backRightMotorCanPort);
    BackRightAngle = new TalonFX(Constants.backRightAngleCanPort);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    printNumbers();
  }

  private void printNumbers(){
    SmartDashboard.putNumber("Front Left Speed", frontLeft.speedMetersPerSecond);
    SmartDashboard.putNumber("Front Right Speed", frontRight.speedMetersPerSecond);
    SmartDashboard.putNumber("Back Left Speed", backLeft.speedMetersPerSecond);
    SmartDashboard.putNumber("Back Right Speed", backRight.speedMetersPerSecond);
    SmartDashboard.putNumber("Front Left Target Angle", frontLeft.angle.getDegrees());
    SmartDashboard.putNumber("Front Right Target Angle", frontRight.angle.getDegrees());
    SmartDashboard.putNumber("Back Left Target Angle", backLeft.angle.getDegrees());
    SmartDashboard.putNumber("Back Right Target Angle", backRight.angle.getDegrees()); 

    SmartDashboard.putNumber("Front Left Position", FrontLeftSpeed.getSelectedSensorPosition()/2048.0/Constants.speedGearReduction);
    SmartDashboard.putNumber("Front Left Angle", FrontLeftAngle.getSelectedSensorPosition()/2048.0/Constants.angleGearReduction*360);
    SmartDashboard.putNumber("Front Right Position", FrontRightSpeed.getSelectedSensorPosition()/2048.0/Constants.speedGearReduction);
    SmartDashboard.putNumber("Front Right Angle", FrontRightAngle.getSelectedSensorPosition()/2048.0/Constants.angleGearReduction*360);
    SmartDashboard.putNumber("Back Left Position", BackLeftSpeed.getSelectedSensorPosition()/2048.0/Constants.speedGearReduction);
    SmartDashboard.putNumber("Back Left Angle", BackLeftAngle.getSelectedSensorPosition()/2048.0/Constants.angleGearReduction*360);
    SmartDashboard.putNumber("Back Right Position", BackRightSpeed.getSelectedSensorPosition()/2048.0/Constants.speedGearReduction);
    SmartDashboard.putNumber("Back Right Angle", BackRightAngle.getSelectedSensorPosition()/2048.0/Constants.angleGearReduction*360);
  }

  public void drive(double Y, double X, double R) {
		
    speeds = ChassisSpeeds.fromFieldRelativeSpeeds(Y, -X, -R, Rotation2d.fromDegrees(gyro.getAngle()));

    moduleStates = kinematics.toSwerveModuleStates(speeds);
    frontLeft = moduleStates[0];
    frontRight = moduleStates[1];
    backLeft = moduleStates[2];
    backRight = moduleStates[3];

    FrontLeftSpeed.set(TalonFXControlMode.PercentOutput, frontLeft.speedMetersPerSecond*Constants.throttle);  //The maximum speed in MPS from the SwerveDrive class just barely exceeds 1 (1.012 is the highest Ive seen), so using Percent Output is more efficient than Velocity Mode
    FrontLeftAngle.set(TalonFXControlMode.Position, frontLeft.angle.getDegrees() * -1.0 / 360 * 2048*Constants.angleGearReduction);        //*-1 because gyro goes counterclockwise for positive values, but motor doesnt, /360 to get a ratio, *2048 because falcon500 encoders have 2048 points per rotation.
    FrontRightSpeed.set(TalonFXControlMode.PercentOutput, frontRight.speedMetersPerSecond*Constants.throttle);
    FrontRightAngle.set(TalonFXControlMode.Position, frontRight.angle.getDegrees() * -1.0 / 360 * 2048*Constants.angleGearReduction);
    BackLeftSpeed.set(TalonFXControlMode.PercentOutput, backLeft.speedMetersPerSecond*Constants.throttle);
    BackLeftAngle.set(TalonFXControlMode.Position, backLeft.angle.getDegrees() * -1.0 / 360 * 2048*Constants.angleGearReduction);
    BackRightSpeed.set(TalonFXControlMode.PercentOutput, backRight.speedMetersPerSecond*Constants.throttle);
    BackRightAngle.set(TalonFXControlMode.Position, backRight.angle.getDegrees() * -1.0 / 360 * 2048*Constants.angleGearReduction);
    
  }

  public void driveXbox(XboxController xbox, double speed){
    drive(xbox.getY(Hand.kLeft)*-1, xbox.getX(Hand.kLeft), xbox.getX(Hand.kRight));
  }

  public boolean driveToDistanceAngle(double setPoint, double angle, double speed){
    setSpeedMotor0();

    double yAngle = Math.cos(Math.toRadians(angle));
    double xAngle = Math.sin(Math.toRadians(angle));

    while(FrontLeftSpeed.getSelectedSensorPosition()/2048.0/Constants.speedGearReduction<setPoint-Constants.slowDownDistance*(FrontLeftSpeed.getSelectedSensorVelocity()/2048.0*10/52.5)){
      drive(yAngle, xAngle, 0); 
    }
    stop();
    setSpeedMotor0();
    return true;
  }

  public boolean driveToDistanceY(double setPointY, double speed){

    setSpeedMotor0();

    while(FrontLeftSpeed.getSelectedSensorPosition()/2048.0/Constants.speedGearReduction<setPointY-Constants.slowDownDistance*(FrontLeftSpeed.getSelectedSensorVelocity()/2048.0*10/52.5)){
      drive(speed, 0, 0); 
    }
    while(FrontLeftSpeed.getSelectedSensorPosition()/2048.0/Constants.speedGearReduction*-1>setPointY+Constants.slowDownDistance*(FrontLeftSpeed.getSelectedSensorVelocity()/2048.0*10/52.5)){
      drive(-speed, 0, 0); 
    }
    stop();
    setSpeedMotor0();
    return true;
  }

  public boolean driveToDistanceX(double setPointX, double speed){

    setSpeedMotor0();

    while(FrontLeftSpeed.getSelectedSensorPosition()/2048.0/Constants.speedGearReduction<setPointX-Constants.slowDownDistance*(FrontLeftSpeed.getSelectedSensorVelocity()/2048.0*10/52.5)){
      drive(0, speed, 0); 
    }
    while(FrontLeftSpeed.getSelectedSensorPosition()/2048.0/Constants.speedGearReduction*-1>setPointX+Constants.slowDownDistance*(FrontLeftSpeed.getSelectedSensorVelocity()/2048.0*10/52.5)){
      drive(0, -speed, 0); 
    }
    stop();
    setSpeedMotor0();
    return true;
  }

  public boolean rotateToAngle(double setPoint, double speed){

    setSpeedMotor0();

    while(gyro.getAngle()*-1<setPoint-10*speed){
      drive(0, 0, speed); 
    }
    while(gyro.getAngle()*-1>setPoint+10*speed){
      drive(0, 0, -speed); 
    }
    stop();
    setSpeedMotor0();
    return true;
  }

  public void setSpeedMotor0(){
    FrontLeftSpeed.setSelectedSensorPosition(0);
    FrontRightSpeed.setSelectedSensorPosition(0);
    BackLeftSpeed.setSelectedSensorPosition(0);
    BackRightSpeed.setSelectedSensorPosition(0);
  }

  public void setAngleMotor0(){
    FrontLeftAngle.setSelectedSensorPosition(0);
    FrontRightAngle.setSelectedSensorPosition(0);
    BackLeftAngle.setSelectedSensorPosition(0);
    BackRightAngle.setSelectedSensorPosition(0);
  }

  public void stop(){
    FrontLeftSpeed.set(TalonFXControlMode.PercentOutput, 0);
    FrontRightSpeed.set(TalonFXControlMode.PercentOutput, 0);
    BackLeftSpeed.set(TalonFXControlMode.PercentOutput, 0);
    BackRightSpeed.set(TalonFXControlMode.PercentOutput, 0);
  }
}
