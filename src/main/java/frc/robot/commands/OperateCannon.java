// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.handlers.RobotStates;
import frc.robot.Constants;
import frc.robot.Constants.CannonState;
import frc.robot.subsystems.CannonUtil;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class OperateCannon extends Command {
  /** Creates a new OperateShooter. */
 
  private final CannonUtil cannonUtil;
  private Constants.CannonState state;

  public OperateCannon(CannonUtil cannonUtil, Constants.CannonState state) {
    this.cannonUtil = cannonUtil;
    this.state = state;
    addRequirements(cannonUtil);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    switch(this.state){
      case LOAD_LONG:
        cannonUtil.setState(this.state);
        new WaitCommand(Constants.WAIT_LONG);
        cannonUtil.setIsLoadedTrue();
        break;
      case LOAD_SHORT:
        cannonUtil.setState(this.state);
        new WaitCommand(Constants.WAIT_SHORT);
        cannonUtil.setIsLoadedTrue();
        break;
      case SHOOT:
        cannonUtil.setState(this.state);
        break;
      default:
        cannonUtil.setState(Constants.CannonState.DEFAULT);
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(this.state == CannonState.LOAD_LONG ||
       this.state == CannonState.LOAD_SHORT){
        return cannonUtil.isLoaded();
       } else {
        return false;
       }
  }
}
