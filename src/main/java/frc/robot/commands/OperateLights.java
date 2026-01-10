package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Lights;
import frc.handlers.RobotStates;
import frc.robot.Constants;

public class OperateLights extends Command {
    private final Lights m_lights;
   // private final RobotStates.loaderMotor m_loadState;
   // private final RobotStates.shooterMotor m_shootState;
   private final RobotStates.lights m_lightState;

    public OperateLights(Lights lights, RobotStates.lights lightState){
        m_lights = lights;
        //m_loadState = loadState;
        //m_shootState = shootState;
        m_lightState = lightState;

        addRequirements(m_lights);
    }
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        //m_lights.setLED(Constants.LARSON_SCANNER_RED);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        switch (m_lightState) {
            case BREATH:
                m_lights.setLED(Constants.BREATH_RED);
                break;
            
            case HEARTBEAT:
                m_lights.setLED(Constants.HEARTBEAT_RED);
                break;
            
            case SHOT:
                m_lights.setLED(Constants.SHOT_RED);
                break;
        
            default:
                m_lights.setLED(Constants.RED);
                break;
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        //m_lights.setLED(Constants.LIGHT_CHASE_RED);
    }

    @Override
    public boolean isFinished() {
      return true;
    }
}
