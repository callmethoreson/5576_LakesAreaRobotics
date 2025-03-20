// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.cscore.HttpCamera.HttpCameraKind;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Vision.LimelightHelpers;
import frc.robot.Vision.LimelightHelpers.LimelightResults;
import frc.robot.Vision.LimelightHelpers.LimelightTarget_Fiducial;
import frc.robot.Vision.LimelightHelpers.LimelightTarget_Retro;
import frc.robot.Vision.LimelightHelpers.RawDetection;
import frc.robot.Vision.LimelightHelpers.RawFiducial;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.MAXSwerveModule;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private final RobotContainer m_robotContainer;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  public Robot() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.

    

    m_robotContainer = new RobotContainer();
    HttpCamera limelightFeed = new HttpCamera("limelight", "http://10.55.76.11:5800");
    CameraServer.addCamera(limelightFeed);                      
    CameraServer.startAutomaticCapture(limelightFeed);
    Shuffleboard.getTab("Tab").add(limelightFeed);




   /*  NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");

    double tagDetected = limelight.getEntry("tv").getDouble(0);

    if(tagDetected == 1.0) {
      int tagID = (int) limelight.getEntry("tid").getDouble(0);
      double xPosition = limelight.getEntry("tx").getDouble(0);
      double yPosition = limelight.getEntry("ty").getDouble(0);
      double tagArea = limelight.getEntry("ta").getDouble(0);
      if(xPosition >.2){
        
       /* double steeringK = 0.3; //steering constant (Tune this)
        double driveK = 0.1; //driving constant (Tune this)
        double maxSpeed = 0.6; //max speed of robot

        double turn = tx * steeringK;
        double distance = 1/Math.abs(ty)*0.5;
        double driveSpeed = Math.min(distance, maxSpeed);

        DriveSubsystem.swerveDrive(driveSpeed, turn);

      

      }
    }*/



    // Basic targeting data
/*double tx = LimelightHelpers.getTX("limelight");  // Horizontal offset from crosshair to target in degrees
double ty = LimelightHelpers.getTY("limelight");  // Vertical offset from crosshair to target in degrees
double ta = LimelightHelpers.getTA("limelight");  // Target area (0% to 100% of image)*/
boolean hasTarget = LimelightHelpers.getTV("limelight"); // Do you have a valid target?


    // Change the camera pose relative to robot center (x forward, y left, z up, degrees)
LimelightHelpers.setCameraPose_RobotSpace("limelight", 
0.5,    // Forward offset (meters)
0.0,    // Side offset (meters)
0.5,    // Height offset (meters)
0.0,    // Roll (degrees)
30.0,   // Pitch (degrees)
0.0     // Yaw (degrees)
);

// Set AprilTag offset tracking point (meters)
LimelightHelpers.setFiducial3DOffset("limelight", 
0.1,    // Forward offset
0.1,    // Side offset  
0.0     // Height offset
);

// Configure AprilTag detection
LimelightHelpers.SetFiducialIDFiltersOverride("limelight", new int[]{1, 2, 3, 4, 5, 6, 7}); // Only track these tag IDs
 
/*LimelightResults results = LimelightHelpers.getLatestResults("");
if (results.valid) {
    // Color/Retroreflective targets
    if (results.targets_Retro.length > 0) {
        LimelightTarget_Retro target = results.targets_Retro[0];
        double skew = target.ts;             // Target skew/rotation
        double shortSide = target.short_side; // Shortest side in pixels
        double longSide = target.long_side;   // Longest side in pixels
        Pose3d targetPose = target.getCameraPose_TargetSpace();
    }
if (results.targets_Fiducials.length > 0) {
        LimelightTarget_Fiducial tag = results.targets_Fiducials[0];
        double id = tag.fiducialID;          // Tag ID
        String family = tag.fiducialFamily;   // Tag family (e.g., "16h5")
        
        // 3D Pose Data
        Pose3d robotPoseField = tag.getRobotPose_FieldSpace();    // Robot's pose in field space
        Pose3d cameraPoseTag = tag.getCameraPose_TargetSpace();   // Camera's pose relative to tag
        Pose3d robotPoseTag = tag.getRobotPose_TargetSpace();     // Robot's pose relative to tag
        Pose3d tagPoseCamera = tag.getTargetPose_CameraSpace();   // Tag's pose relative to camera
        Pose3d tagPoseRobot = tag.getTargetPose_RobotSpace();     // Tag's pose relative to robot
        
        // 2D targeting data
        double tx = tag.tx;                  // Horizontal offset from crosshair
        double ty = tag.ty;                  // Vertical offset from crosshair
        double ta = tag.ta;                  // Target area (0-100% of image)
    }*/
    RawFiducial[] fiducials = LimelightHelpers.getRawFiducials("");
for (RawFiducial fiducial : fiducials) {
    int id = fiducial.id;                    // Tag ID
    double txnc = fiducial.txnc;             // X offset (no crosshair)
    double tync = fiducial.tync;             // Y offset (no crosshair)
    double ta = fiducial.ta;                 // Target area
    double distToCamera = fiducial.distToCamera;  // Distance to camera
    double distToRobot = fiducial.distToRobot;    // Distance to robot
    double ambiguity = fiducial.ambiguity;   // Tag pose ambiguity
}

// Get raw neural detector results
RawDetection[] detections = LimelightHelpers.getRawDetections("");
for (RawDetection detection : detections) {
    int classID = detection.classId;
    double txnc = detection.txnc;
    double tync = detection.tync;
    double ta = detection.ta;
    // Access corner coordinates if needed
    double corner0X = detection.corner0_X;
    double corner0Y = detection.corner0_Y;
    // ... corners 1-3 available similarly
    SmartDashboard.putNumber("DebugTX", txnc);
SmartDashboard.putNumber("DebugTY", tync);
SmartDashboard.putNumber("DebugTA", ta);
}
SmartDashboard.putBoolean("Target?", hasTarget);

  }
    
      /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {

    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
    
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
    
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}


 
}
