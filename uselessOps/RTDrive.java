/**
 * Created by KK on 11/1/2015.
 */
package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
public class RTDrive extends OpMode {
    DcMotor motorRight;
    DcMotor motorLeft;
    public RTDrive(){
    }
    public void init(){
        motorRight = hardwareMap.dcMotor.get("motor_1");
        motorLeft = hardwareMap.dcMotor.get("motor_2");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
    }
    public void loop(){
        float throttle = gamepad1.right_trigger - gamepad1.left_trigger;
        float direction = -gamepad1.left_stick_x;
        float right = throttle - direction;
        float left = throttle + direction;
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);
        right = (float)scaleInput(right);
        left =  (float)scaleInput(left);
        motorRight.setPower(right);
        motorLeft.setPower(left);
    }
    public void stop(){
    }
    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };
        int index = (int) (dVal * 16.0);
        if (index < 0) {
            index = -index;
        } else if (index > 16) {
            index = 16;
        }
        double dScale;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }
        return dScale;
    }
}
