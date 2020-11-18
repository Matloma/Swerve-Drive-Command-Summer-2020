
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

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
  public static ColorSensorV3 colorSensor;
  public static AnalogInput ultrasonic;

  private final SwerveDrive driveTrain;
  private final DriveXbox driveXbox;

  private final Vision vision;

  private final Intake intake;
  private final IntakeXbox intakeXbox;

  private final Loader loader;
  private final LoadXbox loadXbox;

  private final Shooter shooter;
  private final ShootXbox shootXbox;

  private final ColorWheel colorWheel;
  private final SpinUntilColor spinUntilColor;

  private final Hook hook;
  private final ExtendHook extendHook;

  private final AutonomousOne autonomousOne;
  private final AutonomousTwo autonomousTwo;
  private final AutonomousThree autonomousThree;

  private SendableChooser<Command> chooser = new SendableChooser<>();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    xbox = new XboxController(Constants.xboxPort);
    gyro = new AHRS();
    colorSensor = new ColorSensorV3(Constants.i2cPort);
    ultrasonic = new AnalogInput(Constants.ultrasonicSensorPort);

    driveTrain = new SwerveDrive(xbox, gyro);
    driveXbox = new DriveXbox(driveTrain);
    driveXbox.addRequirements(driveTrain);
    driveTrain.setDefaultCommand(driveXbox);
  
    CommandScheduler.getInstance().schedule(new SetDriveTrainAngle0(driveTrain));

    vision = new Vision(driveTrain);

    intake = new Intake();
    intakeXbox = new IntakeXbox(intake);
    intakeXbox.addRequirements(intake);
    intake.setDefaultCommand(intakeXbox);

    loader = new Loader();
    loadXbox = new LoadXbox(loader);
    loadXbox.addRequirements(loader);
    loader.setDefaultCommand(loadXbox);

    shooter = new Shooter(ultrasonic);
    shootXbox = new ShootXbox(shooter);
    shootXbox.addRequirements(shooter);
    shooter.setDefaultCommand(shootXbox);

    colorWheel = new ColorWheel(colorSensor);
    spinUntilColor = new SpinUntilColor(colorWheel);
    spinUntilColor.addRequirements(colorWheel);

    hook = new Hook();
    extendHook = new ExtendHook(hook);
    extendHook.addRequirements(hook);
    
    CommandScheduler.getInstance().registerSubsystem(driveTrain, vision, intake, loader, shooter, colorWheel, hook);
    
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
    track.whileHeld(new Aim(shooter));

    JoystickButton moveToColor = new JoystickButton(xbox, XboxController.Button.kB.value);
    moveToColor.whileHeld(spinUntilColor);

    JoystickButton spinColorWheel = new JoystickButton(xbox, XboxController.Button.kBumperLeft.value);
    spinColorWheel.whileHeld(new SpinColorWheel(colorWheel));
    
    JoystickButton aimUp = new JoystickButton(xbox, XboxController.Button.kY.value);
    aimUp.whenPressed(new RaiseAim(shooter));

    JoystickButton aimDown = new JoystickButton(xbox, XboxController.Button.kX.value);
    aimDown.whenPressed(new LowerAim(shooter));

    JoystickButton extendHookButton = new JoystickButton(xbox, XboxController.Button.kBumperRight.value);
    extendHookButton.toggleWhenPressed(extendHook);
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

  /* private Command getTeamStationCommand(){
    if(DriverStation.getInstance().getLocation()==1){
      return autonomousOne;
    }else if(DriverStation.getInstance().getLocation()==2){
      return autonomousTwo;
    }else{
      return autonomousThree;
    }
  } */
}
