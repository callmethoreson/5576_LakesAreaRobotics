package frc.robot.subsystems;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

public class ClimberSubsystem {
    private final SparkMax m_climber = new SparkMax(-1,MotorType.kBrushless);
    //private final SparkMax m_rightMotor = new SparkMax(-1,MotorType.kBrushless);
    private CommandXboxController m_controller;

    private double speed = 0;
    //private double rightSpeed = 0;
    public ClimberSubsystem(CommandXboxController controller) {
        m_controller = controller;

    }
    public void climberUp(double speed) {
    m_climber.set(speed);
    }
    public void climberDown(double speed){
    m_climber.set(speed);
    }

    //called once per scheduler run
    public void periodic() {
        m_climber.set(speed);

    }
}