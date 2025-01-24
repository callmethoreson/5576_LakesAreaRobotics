// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  /** Creates a new Shooter subsystem. */

  private final TalonFX m_shooterTop = new TalonFX(10, "rio");
  private final TalonFX m_shooterBottom = new TalonFX(11, "rio");


  public Shooter() {
    //reset encoder values
    m_shooterBottom.setPosition(0);
    m_shooterTop.setPosition(0);
  }

  private final double maxShooterSpeed = 0.05;


  /**
   * Example command factory method.
   *
   * @return a command
   */
  public Command exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
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

  public int getStage(){
    System.out.println("Encoder value: " + m_shooterTop.getPosition().getValueAsDouble()) ;

    if(m_shooterTop.getPosition().getValueAsDouble() < 5){
      return 0;
    }else if(m_shooterTop.getPosition().getValueAsDouble() < 15){
      return 1;
    }else{
      return 2;
    }

  }

  public void setSpeed(double motorSpeed){
    m_shooterTop.set(motorSpeed * maxShooterSpeed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
