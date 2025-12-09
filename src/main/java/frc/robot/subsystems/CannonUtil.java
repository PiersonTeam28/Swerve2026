// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.handlers.RobotStates;
import frc.handlers.RobotStates.loaderMotor;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;

import frc.robot.RobotContainer;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class CannonUtil extends SubsystemBase {
  /** Creates a new CannonUtil. */
  private TalonSRX shooter;
  private TalonSRX loader;

  public CannonUtil() {
    shooter = new TalonSRX(Constants.SHOOTER);    // changed names
    loader = new TalonSRX(Constants.LOADER);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setLoader(double motorSpeed, RobotStates.loaderMotor state) {
    RobotContainer.setLoaderState(state);

    switch (state) {
      case LOADING:
        loader.set(TalonSRXControlMode.PercentOutput, Constants.LOADING_SPEED);
        break;
      case LOADED:
        loader.set(TalonSRXControlMode.PercentOutput, Constants.LOADED_SPEED);
        break;
      case SHOOTING:
        loader.set(TalonSRXControlMode.PercentOutput, Constants.LOADED_SPEED);
        break;
      default:
        loader.set(TalonSRXControlMode.PercentOutput, 0.0);
        break;
    }
    //loader.set(TalonSRXControlMode.PercentOutput, motorSpeed);
  }

  public void setShooter(double motorSpeed, RobotStates.shooterMotor state) {
    RobotContainer.setShooterState(state);

    switch (state) {
      case LOADING:
        shooter.set(TalonSRXControlMode.PercentOutput, 0.0);
        break;
      case SHOT:
        shooter.set(TalonSRXControlMode.PercentOutput, Constants.SHOT_SPEED);
        break;
      case SHOOTING:
        shooter.set(TalonSRXControlMode.PercentOutput, Constants.SHOOTING_SPEED);
        break;
      default:
        shooter.set(TalonSRXControlMode.PercentOutput, 0.0);
        break;
    }
    //loader.set(TalonSRXControlMode.PercentOutput, motorSpeed);
  }
}
