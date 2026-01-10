package frc.robot.subsystems;

import frc.handlers.RobotStates;
import frc.robot.RobotContainer;
import frc.robot.Constants;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Lights extends SubsystemBase{
    private Spark blinkin;

    public Lights(){
        blinkin = new Spark(Constants.BLINKIN);
    }

    @Override
    public void periodic() {
      // This method will be called once per scheduler run
    }

    public void setLED(double value){
        blinkin.set(value);
    }

}
