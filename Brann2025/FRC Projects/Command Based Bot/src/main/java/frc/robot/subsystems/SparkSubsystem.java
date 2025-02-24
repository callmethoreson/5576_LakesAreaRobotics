// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class SparkSubsystem extends SubsystemBase {

  private final SparkMax spark1 = new SparkMax(5, MotorType.kBrushless);
  private final SparkMax spark2 = new SparkMax(6, MotorType.kBrushless);
  private RelativeEncoder m_Encoder1;
  private RelativeEncoder m_Encoder2;
  private double error;

  private double desiredDistance = 0;

  private CommandXboxController controller;

  /** Creates a new ExampleSubsystem. */
  public SparkSubsystem(CommandXboxController controller) {

    this.error = 1;
    this.controller = controller;

    m_Encoder1 = spark1.getEncoder();
    m_Encoder2 = spark2.getEncoder();

  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  //function to get position of the encoder
  public double getPosition(){
    return m_Encoder1.getPosition();
  }

  public void moveMotor(double speed) {
    spark1.set(speed * 0.1);
  }

  public void moveForward(){
    spark1.set(0.1);
  }

  public void moveBackward(){
    spark1.set(-0.1);
  }

  public void setDesiredDistance(double distance){
    this.desiredDistance = distance;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Encoder Position:", m_Encoder1.getPosition());
    double position = getPosition();

    if (position >= desiredDistance + error) {
      //go backwards
      moveBackward();
    }else if(position <= desiredDistance - error){
      //go forwards
      moveForward();
    }else{
      moveMotor(0);
    }

  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
