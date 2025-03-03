// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.OperatorConstants.OIConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.SetElevatorHeight;
import frc.robot.commands.SetKrakenDistance;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.FalconSubsystem;
import frc.robot.subsystems.KrakenSubsystem;
import frc.robot.subsystems.SparkSubsystem;
import frc.robot.subsystems.ShooterSubsystem.ShooterDirection;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.commands.ClimberCommands;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.Constants.*;

import java.util.Set;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.XboxController;
// import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.PS4Controller.Button;
import frc.robot.Constants.OperatorConstants.AutoConstants;
import frc.robot.Constants.OperatorConstants.DriveConstants;
import frc.robot.Constants.OperatorConstants.OIConstants;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import java.util.List;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);
  private final CommandXboxController m_secondController =
      new CommandXboxController(OperatorConstants.kSecondControllerPort);
  


  //private final KrakenSubsystem m_exampleSubsystem = new KrakenSubsystem(m_driverController);
  //private final FalconSubsystem m_exampleFalcon = new FalconSubsystem(m_driverController);
  //private final SparkSubsystem  m_exampleSpark = new SparkSubsystem(m_driverController);

  private final ElevatorSubsystem m_elevator = new ElevatorSubsystem(m_driverController);
  //private final ShooterSubsystem m_shooter = new ShooterSubsystem(m_driverController);
  private final ClimberSubsystem m_climber = new ClimberSubsystem(m_driverController);
  private final DriveSubsystem m_robotDrive = new DriveSubsystem();


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    m_robotDrive.setDefaultCommand(
        // The left stick controls translation of the robot.
        // Turning is controlled by the X axis of the right stick.
        new RunCommand(
            () -> m_robotDrive.drive( //Xbox
                -MathUtil.applyDeadband(m_driverController.getLeftY() * DriveConstants.kSpeedScalingConstant, OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(m_driverController.getLeftX() * DriveConstants.kSpeedScalingConstant, OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(m_driverController.getRightX() * DriveConstants.kSpeedScalingConstant, OIConstants.kDriveDeadband),
                true),
            m_robotDrive));

    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    // new Trigger(m_exampleSubsystem::exampleCondition)
    //     .onTrue(new ExampleCommand(m_exampleSubsystem, 0));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    //m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
    // new Trigger(m_driverController.a()).onTrue(new ExampleCommand(m_exampleSubsystem, 2));
    // new Trigger(m_driverController.b()).onTrue(new ExampleCommand(m_exampleSubsystem, 4));
    // new Trigger(m_driverController.x()).onTrue(new ExampleCommand(m_exampleSubsystem, 6));
    // new Trigger(m_driverController.y()).onTrue(new ExampleCommand(m_exampleSubsystem, 8));

    // new Trigger(m_driverController.a()).onTrue(new SetKrakenDistance(m_exampleSubsystem, 20));
    // new Trigger(m_driverController.b()).onTrue(new SetKrakenDistance(m_exampleSubsystem, 40));
    // new Trigger(m_driverController.x()).onTrue(new SetKrakenDistance(m_exampleSubsystem, 60));
    // new Trigger(m_driverController.y()).onTrue(new SetKrakenDistance(m_exampleSubsystem, 80));

    new Trigger(m_driverController.a()).onTrue(new SetElevatorHeight(m_elevator, 0));
    new Trigger(m_driverController.b()).onTrue(new SetElevatorHeight(m_elevator, 20));
    new Trigger(m_driverController.x()).onTrue(new SetElevatorHeight(m_elevator, 40));
    new Trigger(m_driverController.y()).onTrue(new SetElevatorHeight(m_elevator, 60));
    
    new Trigger(m_secondController.leftBumper()).onTrue(ClimberCommands.runBackwards(m_climber));
    new Trigger(m_secondController.rightBumper()).onTrue(ClimberCommands.runForwards(m_climber));
   // new Trigger(m_driverController.leftBumper(new ShooterDirection(SHOOT)


  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    //return Autos.exampleAuto(m_exampleSubsystem);
    return null;
  }
}