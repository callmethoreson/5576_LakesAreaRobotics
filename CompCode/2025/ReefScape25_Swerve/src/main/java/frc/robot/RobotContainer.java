// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.Vision.Limelight;
import frc.robot.Vision.LimelightHelpers;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.SetElevatorHeight;
// import frc.robot.commands.ShooterCommands;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
  
  //Limelight Subsystem
  private Limelight limelight = new Limelight(m_robotDrive);
 

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController = new CommandXboxController(OIConstants.kDriverControllerPort);
  private final CommandXboxController m_secondController = new CommandXboxController(OIConstants.kAssistControllerPort);
  private final ElevatorSubsystem m_elevator = new ElevatorSubsystem(m_secondController);
  private final ShooterSubsystem m_shooter = new ShooterSubsystem();



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

    m_shooter.setDefaultCommand(m_shooter.holdCommand());
    m_elevator.setDefaultCommand(m_elevator.holdCommand());
    limelight.periodic();
    configureBindings();


    //double tx1 = 1;
    //SmartDashboard.putNumber("debug1", tx1);

    NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");

    double tagDetected = limelight.getEntry("tv").getDouble(0);

    
    if(tagDetected == 1.0) {
      int tagID = (int) limelight.getEntry("tid").getDouble(0);
      double xPosition = limelight.getEntry("tx").getDouble(0);
      double yPosition = limelight.getEntry("ty").getDouble(0);
      double tagArea = limelight.getEntry("ta").getDouble(0);
      if(xPosition < 0 ){
        
       double steeringK = 0.3; //steering constant (Tune this)
        double driveK = 0.1; //driving constant (Tune this)
        double maxSpeed = 0.6; //max speed of robot

        //double turn = tx * steeringK;
       // double distance = 1/Math.abs(ty)*0.5;
       // double driveSpeed = Math.min(distance, maxSpeed);

       // DriveSubsystem.swerveDrive(driveSpeed, turn);

        
        m_shooter.setDefaultCommand(m_shooter.intakeCommand());

      }
    }

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

    //Elevator shooting heights
    new Trigger(m_secondController.a()).onTrue(new SetElevatorHeight(m_elevator, 90));
    new Trigger(m_secondController.y()).onTrue(new SetElevatorHeight(m_elevator, 270));
    new Trigger(m_secondController.b()).onTrue(new SetElevatorHeight(m_elevator, 165));
    //Elevator intake height
    new Trigger(m_secondController.x()).onTrue(new SetElevatorHeight(m_elevator, 60));
    //Return to zero
    new Trigger(m_secondController.leftBumper().onTrue(new SetElevatorHeight(m_elevator, 0)));


    new Trigger(m_driverController.leftBumper().whileTrue(m_shooter.outtakeTreeCommand()));
    new Trigger(m_driverController.leftTrigger().whileTrue(m_shooter.outtakeL1Command()));

    new Trigger(m_secondController.rightBumper().whileTrue(m_shooter.intakeCommand()));
    new Trigger(m_driverController.rightTrigger().whileTrue(m_shooter.releaseCommand()));
    // new Trigger(m_driverController.rightTrigger().onTrue(new ShooterCommands(m_shooter)));
    // new Trigger(m_driverController.b().onTrue(new ShooterCommands(m_shooter)));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    //An example command will be run in autonomous
   return Autos.driveStraight(m_robotDrive);
  }
} 