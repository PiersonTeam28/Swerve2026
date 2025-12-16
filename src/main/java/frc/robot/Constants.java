// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/** Add your docs here. */
public class Constants {
    /*
     * CAN ID Mappings
     */
    public static final int ELEVATOR = 15;
    public static final int LOADER = 16; // changed trigger to LOADER
    public static final int SHOOTER = 20; //changed SOLENOID to SHOOTER

    // Swerve Drive CAN IDs
    public static final int FRONT_LEFT_ANGLEMOTOR = 9;
    public static final int FRONT_LEFT_DRIVEMOTOR = 3;
    public static final int FRONT_LEFT_ABSOLUTE_ENCODER = 10;
    public static final int FRONT_LEFT_ABSOLUTE_ANGLE_OFFSET = 9999;

    public static final int FRONT_RIGHT_ANGLEMOTOR = 4;
    public static final int FRONT_RIGHT_DRIVEMOTOR = 5;
    public static final int FRONT_RIGHT_ABSOLUTE_ENCODER = 11;
    public static final int FRONT_RIGHT_ABSOLUTE_ANGLE_OFFSET = 9999;
    
    public static final int BACK_LEFT_ANGLEMOTOR = 8;
    public static final int BACK_LEFT_DRIVEMOTOR = 2;
    public static final int BACK_LEFT_ABSOLUTE_ENCODER = 12;
    public static final int BACK_LEFT_ABSOLUTE_ANGLE_OFFSET = 9999;

    public static final int BACK_RIGHT_ANGLEMOTOR = 35;
    public static final int BACK_RIGHT_DRIVEMOTOR = 30;
    public static final int BACK_RIGHT_ABSOLUTE_ENCODER = 13;
    public static final int BACK_RIGHT_ABSOLUTE_ANGLE_OFFSET = 9999;

    public static final int PIGEON_2 = 14;

    public static final int PDP = 28;

    /*
     * Elevator Constants
     */
    public static final double SPEED_UP = 1.0;
    public static final double SPEED_DOWN = -1.0;
    public static final double ELEVATOR_STOP = 0.0;
}
