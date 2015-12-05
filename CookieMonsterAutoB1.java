package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.*;
/**
 * Created by arunavgupta on 12/4/15.
 */
public class CookieMonsterAutoB1 extends OpMode{

    //Initialize DC motors
    DcMotor motorLeft;
    DcMotor motorRight;

    //Initialize servo
    Servo basketTilt;

    //Initialize state machine
    enum State {drivingStraight, turning, enteringGoal, actionDone};
    State state;


    @Override
    public void init() {

        //Map drive motors
        motorLeft = hardwareMap.dcMotor.get("motor_1");
        motorRight = hardwareMap.dcMotor.get("motor_2");

        //Reverse right motor
        motorLeft.setDirection(DcMotor.Direction.REVERSE);

        //Reset / map encoders
        motorRight.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorLeft.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

        //Set state
        state = State.drivingStraight;

    }


    public void loop() {

        switch (state) {

            case drivingStraight: //Drive to field goal

                //Sets motor's target position
                motorLeft.setTargetPosition(-280);
                motorRight.setTargetPosition(-280);

                //Makes motors run to that position
                motorLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
                motorRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

                //Robot forward
                motorLeft.setPower(1);
                motorRight.setPower(1);

                int leftPosition = motorLeft.getCurrentPosition();
                int rightPosition = motorRight.getCurrentPosition();

                //Checks if motors worked and switches state
                if (leftPosition == 280 && rightPosition == 280) {
                    state = State.turning;
                }

                break;

            case turning: //Turn into goal
                
                motorLeft.setPower(-0.5);
                motorRight.setPower(0.5);

                break;

        }

        //Telemetry
        telemetry.addData("Text", "***Robot Data***");
        telemetry.addData("Left Position", motorLeft.getCurrentPosition());
        telemetry.addData("Right Position", motorRight.getCurrentPosition());
    }

    public void stop(){

    }


}
