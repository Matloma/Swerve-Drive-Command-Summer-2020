/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Hook extends SubsystemBase {
  private DoubleSolenoid hook;
  private boolean extended = false;
  /**
   * Creates a new Hook.
   */
  public Hook() {
    hook = new DoubleSolenoid(Constants.hookSolenoidPort1, Constants.hookSolenoidPort2);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("Hook Extended?", extended);
  }

  public void extend(){
    hook.set(DoubleSolenoid.Value.kForward);
    extended = true;
  }

  public void retract(){
    hook.set(DoubleSolenoid.Value.kReverse);
    extended = false;
  }
}
