// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class KrakenSubsystem extends SubsystemBase {

  TalonFX _talon0 = new TalonFX(6); // Change '0' to match device ID in Tuner.  Use VictorSPX for Victor SPXs

  private double errorMargin;
  private double desiredDistance = 0;

  private CommandXboxController controller;

  /** Creates a new ExampleSubsystem. */
  public KrakenSubsystem(CommandXboxController controller) {

    this.errorMargin = 1.5;
    this.controller = controller;

  }

  public void moveMotor(double speed) {
    _talon0.set(speed);
  }

  public void moveForward(){
    _talon0.set(0.05);
  }

  public void moveBackward(){
    _talon0.set(-0.05);
  }

  public void stop(){
    _talon0.set(0);
  }

  public void setDesiredDistance(double distance){
    this.desiredDistance = distance;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Encoder Position 0:", _talon0.getPosition().getValueAsDouble());
    SmartDashboard.putNumber("Desired Distance", desiredDistance);
    
    double position = (_talon0.getPosition().getValueAsDouble())/2 ;
    
    SmartDashboard.putNumber("Actual Distance", position);

    if (position >= desiredDistance + errorMargin) {
      //go backwards
      moveBackward();
    }else if(position <= desiredDistance - errorMargin){
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
