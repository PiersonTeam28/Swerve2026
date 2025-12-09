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
import frc.robot.commands.OperateShooter;
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

    private final ElevatorUtil elevatorUtil = new ElevatorUtil();

    private final CannonUtil cannonUtil = new CannonUtil();




    private final CommandXboxController joystick0 = new CommandXboxController(0);

    private final CommandXboxController joystick1 = new CommandXboxController(1); 
   
    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-joystick0.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-joystick0.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-joystick0.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );

        // Idle while the robot is disabled. This ensures the configured
        // neutral mode is applied to the drive motors while disabled.
        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
            drivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );

        joystick0.a().whileTrue(drivetrain.applyRequest(() -> brake));
        joystick0.b().whileTrue(drivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-joystick0.getLeftY(), -joystick0.getLeftX()))
        ));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        joystick0.back().and(joystick0.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        joystick0.back().and(joystick0.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        joystick0.start().and(joystick0.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        joystick0.start().and(joystick0.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // reset the field-centric heading on left bumper press
        joystick0.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));


        // second controller for elevator, loader and shooter

        // y moves elevator up
        joystick1.y().onTrue(new OperateElevator(elevatorUtil, "up")).onFalse(new OperateElevator(elevatorUtil, "stop"));

        // a moves elevator down
        joystick1.a().onTrue(new OperateElevator(elevatorUtil, "down")).onFalse(new OperateElevator(elevatorUtil, "stop"));

        // x loads shot 
        joystick1.x().onTrue(new OperateShooter(cannonUtil, RobotStates.loaderMotor.LOADING, RobotStates.shooterMotor.LOADING));

        // right bumper shoots
        joystick1.rightBumper().onTrue(new OperateShooter(cannonUtil, RobotStates.loaderMotor.SHOOTING, RobotStates.shooterMotor.SHOOTING));

        drivetrain.registerTelemetry(logger::telemeterize);
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
