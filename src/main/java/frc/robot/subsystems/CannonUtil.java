// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.handlers.RobotStates;
import frc.handlers.RobotStates.loaderMotor;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;

import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class CannonUtil extends SubsystemBase {
  /** Creates a new CannonUtil. */
  private TalonSRX shooter;
  private TalonSRX loader;
  private Constants.CannonState state;
  private boolean isLoaded;
  private Encoder encoder;

  public CannonUtil(Constants.CannonState state) {
    this.state = state;
    this.isLoaded = false;
    encoder = new Encoder(Constants.SHOT_ENCODER_CHANNEL_A, Constants.SHOT_ENCODER_CHANNEL_B);
    shooter = new TalonSRX(Constants.SHOOTER);   
    loader = new TalonSRX(Constants.LOADER);
  }

  public void setState(Constants.CannonState state){
    this.state = state;
    switch(this.state){
      case SHOOT:
        shoot();
        break;
      case LOAD_SHORT:
        loadShort();
        break;
      case LOAD_LONG:
        loadLong();
        break;
      default:
        doNotLoadAir();
        doNotShoot();
        break;    
    }
  }

  public boolean isLoaded(){
    return this.isLoaded;
  }

  public void setIsLoadedTrue(){
    this.isLoaded = true;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  private void shoot(){
    if(isLoaded){
      shooter.set(TalonSRXControlMode.PercentOutput, Constants.CANNON_SHOOT);
      isLoaded = false;
    }
  }

  private void doNotShoot(){
    shooter.set(TalonSRXControlMode.PercentOutput, Constants.CANNON_DEFAULT);
  }

  private void loadShort(){
    if(!isLoaded){
      loader.set(TalonSRXControlMode.PercentOutput, Constants.CANNON_LOAD);
    }
  }

  private void loadLong(){
    if(!isLoaded){
      loader.set(TalonSRXControlMode.PercentOutput, Constants.CANNON_LOAD);
   }
  }

  private void doNotLoadAir(){
    loader.set(TalonSRXControlMode.PercentOutput, Constants.CANNON_DEFAULT);
  }
}