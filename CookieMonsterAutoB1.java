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
    DcMotor motorGrabber;

    //Initialize servo
    Servo basketTilt;

    //Initialize counter
    int count = 0;

    //Step times under loop()
    static boolean driveFinished = false;
    static boolean turnFinished = false;

    //Initialize state machine
    enum State {drivingStraight, turning, enteringGoal, actionDone};
    State state;

    //-----------------Show work for encoder calculations--------------------------
    //Set base values
    final static int ENCODER_CPR = 1440;        //Encoder counts per rotation
    final static double GEAR_RATIO = 0.66;      //Gear Ratio
    final static int WHEEL_DIAMETER = 4;        //Diameter of wheel in inches
    final static int DISTANCE = 24;             //Distance in inches to drive

    //Do the math
    final static double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;   //Calculates circumference
    final static double ROTATIONS = DISTANCE / CIRCUMFERENCE;
    final static double COUNTS = ENCODER_CPR * ROTATIONS * GEAR_RATIO;
    //-----------------------------------------------------------------------------

    @Override
    public void init() {

        //Map drive motors
        motorLeft = hardwareMap.dcMotor.get("motor_1");
        motorRight = hardwareMap.dcMotor.get("motor_2");
        motorGrabber = hardwareMap.dcMotor.get("motor_3");

        //Reverse right motor
        motorRight.setDirection(DcMotor.Direction.REVERSE);

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
                motorLeft.setTargetPosition((int) COUNTS);
                motorRight.setTargetPosition((int) COUNTS);

                //Makes motors run to that position
                motorLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
                motorRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

                //Robot forward
                motorLeft.setPower(1);
                motorRight.setPower(1);

                //Checks if motors worked
                if (motorLeft.getCurrentPosition() == COUNTS && motorRight.getCurrentPosition() == COUNTS) {
                    driveFinished = true;
                }
                //Switches state after 'forwardTime' seconds
                if (driveFinished == true) {
                    state = State.turning;
                }

                break;

            case turning: //Turn into goal

                motorLeft.setPower(-0.5);
                motorRight.setPower(0.5);

                break;

        }
    }


}
