// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkBase.ResetMode;

public class Elevator extends SubsystemBase {

  private final SparkMax leftSpark = new SparkMax(5, MotorType.kBrushless);
  private final SparkMax rightSpark = new SparkMax(6, MotorType.kBrushless);

  private final SparkMaxConfig leftConfig = new SparkMaxConfig();
  private final SparkMaxConfig rightConfig = new SparkMaxConfig();

  private final double speedScalar = 0.1;


  public Elevator() {
    leftConfig.inverted(true);
    rightConfig.inverted(false);

    leftSpark.configure(leftConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
    rightSpark.configure(rightConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
  }

  public boolean aboveMaxHeight() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  public void setSpeed(double speed){
    leftSpark.set(speed * speedScalar);
    rightSpark.set(speed * speedScalar);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public Command exampleMethodCommand() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'exampleMethodCommand'");
  }

}
