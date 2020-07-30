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

public class IntakeBallTimed extends CommandBase {
  Intake intake;
  double speed;
  private boolean finish = false;
  Timer timer;
  private double time;
  /**
   * Creates a new IntakeBallTimed.
   */
  public IntakeBallTimed(Intake intake, double time,  double speed) {
    this.intake = intake;
    this.speed = speed;
    this.time = time;
    timer = new Timer();
    addRequirements(this.intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
    timer.start();
    while(timer.get() < time){
      intake.intakeBall(speed*Constants.intakeMaxSpeed);
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
    intake.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finish;
  }
}
