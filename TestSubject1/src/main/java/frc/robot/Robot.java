// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;


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
  private final Joystick m_stick = new Joystick(0);
  private final XboxController mController = new XboxController(1);
  /** Called once at the beginning of the robot program. */
  public Robot() {
    SendableRegistry.addChild(m_robotDrive, m_leftMotor);
    SendableRegistry.addChild(m_robotDrive, m_rightMotor);

    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_rightMotor.setInverted(true);
  }
 
  

  @Override
  public void teleopPeriodic() {

    // if (m_stick.getTrigger()){
      spark1.set(   mController.getLeftTriggerAxis()  *   0.1   ); 
      spark2.set(   mController.getRightTriggerAxis() *   0.1   );
    // } else{
    //   spark1.set(0);
    //   spark2.set(0);
    // }
    if (mController.getAButton()){

      spark1.set(  - mController.getLeftTriggerAxis()  *   0.1   ); 
      spark2.set(  - mController.getRightTriggerAxis() *   0.1   );
    }


    // Drive with arcade drive.
    // That means that the Y axis drives forward
    // and backward, and the X turns left and right.
    //m_robotDrive.arcadeDrive(m_stick.getY()*0.5, -m_stick.getZ()*0.5);
    m_robotDrive.arcadeDrive(0, 0);

    
  }
}
