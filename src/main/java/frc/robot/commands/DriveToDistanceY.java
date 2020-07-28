/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;
import frc.robot.Constants;

public class DriveToDistanceY extends CommandBase {
SwerveDrive driveTrain;
double setPoint;
private boolean finish = false;
double speed;


  /**
   * Creates a new DriveToDistanceY.
   */
  public DriveToDistanceY(SwerveDrive driveTrain, double setPoint, double speed){
    // Use addRequirements() here to declare subsystem dependencies.
    this.driveTrain = driveTrain;
    addRequirements(this.driveTrain);
    this.setPoint = setPoint;
    this.speed = speed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    driveTrain.setSpeedMotor0();
    finish = driveTrain.driveToDistanceY(setPoint, speed*Constants.autonomousSpeed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveTrain.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finish;
  }
}
