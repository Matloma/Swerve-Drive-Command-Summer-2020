/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  private TalonSRX intake;
  /**
   * Creates a new Intake.
   */
  public Intake() {
    intake = new TalonSRX(Constants.intakeCANPort);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void intake(XboxController xbox, double speed){
    intake.set(TalonSRXControlMode.PercentOutput, xbox.getTriggerAxis(Hand.kLeft)*speed);
  }

  public void intakeBall(double speed){
    intake.set(TalonSRXControlMode.PercentOutput, speed);
  }

  public void stop(){
    intake.set(TalonSRXControlMode.PercentOutput, 0);
  }
}
