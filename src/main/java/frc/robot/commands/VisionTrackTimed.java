/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.*;

public class VisionTrackTimed extends CommandBase {
  Vision vision;
  SwerveDrive driveTrain;
  Timer timer;
  private double time;
  private boolean finish = false;
  /**
   * Creates a new VisionTrackTimed.
   */
  public VisionTrackTimed(Vision vision, SwerveDrive driveTrain, double time) {
    this.vision = vision;
    this.driveTrain = driveTrain;
    addRequirements(this.vision, this.driveTrain);
    timer = new Timer();
    this.time = time;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(timer.get() < time)
      vision.track(driveTrain);
    else
      finish = true;
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
