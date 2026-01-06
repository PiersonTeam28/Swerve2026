// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

import frc.handlers.RobotStates;

import frc.robot.commands.OperateElevator;
import frc.robot.commands.OperateCannon;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.ElevatorUtil;
import frc.robot.subsystems.CannonUtil;

public class RobotContainer {
    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond)*0.25; // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond)*0.25; // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

    private static RobotStates.elevatorMotor elevatorState;
    private static RobotStates.loaderMotor loaderState;
    private static RobotStates.shooterMotor shooterState;

    private final ElevatorUtil elevatorUtil = new ElevatorUtil(Constants.ElevatorAngle.STOP);

    private final CannonUtil cannonUtil = new CannonUtil(Constants.CannonState.DEFAULT);

    private final CommandXboxController joystick = new CommandXboxController(0);
   
    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
        joystick.y().whileTrue(new OperateElevator(elevatorUtil, Constants.ElevatorAngle.UP));
        joystick.a().whileTrue(new OperateElevator(elevatorUtil, Constants.ElevatorAngle.DOWN));
        joystick.x().onTrue(new OperateCannon(cannonUtil, Constants.CannonState.LOAD_SHORT));
        joystick.b().onTrue(new OperateCannon(cannonUtil, Constants.CannonState.LOAD_LONG));
        joystick.rightTrigger().onTrue(new OperateCannon(cannonUtil, Constants.CannonState.SHOOT));
    
     }
    
     private void setDefaultCommands(){
        cannonUtil.setDefaultCommand(new OperateCannon(cannonUtil, Constants.CannonState.DEFAULT));
        elevatorUtil.setDefaultCommand(new OperateElevator(elevatorUtil, Constants.ElevatorAngle.STOP));
     }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }

    public static void setElevatorState(RobotStates.elevatorMotor state){
        elevatorState = state;
    }

    public static void setLoaderState(RobotStates.loaderMotor state){
        loaderState = state;
    }

    public static void setShooterState(RobotStates.shooterMotor state){
        shooterState = state;
    }

    public static RobotStates.elevatorMotor getElevatorState(){
        return elevatorState;
    }

    public static RobotStates.loaderMotor getLoaderState(){
        return loaderState;
    }

    public static RobotStates.shooterMotor getShooterState(){
        return shooterState;
    }
}