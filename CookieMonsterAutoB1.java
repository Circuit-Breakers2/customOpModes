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
    //DcMotor motorGrabber;

    //Initialize servo
    Servo basketTilt;

    //---------Encoder configuration-------------
    //Preferences
    final static int ENCODER_CPR = 1440;        //Encoder counts per rotation
    final static double GEAR_RATIO = 1.5;       //How many rotations from motor gear to wheel gear (80 to 120)
    final static double CIRCUMFERENCE = 12.56;  //In inches

    //Distance to be traveled
    double distance;

    //Rotations to be made
    double rotations;

    //Counts to be counted
    double counts;
    //-------------------------------------------

    //Initialize state machine
    enum State {drivingStraight, turning, enteringGoal, actionDone};
    State state;


    @Override
    public void init() {

        //Map drive motors
        motorLeft = hardwareMap.dcMotor.get("motor_1");
        motorRight = hardwareMap.dcMotor.get("motor_2");
        //motorGrabber = hardwareMap.dcMotor.get("motor_3");

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
                //Set distance (in inches)
                distance = 24;

                //Calculate counts needed
                rotations = distance / CIRCUMFERENCE;
                counts = ENCODER_CPR * rotations * GEAR_RATIO;

                //Set motor target positions
                motorLeft.setTargetPosition((int)-counts);
                motorRight.setTargetPosition((int)-counts);

                //Make motors run to that position
                motorLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
                motorRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

                //Set motor power
                motorLeft.setPower(1);
                motorRight.setPower(1);

                //Spin grabber outward
                //motorGrabber.setPower(-1);

                //Change state to do next step
                state = State.turning;

                break;

            case turning: //Turn into goal

                //Reset encoders for new movement block
                motorLeft.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
                motorLeft.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

                //Set distance (in inches)
                distance = 10;

                //Calculate counts needed
                rotations = distance /CIRCUMFERENCE;
                counts = ENCODER_CPR * rotations * GEAR_RATIO;

                //Set motor target positions
                motorLeft.setTargetPosition((int)counts);
                motorRight.setTargetPosition((int)-counts);

                //Make motors run to that position
                motorLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
                motorRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

                //Set motor power
                motorLeft.setPower(-1);
                motorRight.setPower(1);

                //Spin grabber outward
                //motorGrabber.setPower(-1);

                //Change state to do next step
                state = State.enteringGoal;

                break;

            case enteringGoal: //Robot forward into goal

                //Reset encoders for new movement block
                motorLeft.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
                motorLeft.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

                //Set distance (in inches)
                distance = 5;

                //Calculate counts needed
                rotations = distance /CIRCUMFERENCE;
                counts = ENCODER_CPR * rotations * GEAR_RATIO;

                //Set motor target positions
                motorLeft.setTargetPosition((int)-counts);
                motorRight.setTargetPosition((int)-counts);

                //Make motors run to that position
                motorLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
                motorRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

                //Set motor power
                motorLeft.setPower(0.3);
                motorRight.setPower(0.3);

                //Spin grabber outward
                //motorGrabber.setPower(-1);

                //Change state to do next step
                state = State.actionDone;

                break;

            case actionDone: //Finishes program
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
