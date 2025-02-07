// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveBase extends SubsystemBase {

  private final PWMSparkMax leftSpark = new PWMSparkMax(0);
  private final PWMSparkMax rightSpark = new PWMSparkMax(1);

  private final DifferentialDrive m_robotDrive = new DifferentialDrive(leftSpark::set, rightSpark::set);

  private CommandXboxController controller;

  private final double speedScalar = 0.5;

  public DriveBase(CommandXboxController controller) {
    this.controller = controller;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    m_robotDrive.tankDrive(controller.getRightY()*speedScalar, -controller.getLeftY()*speedScalar, true);
  }

}
