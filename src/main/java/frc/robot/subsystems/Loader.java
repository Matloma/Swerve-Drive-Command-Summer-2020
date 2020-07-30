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
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Loader extends SubsystemBase {
  Victor loader;
  /**
   * Creates a new Loader.
   */
  public Loader() {
    loader = new Victor(Constants.loaderPWMPort);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void loadXbox(XboxController xbox, double speed){
    if(xbox.getBumper(Hand.kLeft)){
      loadBall(speed);
    } else{
      stop();
    }
  }

  public void loadBall(double speed){
    loader.set(speed);
  }

  public void stop(){
    loader.set(0);
  }
}
