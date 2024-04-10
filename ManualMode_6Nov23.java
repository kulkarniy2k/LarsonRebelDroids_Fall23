package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "ManualMode_6Nov23")
public class ManualMode_6Nov23 extends LinearOpMode {

  private DcMotor rightDrive;
  private DcMotor armRight;
  private DcMotor armLeft;
  private DcMotor leftDrive;
  private Servo wrist;
  private Servo gripper;
  private Servo drone;

  /**
   * This function is executed when this OpMode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    int homearmposition;
    int armIntakePosition;
    int armScorePosition;
    int armDropPosition;
    boolean manualMode;
    double armManualDeadband;
    int armShutdownThreshold;
    int armSetpoint;
    int wristUpPosition;
    int wristDownPosition;
    int WristDownCounter;
    int gripperClosedPosition;
    double gripperOpenPosition;
    int running;
    float manualArmPower;
    double powerToMotor;

    rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
    armRight = hardwareMap.get(DcMotor.class, "armRight");
    armLeft = hardwareMap.get(DcMotor.class, "armLeft");
    leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
    wrist = hardwareMap.get(Servo.class, "wrist");
    gripper = hardwareMap.get(Servo.class, "gripper");
    drone = hardwareMap.get(Servo.class, "drone");

    homearmposition = 0;
    armIntakePosition = 50;
    armScorePosition = 500;
    armDropPosition = -560;
    manualMode = false;
    armManualDeadband = 0.03;
    armShutdownThreshold = 5;
    armSetpoint = 0;
    wristUpPosition = 1;
    wristDownPosition = 0;
    WristDownCounter = 0;
    gripperClosedPosition = 1;
    gripperOpenPosition = 0;
    running = 0;
    //powerToMotor = 0.8;
    powerToMotor = 1;
    
    // You will need to reverse one of the drive motors.
    rightDrive.setDirection(DcMotor.Direction.REVERSE);
    armRight.setDirection(DcMotor.Direction.REVERSE);
    armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    armLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    armRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    armLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    armRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    armLeft.setPower(0);
    armRight.setPower(0);
    telemetry.addData("Status", "Initialized");
    waitForStart();
    if (opModeIsActive()) {
      armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      armLeft.setTargetPosition(0);
      armRight.setTargetPosition(0);
      armLeft.setPower(1);
      armRight.setPower(1);
      armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
       drone.setPosition(1);
      
      while (opModeIsActive()) {
        
        if(wrist.getPosition() != wristDownPosition)
        {
          // Show the motor's status via telemetry
          // Split Arcade Drive
          // Use left stick to drive and right stick to turn!
          rightDrive.setPower((gamepad2.left_stick_y + gamepad2.right_stick_x)*0.6);
          leftDrive.setPower((gamepad2.left_stick_y - gamepad2.right_stick_x)*0.6);
        }
        
        
        
       // telemetry.addData("leftDrive = ", leftDrive.getPower());
      //  telemetry.addData("rightDrive = ", rightDrive.getPower());
        
        // Manual Arm Control
        manualArmPower = gamepad1.right_trigger - gamepad1.left_trigger;
        if (Math.abs(manualArmPower) > armManualDeadband) {
          if (!manualMode) {
            armLeft.setPower(0);
            armRight.setPower(0);
            armLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            armRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            manualMode = true;
            running = 0;
          }
          armLeft.setPower(manualArmPower*1);
          armRight.setPower(manualArmPower*1);
        } else {
          if (manualMode) {
            armLeft.setTargetPosition(armLeft.getCurrentPosition());
            armRight.setTargetPosition(armRight.getCurrentPosition());
            armLeft.setPower(powerToMotor);
            armRight.setPower(powerToMotor);
            armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            manualMode = false;
            running = 1;
          }
        }
        // Arm and Wrist Presets
        if (gamepad1.cross) {
          armLeft.setTargetPosition(homearmposition);
          armRight.setTargetPosition(homearmposition);
          armLeft.setPower(powerToMotor);
          armRight.setPower(powerToMotor);
          armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
          armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
          wrist.setPosition(wristUpPosition);
          running = 1;
        } else if (gamepad1.circle) {
          armLeft.setTargetPosition(armIntakePosition);
          armRight.setTargetPosition(armIntakePosition);
          armLeft.setPower(powerToMotor);
          armRight.setPower(powerToMotor);
          armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
          armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
          gripper.setPosition(gripperOpenPosition); //ensure that gripper is open when wrist is down
          wrist.setPosition(wristDownPosition);
          running = 1;
        } else if (gamepad1.triangle) {
            armLeft.setTargetPosition(armScorePosition);
            armRight.setTargetPosition(armScorePosition);
            armLeft.setPower(powerToMotor);
            armRight.setPower(powerToMotor);
            armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            wrist.setPosition(wristUpPosition);
            running = 1;
          }
          
          else if(gamepad1.dpad_up)
          {
            // Manual Wrist Control
            wrist.setPosition(wristUpPosition);
            //telemetry.addData("dpad UP is pressed", armLeft.getTargetPosition());
            
            running = 1;
          }
          
          else if(gamepad1.dpad_down)
          {
            gripper.setPosition(gripperClosedPosition); //ensure that gripper is open when wrist is down
            // Manual Wrist Control
            wrist.setPosition(wristDownPosition);
            telemetry.addData("dpad down is pressed", armLeft.getTargetPosition());
            
            WristDownCounter=500;
            gripper.setPosition(gripperOpenPosition);
            running = 1;
          }
          else if(gamepad1.dpad_right)
          {
            drone.setPosition(-1);
          }
          else if(gamepad1.dpad_right)
          {
            drone.setPosition(0);
          }
          else if (gamepad1.square) {
            
            armLeft.setTargetPosition(armDropPosition);
            armRight.setTargetPosition(armDropPosition);
            armLeft.setPower(powerToMotor);
            armRight.setPower(powerToMotor);
            armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            wrist.setPosition(wristUpPosition);
            //telemetry.addData("getTargetPosition =", armLeft.getTargetPosition());
            //telemetry.addData("getCurrentPosition =", armLeft.getCurrentPosition());
            running = 1;
          }
        
        // Re-zero Encoder Button
        if (gamepad1.options) {
          armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
          armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
          armLeft.setPower(0);
          armRight.setPower(0);
          manualMode = false;
        }
        //telemetry.addData("getCurrentPosition =", armLeft.getCurrentPosition());
        //telemetry.addData("getTargetPosition =", armLeft.getTargetPosition());
        
        
        // Watchdog to shut down motor once arm reaches the home position
       /* if (!manualMode && running == 1 && armLeft.getTargetPosition() <= armShutdownThreshold && armLeft.getCurrentPosition() <= armShutdownThreshold) */
       
       if ((!manualMode && running == 1) && 
       //(Math.abs(armLeft.getTargetPosition()) - Math.abs(armLeft.getCurrentPosition())  )<= armShutdownThreshold)
       (Math.abs(armLeft.getTargetPosition() - armLeft.getCurrentPosition())  )<= armShutdownThreshold)
       {
          armLeft.setPower(0);
          armRight.setPower(0);
          armLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
          armRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
          //telemetry.addData("WATCHDOG OPERATED =", armLeft.getCurrentPosition());
          running = 0;
        }
        // Gripper
        if (gamepad1.left_bumper || gamepad1.right_bumper || (WristDownCounter != 0)) {
          gripper.setPosition(gripperOpenPosition);
        } else {
          gripper.setPosition(gripperClosedPosition);
        }
        
        if(WristDownCounter != 0)
        {
          WristDownCounter--;
        }

        
        telemetry.update();
      }
    }
  }
}
