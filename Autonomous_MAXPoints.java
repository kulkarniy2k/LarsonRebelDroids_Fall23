package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Autonomous_MAXpoints")
public class Autonomous_MAXpoints extends LinearOpMode {

  private DcMotor rightDrive;
  private DcMotor leftDrive;
  private Servo wrist;
  private Servo gripper;
  private DcMotor armRight;
  private DcMotor armLeft;

  /**
   * This function is executed when this OpMode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    
    int rightMotorPosInit;
    int leftMotorPosInit;
    double counterStraight = 0;
    double keepGripperClosedTimer = 0;
    int wristUpPosition =1;
    int wristDownPosition =0;
    int gripperClosedPosition = 1;
    int gripperOpenPosition = 0;
    int ReachedDestination = 0;
    int armIntakePosition = 50;
    int armScorePosition = 500;
    int armDropPosition = -560;
    int powerToMotor = 1;
    int OnlyOneTime=0;
    int armShutdownThreshold = 5;
    int arm_moving = 0;
    int Timer_WaitToGoToIntakePosition = 0;
    
    int Pixel_Dropped = 0;

    int ReturnArm_moving = 0;
    int DidArmReturnTimer = 0;
    
    rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
    leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
    wrist = hardwareMap.get(Servo.class, "wrist");
    gripper = hardwareMap.get(Servo.class, "gripper");
    armRight = hardwareMap.get(DcMotor.class, "armRight");
    armLeft = hardwareMap.get(DcMotor.class, "armLeft");

    // Put initialization blocks here.
    //rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    //leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    
    rightDrive.setDirection(DcMotor.Direction.REVERSE);
    armRight.setDirection(DcMotor.Direction.REVERSE);
    
    rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    gripper.setPosition(gripperClosedPosition);
    armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      armLeft.setTargetPosition(0);
      armRight.setTargetPosition(0);
  
  
      armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    armLeft.setPower(0);
    armRight.setPower(0);
    
    
    waitForStart();
    if (opModeIsActive()) {
      // Put run blocks here.
      //Get the current position of the motors
      rightMotorPosInit = rightDrive.getCurrentPosition();
      leftMotorPosInit = leftDrive.getCurrentPosition();
      
      
      //rightDrive.setPower(20);
      //leftDrive.setPower(20);
      wrist.setPosition(wristUpPosition);
        
      while (opModeIsActive()) 
      {
        // Put loop blocks here.
        
        wrist.setPosition(wristUpPosition);

        //Go straight towards landing area
        if(counterStraight < 22500)//24000
        {
          rightDrive.setPower(2);
          leftDrive.setPower(2);
        }
        else
        {
          
          
          ReachedDestination = 1;
          rightDrive.setPower(0);
          leftDrive.setPower(0);
          //keepGripperClosedTimer = 0;
          
        }
        
        
        
        if((ReachedDestination ==1) && (OnlyOneTime ==0))
        {
          telemetry.addData("robot reached destination ReachedDestination =", ReachedDestination);

          OnlyOneTime = 1;
          
            //Go to drop the pixels
            armLeft.setTargetPosition(armDropPosition);
            armRight.setTargetPosition(armDropPosition);
            armLeft.setPower(powerToMotor);
            armRight.setPower(powerToMotor);
            armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            wrist.setPosition(wristUpPosition);
            keepGripperClosedTimer = 0;
            ReachedDestination = 0;
            telemetry.addData("Current position", armLeft.getCurrentPosition());
            telemetry.addData("Target position", armLeft.getTargetPosition());
            
            arm_moving = 1;
          
        
        }
        counterStraight++;
        telemetry.addData("keepGripperClosedTimer", keepGripperClosedTimer);
        //Move Left
        
        if(arm_moving == 1)
        {
          if(keepGripperClosedTimer > 150000)
          {
            //open the gripper on arm reaching dropping position
            gripper.setPosition(gripperOpenPosition);
            keepGripperClosedTimer = 0;
            arm_moving = 0;
            
            Pixel_Dropped = 1;
            
          }
          else
          {
            keepGripperClosedTimer++;
          }
        }
        

        if(Pixel_Dropped == 1)
        {
            Timer_WaitToGoToIntakePosition++;
            if(Timer_WaitToGoToIntakePosition > 50000)
            { 
              //Go back to arm intake position
              armLeft.setTargetPosition(armIntakePosition);
              armRight.setTargetPosition(armIntakePosition);
              armLeft.setPower(powerToMotor);
              armRight.setPower(powerToMotor);
              armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
              armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
              ReturnArm_moving = 1;
              Pixel_Dropped = 0;
            }
        }
        
        if(ReturnArm_moving == 1)
        {
          DidArmReturnTimer++;
          if(DidArmReturnTimer > 150000)
          {
            armLeft.setPower(0);
            armRight.setPower(0);
            ReturnArm_moving = 0;
          }
        }
        
        
        
        //}
        
       /*if((arm_moving ==1) &&
         ((Math.abs(armLeft.getTargetPosition() - armLeft.getCurrentPosition())  )<= armShutdownThreshold))
       {
          armLeft.setPower(0);
          armRight.setPower(0);
          armLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
          armRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
          arm_moving = 0;
          //telemetry.addData("WATCHDOG OPERATED =", armLeft.getCurrentPosition());
          
        }*/
        
        
        
        telemetry.update();
      }
    }
  }
}
