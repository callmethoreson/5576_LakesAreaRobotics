// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.SubsystemConstants;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.configs.MotorOutputConfigs;

public class ElevatorSubsystem extends SubsystemBase {

    private final TalonFX m_leftMotor = new TalonFX(SubsystemConstants.kLeftElevatorCanID);
    private final TalonFX m_rightMotor = new TalonFX(SubsystemConstants.kRightElevatorCanID);
    private final CommandXboxController m_controller;

    private double leftDistance = 0;
    private double rightDistance = 0;

    private double minHeight = 0.5;
    private double maxHeight = 5;

    private double leftSpeed = 0;
    private double rightSpeed = 0;

    private boolean manualMode = false;
    private double autoModeSpeed = 0.3;

    private double error = 0.5;

    private double currentHeight = 0;
    private double desiredHeight = 0;

    //stage enum
    public enum ElevatorStage {
        PARK,
        INTAKE,
        FIRST,
        SECOND,
        THIRD,
        FOURTH,
    }

    /** Creates a new ExampleSubsystem. */
    public ElevatorSubsystem(CommandXboxController controller) {
        m_controller = controller;

        //setup left talonfx as inverted and in brake mode
        m_leftMotor.getConfigurator().apply(new MotorOutputConfigs().withInverted(InvertedValue.Clockwise_Positive));
        m_leftMotor.setNeutralMode(NeutralModeValue.Brake);

        //setup right talonfx as inverted and in brake mode
        m_rightMotor.getConfigurator().apply(new MotorOutputConfigs().withInverted(InvertedValue.Clockwise_Positive));
        m_rightMotor.setNeutralMode(NeutralModeValue.Brake);

        //zero the encoders
        m_leftMotor.setPosition(0);
        m_rightMotor.setPosition(0);
    }

    //called once per scheduler run
    @Override
    public void periodic() {
        updateDistance();
        checkControllerInputs();
        setSpeed(0); //default speeds to zero

        //two modes, manual and automatic, default to automatic, commands will be used to set this.
        if (manualMode) {
            //make sure we dont go below minimum height
            if (leftDistance <= minHeight || rightDistance <= minHeight) {
                setSpeed(m_controller.getRightTriggerAxis());                //only allow driving up
            }else if(leftDistance >= maxHeight || rightDistance <= maxHeight){
                setSpeed(-m_controller.getLeftTriggerAxis());                //only allow driving down
            }else{
                //allow driving up or down
                setSpeed(m_controller.getRightTriggerAxis() - m_controller.getLeftTriggerAxis());
            }
        } else {
            //automatic mode, use commands to set desired height, auto mode will drive to desired height
            if(currentHeight < (desiredHeight + error)){
                setSpeed(autoModeSpeed); //drive up
            }else if(currentHeight > (desiredHeight - error)){
                setSpeed(-autoModeSpeed); //drive down
            }else{
                setSpeed(0); //stop
            }
        }

        m_leftMotor.set(leftSpeed);
        m_rightMotor.set(rightSpeed);

    }

    public void setManualMode(boolean mode) {
        manualMode = mode;
    }

    private void setSpeed(double speed) {
        leftSpeed = speed;
        rightSpeed = speed;
    }

    public void setDesiredHeight(double height){
        desiredHeight = height;
    }

    private void updateDistance() {
        leftDistance = m_leftMotor.getPosition().getValueAsDouble();
        rightDistance = m_rightMotor.getPosition().getValueAsDouble();
        currentHeight = (leftDistance + rightDistance) / 2;
        SmartDashboard.putNumber("Left Elevator Encoder", m_leftMotor.getPosition().getValueAsDouble());
        SmartDashboard.putNumber("Right Elevator Encoder", m_rightMotor.getPosition().getValueAsDouble());
        SmartDashboard.putNumber("Current Height", currentHeight);
    }

    private void checkControllerInputs(){
        if(m_controller.getRightTriggerAxis() > 0.1 || m_controller.getLeftTriggerAxis() > 0.1){
            setManualMode(true);
        }
    }
}
