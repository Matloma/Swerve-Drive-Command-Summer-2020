/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class AutonomousThree extends SequentialCommandGroup {
  /**
   * Creates a new AutonomousThree.
   */
  public AutonomousThree(SwerveDrive driveTrain, Intake intake, Shooter shooter) {
    addCommands(new ResetGyro(),
                new DriveToDistanceX(driveTrain, .1, 1), 
                new ParallelCommandGroup(new ShootBallTimed(shooter, .5, 1), 
                                         new IntakeBallTimed(intake, .5, 1))
    );
  }
}
