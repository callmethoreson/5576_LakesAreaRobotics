// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

public class ShooterSubsystem extends SubsystemBase {

    //TODO Change CAN ID's to match Shooter Neo 550s
    private final SparkMax m_leftMotor = new SparkMax(-1,MotorType.kBrushless);
    //private final SparkMax m_rightMotor = new SparkMax(-1,MotorType.kBrushless);
    private CommandXboxController m_controller;

    private double leftSpeed = 0;
    //private double rightSpeed = 0;
    
        //stage enum
        public enum ShooterDirection {
            INTAKE,
            FIRST,
            SECOND,
            THIRD,
            FOURTH
        }
    
        /** Creates a new ExampleSubsystem. */
    public ShooterSubsystem(CommandXboxController controller) {
        m_controller = controller;


    }

    //called once per scheduler run
    @Override
    public void periodic() {
        m_leftMotor.set(leftSpeed);
       // m_rightMotor.set(rightSpeed);
    }
    private void setSpeed(double speed) {

    
    }
}