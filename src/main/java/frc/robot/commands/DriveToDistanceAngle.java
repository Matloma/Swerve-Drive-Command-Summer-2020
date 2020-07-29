/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class DriveToDistanceAngle extends CommandBase {
  SwerveDrive driveTrain;
  double setPoint;
  double angle;
  double speed;
  private boolean finish = false;
  /**
   * Creates a new DriveToDistanceAngle.
   */
  public DriveToDistanceAngle(SwerveDrive driveTrain, double setPoint, double angle, double speed) {
    this.driveTrain = driveTrain;
    addRequirements(this.driveTrain);
    this.setPoint = setPoint;
    this.angle = angle;
    this.speed = speed;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    driveTrain.setSpeedMotor0();
    finish = driveTrain.driveToDistanceAngle(setPoint, angle, speed);
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
