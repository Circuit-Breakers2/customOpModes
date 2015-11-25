package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
/**
 * Created by KK on 10/17/2015.
 */
public class ServoTEST extends OpMode {
    Servo arm;
    public ServoTEST() {
    }
    @Override
    public void init() {
        arm = hardwareMap.servo.get("servo_1");
    }
    @Override
    public void loop() {
        float thorttle = gamepad1.left_stick_x;
        arm.setPosition((thorttle / 2) + 0.5);
    }
    @Override
    public void stop() {
    }
}