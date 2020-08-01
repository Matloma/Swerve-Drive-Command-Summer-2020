/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;

public class ColorWheel extends SubsystemBase {
  private final ColorSensorV3 colorSensor;
  private final ColorMatch colorMatcher;
  private final Color blueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final Color greenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  private final Color redTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  private final Color yellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

  Color detectedColor;
  String colorString;
  ColorMatchResult match;

  Victor colorWheelMotor;

  /**
   * Creates a new ColorWheel.
   */
  public ColorWheel(ColorSensorV3 colorSensor) {
    this.colorSensor = colorSensor;
    colorMatcher = new ColorMatch();
    colorMatcher.addColorMatch(blueTarget);
    colorMatcher.addColorMatch(greenTarget);
    colorMatcher.addColorMatch(redTarget);
    colorMatcher.addColorMatch(yellowTarget);
    colorWheelMotor = new Victor(Constants.colorWheelMotorPWMPort);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    detectedColor = colorSensor.getColor();
    match = colorMatcher.matchClosestColor(detectedColor);
    
    if( match.color == blueTarget){
      colorString = "Blue";
    }else if(match.color == redTarget){
      colorString = "Red";
    }else if(match.color == greenTarget){
      colorString = "Green";
    }else if(match.color == yellowTarget){
      colorString = "Yellow";
    }else{
      colorString = "Unknown";
    }

    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("Confidence", match.confidence);
    SmartDashboard.putString("Detected Color", colorString);

  }

  public void spinUntilColor(){
    String gameData = DriverStation.getInstance().getGameSpecificMessage();
    if(gameData.length() > 0){
      switch(gameData.toUpperCase().charAt(0)){
        case 'B': //Red is 2 sections after Blue, meaning Blue will be below the sensor when red is detected by the robot. Same case for other colors.
          SmartDashboard.putString("ColorSensor","Blue");  
          if(match.color == redTarget)
            stop();
          else
            colorWheelMotor.set(Constants.colorWheelMotorMaxSpeed);
          break;
        case 'Y': 
          SmartDashboard.putString("ColorSensor","Yellow");
          if(match.color == greenTarget)
            stop();
          else
            colorWheelMotor.set(Constants.colorWheelMotorMaxSpeed);
          break;
        case 'R': 
          SmartDashboard.putString("ColorSensor","Red");
          if(match.color == blueTarget)
            stop();
          else
            colorWheelMotor.set(Constants.colorWheelMotorMaxSpeed);
          break;
        case 'G': 
          SmartDashboard.putString("ColorSensor","Green");
          if(match.color == yellowTarget)
            stop();
          else
            colorWheelMotor.set(Constants.colorWheelMotorMaxSpeed);
          break;
        default: 
          SmartDashboard.putString("ColorSensor","GAME DATA IS NOT VALID");
          break;
      }
    } else {
      SmartDashboard.putString("ColorSensor","NO GAME DATA RECEIVED");
    }
  }

  public void spin(double speed){
    colorWheelMotor.set(speed);
  }

  public void stop(){
    colorWheelMotor.set(0);
  }
}
