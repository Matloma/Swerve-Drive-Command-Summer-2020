/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;

public class Shooter extends SubsystemBase {
  Victor shooter;
  Servo servo;
  AnalogInput ultrasonic;
  double finalTheta = 65;
  double distanceKnown; 
  DistanceObject[] vals;

  /**
   * Creates a new Shooter.
   */
  public Shooter(AnalogInput ultrasonic) {
    shooter = new Victor(Constants.shooterPWMPort);
    servo = new Servo(Constants.aimingServoPWMPort);
    this.ultrasonic = ultrasonic;
    distanceKnown = ultrasonic.getValue()*0.125*2.54/100;  //distance in meters from an object
    vals = new DistanceObject[55];

    for(int i = 0; i < vals.length; i++){
      double temp = i+10;
      vals[i] = new DistanceObject(temp);
    }


  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    distanceKnown = ultrasonic.getValue()*0.125*2.54/100;    
  }

  public void shootXbox(XboxController xbox, double speed){
    shooter.set(xbox.getTriggerAxis(Hand.kRight)*speed);
    servo.setAngle(finalTheta);
    SmartDashboard.putNumber("Servo Angle", servo.getAngle());
  }

  public void shoot(double speed){
    shooter.set(speed);
  }

  public void stop(){
    shooter.set(0);
  }

  public void aim(){
    for(int i = 0; i < vals.length; i++){
      if(vals[i].getDistance() > distanceKnown){
        finalTheta = vals[i].getTheta();
        break;
      }
    }
    
    servo.setAngle(finalTheta);  //Might need some conversion afterward

  }

  public void raiseAim(){
    if(finalTheta < 65)
      finalTheta++;
    servo.setAngle(finalTheta);
  }

  public void lowerAim(){
    if(finalTheta > 10)
      finalTheta--;
    servo.setAngle(finalTheta);
  }
}
