package frc.robot.commands;

import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;

public class ShooterCommands extends Command{
    private ShooterSubsystem m_subsystem;
        private boolean finished;
        
        public void Manipulator(ShooterSubsystem subsystem) {
        m_subsystem = subsystem;
        finished = false;
        addRequirements(subsystem);
    }
    @Override
    public void initialize() {}

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return finished;
    }
}