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

public class LoadBallTimed extends CommandBase {
  Loader loader;
  double speed;
  private boolean finish = false;
  Timer timer;
  private double time;
  /**
   * Creates a new LoadBallTimed.
   */
  public LoadBallTimed(Loader loader, double time,  double speed) {
    this.loader = loader;
    this.speed = speed;
    this.time = time;
    timer = new Timer();
    addRequirements(this.loader);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
    timer.start();
    while(timer.get() < time){
      loader.loadBall(speed*Constants.loaderMaxSpeed);
    }
    finish = true;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    loader.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finish;
  }
}
