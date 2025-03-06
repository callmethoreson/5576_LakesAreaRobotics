// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.SubsystemConstants;

public class ShooterSubsystem extends SubsystemBase {
  private final CommandXboxController controller;
  private final SparkMax m_shooterLeft = new SparkMax(SubsystemConstants.kShooterLeftCanID, MotorType.kBrushless);
  private final SparkMax m_shooterRight = new SparkMax(SubsystemConstants.kShooterRightCanID, MotorType.kBrushless);
  private final SparkMax m_intake = new SparkMax(SubsystemConstants.kIntakeID, MotorType.kBrushless);
  private SparkBaseConfig m_shooterConfig;

  private final double intakeSpeed = 0.2;
  private final double shootLeftSpeed = 0.4;
  private final double shootRightSpeed = 0.4;

  /** Creates a new ExampleSubsystem. */
  public ShooterSubsystem(CommandXboxController controller) {
    this.controller = controller;

    //set wrist to brake mode
    m_shooterLeft.configure(m_shooterConfig.idleMode(IdleMode.kBrake), ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    m_shooterRight.configure(m_shooterConfig.idleMode(IdleMode.kBrake), ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    m_intake.configure(m_shooterConfig.idleMode(IdleMode.kBrake), ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if(controller.rightBumper() != null){
      m_shooterRight.set(shootLeftSpeed);
      m_shooterLeft.set(shootRightSpeed);
      m_intake.set(shootLeftSpeed);
    }
    else if(controller.leftBumper() != null){
      m_intake.set(shootLeftSpeed);
      m_shooterLeft.set(shootLeftSpeed);
      m_shooterLeft.set(shootRightSpeed*.25);
    }
    else if (controller.b() != null)  {
      if(controller.getLeftTriggerAxis() != 0) {
        m_intake.set(controller.getLeftTriggerAxis());
        m_shooterLeft.set(controller.getLeftTriggerAxis());
        m_shooterRight.set(controller.getLeftTriggerAxis());
      }
      if(controller.getRightTriggerAxis() != 0) {
        m_intake.set(controller.getRightTriggerAxis());
        m_shooterLeft.set(controller.getRightTriggerAxis());
        m_shooterRight.set(controller.getRightTriggerAxis());
      }
    }
    else if(controller.getLeftTriggerAxis() != 0 || controller.getRightTriggerAxis() != 0){
      if(controller.b() == null){
      m_intake.set(intakeSpeed);
      m_shooterLeft.set(-intakeSpeed);
      m_shooterRight.set(-intakeSpeed);
      }
    }
   
      else{
      m_intake.set(0);
      m_shooterLeft.set(0);
      m_shooterRight.set(0);
    }


    
  }
  // public void shoot() {
  //   m_shooterLeft.set(shootLeftSpeed);
  //   m_shooterRight.set(shootRightSpeed);
  //   m_intake.set(intakeSpeed*2);
  // }
  // public void shootLow() {
  //   m_shooterLeft.set(shootRightSpeed);
  //   m_intake.set(intakeSpeed*2);
  // }

  // public void intake() {
  //   m_shooterLeft.set(intakeSpeed);
  //   m_shooterRight.set(intakeSpeed);
  //   m_intake.set(intakeSpeed);

  // }

  // public void stop() {
  //   m_shooterLeft.stopMotor();
  //   m_shooterRight.stopMotor();
  //   m_intake.stopMotor();
  // }
}