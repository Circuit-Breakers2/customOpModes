package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.*;
import com.qualcomm.robotcore.hardware.*;
/**
 * Created by KK on 12/5/2015.
 */
public class AutoB1 extends OpMode{

    int count = 0;
    DcMotor motorRight;
    DcMotor motorLeft;
    final static int ENCODER_CPR = 1440;        //Encoder counts per rotation
    final static double GEAR_RATIO = 1.5;       //How many rotations from motor gear to wheel gear (80 to 120)
    final static double CIRCUMFERENCE = 12.56;

    public AutoB1(){

    }

    public void init(){
        //Map motors
        motorLeft = hardwareMap.dcMotor.get("motor_1");
        motorRight = hardwareMap.dcMotor.get("motor_2");

        //Reset encoders to get accurate value
        motorRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);
    }

    public void start(){

    }
    
    public void loop(){
        motorRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        double x = turnDrive(13);
        while (motorLeft.getCurrentPosition() < x && motorRight.getCurrentPosition() < x){
            motorLeft.setPower(1);
            motorRight.setPower(1);
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);

        motorRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        x = turnDegrees(45);
        if(x <= 0) while (motorRight.getCurrentPosition() < -x) motorRight.setPower(1);
        if(x > 0) while (motorLeft.getCurrentPosition() < x) motorLeft.setPower(1);
        motorLeft.setPower(0);
        motorRight.setPower(0);

        motorRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        x = turnDrive(61);
        while (motorLeft.getCurrentPosition() < x && motorRight.getCurrentPosition() < x){
            motorLeft.setPower(1);
            motorRight.setPower(1);
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }
    public void stop() {

    }
    public double turnDrive(double dist) {
        telemetry.addData("Status: Drive ", dist);
        double rotations = dist / CIRCUMFERENCE;
        double finalCounts = ENCODER_CPR * rotations * GEAR_RATIO;
        return finalCounts;
    }

    public double turnDegrees(float turnDist) {
        telemetry.addData("Status: Turn ", turnDist);
        double turnConstant = 0.2965 * turnDist;
        double rotations = turnConstant / CIRCUMFERENCE;
        double finalCounts = ENCODER_CPR * rotations * GEAR_RATIO;
        return finalCounts;
    }
}
