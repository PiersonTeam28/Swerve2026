package frc.handlers;

public class RobotStates {
    
    //states for the shooter as a whole, may not use all of these but it should be useful for loading/shooting sequence

    public enum elevatorMotor{
        UP,
        DOWN,
        STOP;
    }

    public enum loaderMotor{
        LOADING, // active when loading
        LOADED, //stop when loaded
        SHOOTING, //should be stopped while shooting
        EMPTY; // button was pressed and air discharged
    }

    public enum shooterMotor{
        LOADING, // should be stopped while loading
        SHOOTING, // active when shooting
        SHOT; // shot success meaning trigger was pressed
    }
}
