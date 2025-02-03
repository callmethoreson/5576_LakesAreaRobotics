// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Lifter extends SubsystemBase {
  /** Creates a new Shooter subsystem. */

  private final TalonFX m_climberL = new TalonFX(4, "rio");
  private final TalonFX m_climberR = new TalonFX(5, "rio");


  public Lifter() {
    //reset encoder values
    m_climberL.setPosition(0);
    m_climberR.setPosition(0);
  }

  private final double maxLiftSpeed = 0.2;

  public int getStage(){
    System.out.println("Encoder value: " + m_climberL.getPosition().getValueAsDouble()) ;

    if(m_climberL.getPosition().getValueAsDouble() <= 2){
      return 0;
    }else if(m_climberL.getPosition().getValueAsDouble() < 60){
      return 1;
    }else{
      return 2;
    }

  }

  public void setSpeed(double motorSpeed){
    m_climberL.set(motorSpeed * maxLiftSpeed);
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
