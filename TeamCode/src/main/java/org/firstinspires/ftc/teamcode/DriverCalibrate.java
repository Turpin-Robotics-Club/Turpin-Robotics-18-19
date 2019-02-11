package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
@TeleOp(name = "Driver Calibrate")
public class DriverCalibrate extends OpMode {


    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private double forwardBoost = 0.5;
    private double sidewaysBoost = 0.5;
    private double turnBoost = 0.5;
    private double frontLeftPower;
    private double frontRightPower;
    private double backLeftPower;
    private double backRightPower;
    private boolean buttonPushed = false;
    @Override
    public void init() {
        frontLeft = hardwareMap.dcMotor.get("front_left");
        frontRight = hardwareMap.dcMotor.get("front_right");
        backLeft = hardwareMap.dcMotor.get("back_left");
        backRight = hardwareMap.dcMotor.get("back_right");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }


    @Override
    public void loop() {
        frontLeftPower=0;
        frontRightPower=0;
        backLeftPower=0;
        backRightPower=0;



        // Movement
        if (gamepad1.right_bumper) {
            frontLeftPower += ((gamepad1.left_stick_x * sidewaysBoost) + (-gamepad1.left_stick_y * forwardBoost)) / 2d;
            frontRightPower += ((-gamepad1.left_stick_x * sidewaysBoost) + (-gamepad1.left_stick_y * forwardBoost)) / 2d;
            backLeftPower += ((-gamepad1.left_stick_x * sidewaysBoost) + (-gamepad1.left_stick_y * forwardBoost)) / 2d;
            backRightPower += ((gamepad1.left_stick_x * sidewaysBoost) + (-gamepad1.left_stick_y * forwardBoost)) / 2d;
        } else {
            frontLeftPower += ((gamepad1.left_stick_x * sidewaysBoost) + (-gamepad1.left_stick_y * forwardBoost));
            frontRightPower += ((-gamepad1.left_stick_x * sidewaysBoost) + (-gamepad1.left_stick_y * forwardBoost));
            backLeftPower += ((-gamepad1.left_stick_x * sidewaysBoost) + (-gamepad1.left_stick_y * forwardBoost));
            backRightPower += ((gamepad1.left_stick_x * sidewaysBoost) + (-gamepad1.left_stick_y * forwardBoost));
        }


        // Turning
        frontLeftPower += gamepad1.right_stick_x * turnBoost;
        frontRightPower += -gamepad1.right_stick_x * turnBoost;
        backLeftPower += gamepad1.right_stick_x * turnBoost;
        backRightPower += -gamepad1.right_stick_x * turnBoost;

        if(!(gamepad1.dpad_up || gamepad1.dpad_right || gamepad1.dpad_down || gamepad1.dpad_left || gamepad1.left_bumper || gamepad1.left_trigger>0.9) && buttonPushed) buttonPushed = false;
        else {
            if (gamepad1.dpad_up) {
                forwardBoost += 0.001;
                buttonPushed = true;
            }
            if (gamepad1.dpad_down) {
                forwardBoost -= 0.001;
                buttonPushed = true;
            }
            if (gamepad1.dpad_right) {
                sidewaysBoost += 0.001;
                buttonPushed = true;
            }
            if (gamepad1.dpad_left) {
                sidewaysBoost -= 0.001;
                buttonPushed = true;
            }
            if (gamepad1.left_bumper) {
                turnBoost += 0.001;
                buttonPushed = true;
            }
            if (gamepad1.left_trigger > 0.9) {
                turnBoost -= 0.001;
                buttonPushed = true;
            }

            frontLeft.setPower(frontLeftPower);
            frontRight.setPower(frontRightPower);
            backLeft.setPower(backLeftPower);
            backRight.setPower(backRightPower);

            telemetry.addData("Forward", forwardBoost);
            telemetry.addData("Sideways", sidewaysBoost);
            telemetry.addData("Turn", turnBoost);
            telemetry.update();
        }
    }
}
