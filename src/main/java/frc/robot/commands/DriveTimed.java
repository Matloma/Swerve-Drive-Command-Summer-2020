/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.Timer;

public class DriveTimed extends CommandBase {
  SwerveDrive driveTrain;
  Timer timer;
  private boolean finish = false;
  private double time;
  private double speedY;
  private double speedX;
  private double speedR;

  /**
   * Creates a new DriveTimed.
   */
  public DriveTimed(SwerveDrive driveTrain, double time, double speedY, double speedX, double speedR) {
    this.driveTrain = driveTrain;
    this.speedY = speedY;
    this.speedX = speedX;
    this.speedR = speedR;
    this.time = time;
    timer = new Timer();
    addRequirements(this.driveTrain);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    driveTrain.setSpeedMotor0();
    finish = false;
    timer.reset();
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(timer.get() < time)
      driveTrain.drive(speedY*Constants.autonomousSpeed, 
                       speedX*Constants.autonomousSpeed, 
                       speedR*Constants.autonomousSpeed
      );
    else{
      driveTrain.setSpeedMotor0();
      finish = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveTrain.setSpeedMotor0();
    driveTrain.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finish;
  }
}
