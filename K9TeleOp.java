package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.*;
import com.qualcomm.robotcore.hardware.*;
/**
 * Created by KK on 11/26/2015.
 */
public class K9TeleOp extends OpMode {
    
    //initialize motors
    DcMotor motorRight;
    DcMotor motorLeft;
    DcMotor motorTape;
    DcMotor motorGrabber;
    
    public K9TeleOp(){

    }
    
    
    public void init(){
        //map motors
        motorLeft = hardwareMap.dcMotor.get("motor_1");
        motorRight = hardwareMap.dcMotor.get("motor_2");
        motorTape = hardwareMap.dcMotor.get("motor_3");
        motorGrabber = hardwareMap.dcMotor.get("motor_4");
        
        //reverse left so it matches right
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        motorTape = hardwareMap.
    }
    
    
    public void loop(){
        //get values from gamepad
        float left = -gamepad1.left_stick_y;
        float right = -gamepad1.right_stick_y;
        float power = -gamepad2.right_stick_y;
        float grabber = -gamepad2.left_stick_y;
        
        //clip values
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);
        power = Range.clip(power, -1, 1);
        grabber = Range.clip(grabber, -1, 1);
        
        //scale values
        right = (float)scaleInput(right);
        left =  (float)scaleInput(left);
        power = (float)scaleInput(power);
        grabber =  (float)scaleInput(grabber);
        
        //set values
        motorRight.setPower(right);
        motorLeft.setPower(left);
        motorTape.setPower(power);
        motorGrabber.setPower(grabber);
        
        //telemetry
        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("tape", "tape:  " + String.format("%.2f", power));
        telemetry.addData("grabber", "grabber:  " + String.format("%.2f", grabber));
        telemetry.addData("left tgt pwr", "left  pwr: " + String.format("%.2f", left));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));
    }
    
    
    public void stop(){

    }
    
    
    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);
        if (index < 0) {
            index = -index;
        } else if (index > 16) {
            index = 16;
        }

        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        return dScale;
    }
}
