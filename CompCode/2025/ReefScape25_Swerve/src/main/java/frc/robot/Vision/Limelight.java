package frc.robot.Vision;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Util.RectanglePoseArea;
import frc.robot.Vision.LimelightHelpers.LimelightTarget_Fiducial;
import frc.robot.subsystems.DriveSubsystem;
//import frc.robot.Util.RectanglePoseArea;

public class Limelight extends SubsystemBase{
    DriveSubsystem drivetrain;
    Alliance alliance;
    private String ll = "limelight";
    private Boolean enable = true;
    private Boolean trust = true;
    private int fieldError = 0;
    private int distanceError = 0;
    private Pose2d botpose;
    private static final RectanglePoseArea field = new RectanglePoseArea(new Translation2d(0.0, 0.0), new Translation2d(16.54, 8.02));

    public Limelight(DriveSubsystem drivetrain) {
    this.drivetrain = drivetrain;
    //return drivetrain;
    
    //SmartDashboard.putNumber("Field Error", fieldError);
    //SmartDashboard.putNumber("Limelight Error", distanceError);
    }

    public void periodic() {
        
        
        /*if (enable) {
          Double targetDistance = LimelightHelpers.getTargetPose3d_CameraSpace(ll).getTranslation().getDistance(new Translation3d());
          Double confidence = 1 - ((targetDistance - 1) / 6);
          LimelightHelpers.Results result =
              LimelightHelpers.getLatestResults(ll).targetingResults;
          if (true) {
            botpose = LimelightHelpers.getBotPose2d_wpiBlue(ll);
            

            /*for (LimelightTarget_Fiducial target: result.targets_Fiducials) {
                if (target.fiducialID == 1) {
                    SmartDashboard.putNumber("Tag", 1);
                    }
                else if (target.fiducialID == 2) {
                    SmartDashboard.putNumber("Tag", 2);
                    }
                else{
                    SmartDashboard.putNumber("Tag", -1);
                }
                }*/
          //}
          
       // }

       

      }


    }
