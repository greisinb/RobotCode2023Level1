// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import javax.swing.AbstractCellEditor;
import javax.swing.plaf.nimbus.AbstractRegionPainter;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import com.revrobotics.CANSparkMax; //imports the libary for Spark Max control over CAN
import com.revrobotics.CANSparkMaxLowLevel; //additional functionality for Spark motor controllers
import com.revrobotics.CANSparkMaxLowLevel.MotorType; //allows for brushless motor control
import com.revrobotics.RelativeEncoder; //allows the NEO ecoders to be used


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
 
private MecanumDrive robotDrive;
private Joystick translateStick;
private Joystick turningStick;
DoubleSolenoid wristSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0,1);
DoubleSolenoid brakeSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 2,3);
DoubleSolenoid gripSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 4,5);

WPI_TalonFX Frontleft = new WPI_TalonFX(1,"rio");
WPI_TalonFX Rearleft = new WPI_TalonFX(3,"rio");
WPI_TalonFX Frontright = new WPI_TalonFX(2,"rio");
WPI_TalonFX Rearright = new WPI_TalonFX(4,"rio");

final CANSparkMax armMotor = new CANSparkMax(5, MotorType.kBrushless); //creates and names a motor controller CAN ID 6 and makes it brushless
final CANSparkMax extensionMotor = new CANSparkMax(6, MotorType.kBrushless); //creates and names a motor controller CAN ID 6 and makes it brushless

@Override
  public void robotInit() {
    
    translateStick = new Joystick(0);
    turningStick = new Joystick(1);

    robotDrive = new MecanumDrive(Frontleft, Rearleft, Frontright, Rearright);
    Frontleft.setInverted(true);
    Rearleft.setInverted(true);
    
  }


  @Override
  public void robotPeriodic() {
    double frontLeftVal = Frontleft.getSelectedSensorVelocity(0); /* position units per 100ms */
    double rearLeftVal = Rearleft.getSelectedSensorVelocity(0);
    double frontRightVal = Frontright.getSelectedSensorVelocity(0);
    double rearRightVal = Rearright.getSelectedSensorVelocity(0);

    SmartDashboard.putNumber("Front Left Encoder", frontLeftVal); // displays the velocity of the wheels
    SmartDashboard.putNumber("Rear Left Encoder", rearLeftVal);
    SmartDashboard.putNumber("Front Right Encoder", frontRightVal);
    SmartDashboard.putNumber("Rear Right Encoder", rearRightVal);
  }

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {
    robotDrive.driveCartesian(translateStick.getY(), -translateStick.getX(), -turningStick.getX());
if(translateStick.getRawButtonPressed(1)){
  gripSolenoid.set(Value.kForward);
}
if(translateStick.getRawButtonReleased(1)){
  gripSolenoid.set(Value.kReverse);
}
if(translateStick.getRawButtonPressed(2)){
  wristSolenoid.set(Value.kForward);
}
if(translateStick.getRawButtonReleased(2)){
  wristSolenoid.set(Value.kReverse);
}
if(translateStick.getRawButtonPressed(3)){
  brakeSolenoid.set(Value.kForward);
}
if(translateStick.getRawButtonReleased(3)){
  brakeSolenoid.set(Value.kReverse);
}
if(translateStick.getRawButtonPressed(4)){
  armMotor.set(.5);
}
if(translateStick.getRawButtonReleased(4)){
  armMotor.set(0);
}
if(translateStick.getRawButtonPressed(5)){
  armMotor.set(-.5);
}
if(translateStick.getRawButtonReleased(5)){
  armMotor.set(0);
}
if(translateStick.getRawButtonPressed(6)){
  extensionMotor.set(1);
}
if(translateStick.getRawButtonReleased(6)){
  extensionMotor.set(0);
}
if(translateStick.getRawButtonPressed(7)){
  extensionMotor.set(-.1);
}
if(translateStick.getRawButtonReleased(7)){
  extensionMotor.set(0);
}
  }


  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}