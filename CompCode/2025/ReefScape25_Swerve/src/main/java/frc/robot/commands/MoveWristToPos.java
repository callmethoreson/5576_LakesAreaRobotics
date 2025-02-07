// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.ElevatorSubsystem.ElevatorStage;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class MoveWristToPos extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  public static enum WristPos{
    INTAKE,
    SHOOT,
    PARK
  }

  private final ShooterSubsystem m_subsystem;
  private final WristPos pos;

  public MoveWristToPos(ShooterSubsystem subsystem, WristPos pos) {
    m_subsystem = subsystem;
    this.pos = pos;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double dist = 0;

    //need case for each elevator stage, maps stage to height
    switch (pos) {
        case PARK:
            //move elevator to park
            dist = 0;
            break;
        case INTAKE:
            //move elevator to intake
            dist = 1;
            break;
        case SHOOT:
            //move elevator to first
            dist = 2;
            break;
        default:
            //move elevator to park
            break;
    }

    m_subsystem.setDesiredDist(dist);

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
