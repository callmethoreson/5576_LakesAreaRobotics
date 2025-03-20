// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;

import java.io.IOException;
import java.nio.file.Path;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public final class Autos {
  /** Example static factory for an autonomous command. */
  public static Command driveStraight(DriveSubsystem subsystem) {
    return Commands.repeatingSequence(
    subsystem.autoDrive(.1,0,0,false )
    );
  }

String trajectoryJSON = "paths/Calebtestauto.wpilib.json";
Trajectory trajectory = new Trajectory();

/*public Autos() {
  try {
    Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
    trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
  }catch (IOException ex) {
    DriverStation.reportError("Unable to open trajectory:"+trajectoryJSON, ex.getStackTrace());
  }
}*/
public class RobotContainer {
  private final SendableChooser<Command> autoChooser;

  
  public RobotContainer() {
    // ...

    // Build an auto chooser. This will use Commands.none() as the default option.
    autoChooser = AutoBuilder.buildAutoChooser();

    // Another option that allows you to specify the default auto by its name
    // autoChooser = AutoBuilder.buildAutoChooser("My Default Auto");

    SmartDashboard.putData("Auto Chooser", autoChooser);
  }

  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }
}

}

