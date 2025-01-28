// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;


public class DriveSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */

  private DifferentialDrive m_robotDrive;

  //motor controllers
  private PWMSparkMax m_left = new PWMSparkMax(0);
  private PWMSparkMax m_right = new PWMSparkMax(1);

  private double xSpeed = 0;
  private double zRotation = 0;

  public void updateSpeeds(double xSpeed, double zRotation){
    this.xSpeed = xSpeed;
    this.zRotation = zRotation;
  }

  public DriveSubsystem() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // drive the robot
    m_robotDrive.arcadeDrive(this.xSpeed, this.zRotation);
  }

}
