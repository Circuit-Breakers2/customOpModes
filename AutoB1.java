package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.*;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by arunavgupta on 12/5/15.
 */
public class AutoB1 extends LinearOpMode{

    //Initialize DC motors
    DcMotor motorLeft;
    DcMotor motorRight;
    //DcMotor motorGrabber;
    //---------Encoder configuration-------------
    //Preferences
    final static int ENCODER_CPR = 1440;        //Encoder counts per rotation
    final static double GEAR_RATIO = 1.5;       //How many rotations from motor gear to wheel gear (80 to 120)
    final static double CIRCUMFERENCE = 12.56;  //In inches
    //-------------------------------------------
    @Override
    public void runOpMode() throws InterruptedException {

        //Map drive motors
        motorLeft = hardwareMap.dcMotor.get("motor_2");
        motorRight = hardwareMap.dcMotor.get("motor_1");
        //motorGrabber = hardwareMap.dcMotor.get("motor_3");

        //Reverse right motor
        motorLeft.setDirection(DcMotor.Direction.REVERSE);

        //Reset encoders
        motorRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        waitForStart();

        while (opModeIsActive()) {
            drive(13);
            turn(45);
            drive(61);

            waitOneFullHardwareCycle();
        }

    }

    public void drive(float dist) {

        //Reset encoders
        motorRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        telemetry.addData("Status: Drive", dist);

        double rotations = dist / CIRCUMFERENCE;
        double finalCounts = ENCODER_CPR * rotations * GEAR_RATIO;

        /*while(motorLeft.getCurrentPosition() < finalCounts && motorRight.getCurrentPosition() < finalCounts){
            motorRight.setPower(1);
            motorLeft.setPower(1);
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);*/

        motorLeft.setTargetPosition(2235);
        motorRight.setTargetPosition(2235);

        //Make motors run to that position
        motorLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        //Set motor power
        motorLeft.setPower(1);
        motorRight.setPower(1);

        //Spin grabber outward
        //motorGrabber.setPower(-1);
    }

    public void turn(float turnDist) {

        //Reset encoders
        motorRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        double turnConstant = 0.2965 * turnDist;

        double rotations = turnConstant / CIRCUMFERENCE;
        double finalCounts = ENCODER_CPR * rotations * GEAR_RATIO;

        //NOTE: positive turnConstant = right, negative turnConstant = left
        if (turnDist >= 0) {
            motorLeft.setTargetPosition(((int) finalCounts));
            motorLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

            motorLeft.setPower(1);
            motorRight.setPower(0);
        }

        if(turnDist < 0) {
            motorRight.setTargetPosition(-1 * (int) finalCounts);
            motorRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

            motorLeft.setPower(0);
            motorRight.setPower(1);
        }


    }
}
