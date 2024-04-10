package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Autonomous_11points")
public class Autonomous_11points extends LinearOpMode {

  private DcMotor rightDrive;
  private DcMotor leftDrive;
  private Servo wrist;
  private Servo gripper;
  

  /**
   * This function is executed when this OpMode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    
    int rightMotorPosInit;
    int leftMotorPosInit;
    double counterStraight = 0;
    double counterLeft = 500;
    int wristUpPosition =1;
    int wristDownPosition =0;
    int gripperClosedPosition = 1;
    int gripperOpenPosition = 0;
    
    rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
    leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
    wrist = hardwareMap.get(Servo.class, "wrist");
    gripper = hardwareMap.get(Servo.class, "gripper");
    

    // Put initialization blocks here.
    //rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    //leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    
    rightDrive.setDirection(DcMotor.Direction.REVERSE);
    rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    gripper.setPosition(gripperClosedPosition);
    
  
    
    
    waitForStart();
    if (opModeIsActive()) {
      // Put run blocks here.
      //Get the current position of the motors
      rightMotorPosInit = rightDrive.getCurrentPosition();
      leftMotorPosInit = leftDrive.getCurrentPosition();
      
      
      //rightDrive.setPower(20);
      //leftDrive.setPower(20);
      wrist.setPosition(wristUpPosition);
        
      while (opModeIsActive()) {
        // Put loop blocks here.
        wrist.setPosition(wristUpPosition);
        //while(counter<5)
        //{
        if(counterStraight < 20000)
        {
          rightDrive.setPower(-2);
          leftDrive.setPower(-2);
        }
        else
        {
          rightDrive.setPower(0);
          leftDrive.setPower(0);
          counterLeft = 0;
          gripper.setPosition(gripperOpenPosition);
          if(counterLeft < 500)
          {
            //leftDrive.setPower(10);
            counterLeft++;
          }
          else
          {
            //leftDrive.setPower(0);
          }
        }
        counterStraight++;
        telemetry.addData("Counter Value is", counterStraight);
        //Move Left
        if(counterLeft < 500)
        {
          //leftDrive.setPower(10);
          counterLeft++;
        }
        else
        {
          //leftDrive.setPower(0);
        }
        //}
        
        telemetry.update();
      }
    }
  }
}
