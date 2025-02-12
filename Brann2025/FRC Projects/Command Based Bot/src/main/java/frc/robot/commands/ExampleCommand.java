// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.FalconSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

/** An example command that uses an example subsystem. */
public class ExampleCommand extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final FalconSubsystem m_subsystem;
  private final double desiredDistance;
  private boolean finished;
  private double error;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ExampleCommand(FalconSubsystem subsystem, double absDistance) {
    m_subsystem = subsystem;
    desiredDistance = absDistance;
    finished = false;
    error = 1;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // double position = m_subsystem.getPosition();

    // if (position >= desiredDistance + error) {
    //   //go backwards
    //   m_subsystem.moveBackward();
    // }else if(position <= desiredDistance - error){
    //   //go forwards
    //   m_subsystem.moveForward();
    // }else{
    //   m_subsystem.moveMotor(0);
    //   finished = true;
    // }

    m_subsystem.setDesiredDistance(desiredDistance);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finished;
  }
}
