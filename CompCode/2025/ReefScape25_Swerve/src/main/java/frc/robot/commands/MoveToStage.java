// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.ElevatorSubsystem.ElevatorStage;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class MoveToStage extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final ElevatorSubsystem m_subsystem;
  private final ElevatorStage stage;

  public MoveToStage(ElevatorSubsystem subsystem, ElevatorStage stage) {
    m_subsystem = subsystem;
    this.stage = stage;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double height = 0;

    //need case for each elevator stage, maps stage to height
    switch (stage) {
        case PARK:
            //move elevator to park
            height = 0;
            break;
        case INTAKE:
            //move elevator to intake
            height = 1;
            break;
        case FIRST:
            //move elevator to first
            height = 2;
            break;
        case SECOND:
            //move elevator to second
            height = 3;
            break;
        case THIRD:
            //move elevator to third
            height = 4;
            break;
        case FOURTH:
            //move elevator to fourth
            height = 5;
            break;
        default:
            //move elevator to park
            break;
    }

    m_subsystem.setDesiredHeight(height);
    m_subsystem.setManualMode(false); //want to run in auto mode

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
