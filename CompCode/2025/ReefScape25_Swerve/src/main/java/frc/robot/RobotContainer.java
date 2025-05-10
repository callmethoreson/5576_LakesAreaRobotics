// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.SetElevatorHeight;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // define used subsystems
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  // drive subsystem
  private final DriveSubsystem m_robotDrive = new DriveSubsystem();
 

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController = new CommandXboxController(OIConstants.kDriverControllerPort);
  private final CommandXboxController m_secondController = new CommandXboxController(OIConstants.kAssistControllerPort);
  private final ElevatorSubsystem m_elevator = new ElevatorSubsystem(m_secondController);
  //private final ShooterSubsystem m_shooter = new ShooterSubsystem(m_driverController);



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
  * Use this method to define your trigger->command mappings.
  */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    new Trigger(m_exampleSubsystem::exampleCondition).onTrue(new ExampleCommand(m_exampleSubsystem));
    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed, cancelling on release.
    m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());

    new Trigger(m_driverController.start()).onTrue(m_robotDrive.setXCommand());


    new Trigger(m_secondController.a()).onTrue(new SetElevatorHeight(m_elevator, 34.5));
    new Trigger(m_secondController.b()).onTrue(new SetElevatorHeight(m_elevator, 63.5));
    // new Trigger(m_secondController.x()).onTrue(new SetElevatorHeight(m_elevator, 40));
    // new Trigger(m_secondController.y()).onTrue(new SetElevatorHeight(m_elevator, 60));
    

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }
}
