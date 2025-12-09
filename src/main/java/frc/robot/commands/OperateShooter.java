// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.handlers.RobotStates;
import frc.robot.subsystems.CannonUtil;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class OperateShooter extends Command {
  /** Creates a new OperateShooter. */
  //private final CannonUtil m_shooter;
  //private final CannonUtil m_loader;
  private final CannonUtil m_cannon;
  RobotStates.loaderMotor m_loadState;
  RobotStates.shooterMotor m_shootState;


  public OperateShooter(CannonUtil cannon, RobotStates.loaderMotor loadState, RobotStates.shooterMotor shootState) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_cannon = cannon;
    m_loadState = loadState;
    m_shootState = shootState;

    addRequirements(m_cannon);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // we need to load and then shoot, make sure it is loaded before shooting and empty before loading
    switch (m_loadState) {
      case LOADING:
        
        break;
      case LOADED:

        break;
      case SHOOTING:

        break;
      default:
        break;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
