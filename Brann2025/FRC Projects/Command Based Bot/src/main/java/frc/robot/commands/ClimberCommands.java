package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.ClimberSubsystem;

public class ClimberCommands {
    public static Command runBackwards(ClimberSubsystem climber)
    {
        return Commands.runOnce(() -> climber.climberDown(-1));
    }
    public static Command runForwards(ClimberSubsystem climber)
    {
        return Commands.runOnce(() -> climber.climberUp(1));
    }
}
