/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public static XboxController xbox;
  public static AHRS gyro;

  private final SwerveDrive driveTrain;
  private final DriveXbox driveXbox;

  private final Vision vision;

  private final Intake intake;
  private final IntakeXbox intakeXbox;

  private final Loader loader;
  private final LoadXbox loadXbox;

  private final Shooter shooter;
  private final ShootXbox shootXbox;

  private final AutonomousOne autonomousOne;
  private final AutonomousTwo autonomousTwo;
  private final AutonomousThree autonomousThree;

  SendableChooser<Command> chooser = new SendableChooser<>();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    xbox = new XboxController(Constants.xboxPort);
    gyro = new AHRS();

    driveTrain = new SwerveDrive(xbox, gyro);
    driveXbox = new DriveXbox(driveTrain);
    driveXbox.addRequirements(driveTrain);
    driveTrain.setDefaultCommand(driveXbox);

    vision = new Vision(driveTrain);

    intake = new Intake();
    intakeXbox = new IntakeXbox(intake);
    intakeXbox.addRequirements(intake);
    intake.setDefaultCommand(intakeXbox);

    loader = new Loader();
    loadXbox = new LoadXbox(loader);
    loadXbox.addRequirements(loader);
    loader.setDefaultCommand(loadXbox);

    shooter = new Shooter();
    shootXbox = new ShootXbox(shooter);
    shootXbox.addRequirements(shooter);
    shooter.setDefaultCommand(shootXbox);

    autonomousOne = new AutonomousOne(driveTrain, vision, intake, loader, shooter);
    autonomousTwo = new AutonomousTwo(driveTrain, vision, intake, loader, shooter);
    autonomousThree = new AutonomousThree(driveTrain, vision, intake, loader, shooter);

    chooser.setDefaultOption("Autonomous One", autonomousOne);
    chooser.addOption("Autonomous Two", autonomousTwo);
    chooser.addOption("Autonomous Three", autonomousThree);
    SmartDashboard.putData("Autonomous Chooser", chooser);
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    JoystickButton resetGyro = new JoystickButton(xbox, XboxController.Button.kBack.value);
    resetGyro.whenPressed(new ResetGyro());
    
    JoystickButton track = new JoystickButton(xbox, XboxController.Button.kA.value);
    track.whileHeld(new VisionTrack(vision, driveTrain));

    JoystickButton move = new JoystickButton(xbox, XboxController.Button.kY.value);
    move.whenPressed(new DriveToDistanceY(driveTrain, 1, 1));
  }
  
  public static void resetGyro() {
    gyro.zeroYaw();
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return chooser.getSelected();
    // return getTeamStationCommand();
  }

  public Command getTeamStationCommand(){
    if(DriverStation.getInstance().getLocation()==1){
      return autonomousOne;
    }else if(DriverStation.getInstance().getLocation()==2){
      return autonomousTwo;
    }else{
      return autonomousThree;
    }
  }

  
}
