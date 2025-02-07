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
  private final SparkMax m_shooter = new SparkMax(SubsystemConstants.kShooterCanID, MotorType.kBrushless);
  private final SparkMax m_wrist = new SparkMax(SubsystemConstants.kWristCanID, MotorType.kBrushless);
  private SparkBaseConfig m_shooterConfig;
  private RelativeEncoder m_wristEncoder;

  private final double intakeSpeed = -0.2;
  private final double shootSpeed = 0.4;

  private final double autoModeSpeed = 0.2;
  private final double error = 0.1;

  private double currentHeight = 0;
  private double desiredHeight = 0;

  

  /** Creates a new ExampleSubsystem. */
  public ShooterSubsystem(CommandXboxController controller) {
    this.controller = controller;

    //set wrist to brake mode
    m_wrist.configure(m_shooterConfig.idleMode(IdleMode.kBrake), ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    m_wristEncoder = m_wrist.getEncoder();
  }

  public void init() {
    // Initialize the shooter subsystem
    m_wristEncoder.setPosition(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    updateHeight();

    if(currentHeight < (desiredHeight + error)){
      setWristSpeed(autoModeSpeed); //drive up
    }else if(currentHeight > (desiredHeight - error)){
      setWristSpeed(-autoModeSpeed); //drive down
    }else{
      setWristSpeed(0); //stop
    }


  }

  public void shoot() {
    m_shooter.set(shootSpeed);
  }

  public void intake() {
    m_shooter.set(intakeSpeed);
  }

  public void stop() {
    m_shooter.stopMotor();
  }

  private void setWristSpeed(double speed) {
    m_wrist.set(speed);
  }

  private void updateHeight() {
    currentHeight = m_wristEncoder.getPosition();
  }

  public void setDesiredDist(double height) {
    desiredHeight = height;
  }

}
