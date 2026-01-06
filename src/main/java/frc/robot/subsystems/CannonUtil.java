// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.handlers.RobotStates;
import frc.handlers.RobotStates.loaderMotor;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import edu.wpi.first.wpilibj.Encoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.RobotContainer;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class CannonUtil extends SubsystemBase {
  /** Creates a new CannonUtil. */
  private TalonSRX shooter;
  private TalonSRX loader;
  private Encoder encoder;
  private double enc;
  private double buffer;

  public CannonUtil() {
    shooter = new TalonSRX(Constants.SHOOTER);    // changed names
    loader = new TalonSRX(Constants.LOADER);
    encoder = new Encoder(Constants.SHOT_ENCODER_CHANNEL_A, Constants.SHOT_ENCODER_CHANNEL_B);
    encoder.setDistancePerPulse(360.0/(7.0*71.0));
    encoder.reset();
    buffer = 10.0;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    updateDashboard();
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
      case EMPTY:
        loader.set(TalonSRXControlMode.PercentOutput, 0);
        break;
      default:
        loader.set(TalonSRXControlMode.PercentOutput, 0);
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
        case REVERSE:
        shooter.set(TalonSRXControlMode.PercentOutput, Constants.SHOOTING_SPEED*-1.0);
        break;
      case HOMING:
        if((encoder.getDistance() % 360.0)>0){
          shooter.set(TalonSRXControlMode.PercentOutput, Constants.HOMING_SPEED*-1.0);
        }
        else if((encoder.getDistance() % 360.0)<0){
          shooter.set(TalonSRXControlMode.PercentOutput, Constants.HOMING_SPEED);

        }
        else if((encoder.getDistance() % 360.0)==0){
          shooter.set(TalonSRXControlMode.PercentOutput, 0.0);

        }
        break;
        case SHOOTS:
        if((encoder.getDistance() % 360.0)>buffer){
          shooter.set(TalonSRXControlMode.PercentOutput, Constants.HOMING_SPEED*1.0);
        }
        else if(((encoder.getDistance() % 360.0)<=buffer)&&((encoder.getDistance() % 360.0)>=buffer*-1.0)){
          shooter.set(TalonSRXControlMode.PercentOutput, 0.0);
        }
        break;
      default:
        shooter.set(TalonSRXControlMode.PercentOutput, 0.0);
        break;
    }
    
    //loader.set(TalonSRXControlMode.PercentOutput, motorSpeed);
  }

  private void updateDashboard(){
    
    SmartDashboard.putNumber("Encoder Rate :: ", encoder.getRate());
    SmartDashboard.putNumber("Encoder Period :: ", encoder.getPeriod());
    SmartDashboard.putNumber("Encoder Distance Per Pulse :: ", encoder.getDistancePerPulse());
    SmartDashboard.putNumber("Encoder Angle :: ", encoder.getDistance() % 360.0);

    SmartDashboard.putBoolean("Encoder Direction :: ", encoder.getDirection());
    enc = encoder.get();
    SmartDashboard.putNumber("Enc :: ", enc);

    SmartDashboard.putNumber("LOADER MOTOR PERCENT", loader.getMotorOutputPercent());
    SmartDashboard.putNumber("SHOOTER MOTOR PERCENT", shooter.getMotorOutputPercent());
  }
}

//27:1 gear ratio or 71:1 or 188:1
// 7 pulses per revolution
//