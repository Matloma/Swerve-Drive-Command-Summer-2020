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
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;

public class Shooter extends SubsystemBase {
  Victor shooter;
  /**
   * Creates a new Shooter.
   */
  public Shooter() {
    shooter = new Victor(Constants.shooterPWMPort);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void shootXbox(XboxController xbox, double speed){
    shooter.set(xbox.getTriggerAxis(Hand.kRight)*speed);
  }

  public void shoot(double speed){
    shooter.set(speed);
  }

  public void stop(){
    shooter.set(0);
  }
}
