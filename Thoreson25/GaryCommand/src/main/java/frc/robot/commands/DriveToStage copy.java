// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class DriveToStage extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Shooter m_shooter;
  private int desStage;
  private int curStage;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DriveToStage(Shooter shooter, int desStage) {
    m_shooter = shooter;
    this.desStage = desStage;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //determine setpoint and direction to go based on encoder value from the shooter
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    curStage = m_shooter.getStage();

    System.out.println("Stage " + curStage) ;


    if(curStage < desStage){
      m_shooter.setSpeed(1);
    }else if(curStage > desStage){
      m_shooter.setSpeed(-1);
    }else{
      m_shooter.setSpeed(0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(curStage == desStage){
      return true;
    }
    return false;
  }
}
