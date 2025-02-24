// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;


/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs the motors with
 * arcade steering.
 */
public class Robot extends TimedRobot {
  private final PWMSparkMax m_leftMotor = new PWMSparkMax(0);
  private final PWMSparkMax m_rightMotor = new PWMSparkMax(1);

  private final SparkMax spark1 = new SparkMax(5, MotorType.kBrushless);
  private final SparkMax spark2 = new SparkMax(6, MotorType.kBrushless);


  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftMotor::set, m_rightMotor::set);
  private final XboxController x_controller = new XboxController(0);

  private RelativeEncoder m_Encoder1;
  private RelativeEncoder m_Encoder2;

  public boolean invertSpin = true;

  /** Called once at the beginning of the robot program. */
  public Robot() {
    SendableRegistry.addChild(m_robotDrive, m_leftMotor);
    SendableRegistry.addChild(m_robotDrive, m_rightMotor);

    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_rightMotor.setInverted(true);

    m_Encoder1 = spark1.getEncoder();
    m_Encoder2 = spark2.getEncoder();
  }

  @Override
  public void teleopPeriodic() {

    // if (x_controller.getYButtonPressed()) {
    //   if (invertSpin == true){
    //     invertSpin = false;
    //   } else {
    //     invertSpin = true;
    //   }
    // }

    // if (invertSpin) {
    //   spark1.set(x_controller.getLeftTriggerAxis()*0.1);
    //   spark2.set(x_controller.getRightTriggerAxis()*0.1);
    // } else {
    //   spark1.set(x_controller.getLeftTriggerAxis()*-0.1);
    //   spark2.set(x_controller.getRightTriggerAxis()*-0.1);
//    }


    spark1.set(x_controller.getLeftY());
    SmartDashboard.putNumber("Encoder Position:", m_Encoder1.getPosition());

    // Drive with arcade drive.
    // That means that the Y axis drives forward
    // and backward, and the X turns left and right.
    //m_robotDrive.arcadeDrive(m_stick.getY()*0.5, -m_stick.getZ()*0.5);
    // m_robotDrive.tankDrive(-x_controller.getLeftY(), -x_controller.getRightY());
    
  }
}
