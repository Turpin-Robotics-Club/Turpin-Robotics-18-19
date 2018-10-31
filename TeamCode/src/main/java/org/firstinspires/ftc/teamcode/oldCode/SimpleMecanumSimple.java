package org.firstinspires.ftc.teamcode.oldCode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="Simple Simple Drive", group = "Old Code")
public class SimpleMecanumSimple extends OpMode{



    private DcMotor frontleft;
    private DcMotor frontright;
    private DcMotor backleft;
    private DcMotor backright;


    private double frontLeftPower;
    private double frontRightPower;
    private double backLeftPower;
    private double backRightPower;




    private final double SPEED = 0.75;
    private final double forwardBonus = 1.5;



    @Override
    public void init() {

        frontleft = hardwareMap.dcMotor.get("front_left");
        frontright = hardwareMap.dcMotor.get("front_right");
        backleft = hardwareMap.dcMotor.get("back_left");
        backright = hardwareMap.dcMotor.get("back_right");





        frontleft.setDirection(DcMotorSimple.Direction.REVERSE);
        backleft.setDirection(DcMotorSimple.Direction.REVERSE);



    }

    @Override
    public void loop() {



        // Movement
        if(gamepad1.right_bumper)
        {
            frontLeftPower = (gamepad1.left_stick_x + (-gamepad1.left_stick_y * forwardBonus))/3;
            frontRightPower = (-gamepad1.left_stick_x + (-gamepad1.left_stick_y * forwardBonus))/3;
            backLeftPower = (-gamepad1.left_stick_x + (-gamepad1.left_stick_y * forwardBonus))/3;
            backRightPower = (gamepad1.left_stick_x + (-gamepad1.left_stick_y * forwardBonus))/3;
        }


        else {
            frontLeftPower = (gamepad1.left_stick_x + (-gamepad1.left_stick_y * forwardBonus));
            frontRightPower = (-gamepad1.left_stick_x + (-gamepad1.left_stick_y * forwardBonus));
            backLeftPower = (-gamepad1.left_stick_x + (-gamepad1.left_stick_y * forwardBonus));
            backRightPower = (gamepad1.left_stick_x + (-gamepad1.left_stick_y * forwardBonus));
        }



        // Turning
        frontLeftPower += gamepad1.right_stick_x;
        frontRightPower += -gamepad1.right_stick_x;
        backLeftPower += gamepad1.right_stick_x;
        backRightPower += -gamepad1.right_stick_x;

        frontleft.setPower(frontLeftPower * SPEED);
        frontright.setPower(frontRightPower * SPEED);
        backleft.setPower(backLeftPower * SPEED);
        backright.setPower(backRightPower * SPEED);




    }

    @Override
    public void stop() {
        frontleft.setPower(0);
        frontright.setPower(0);
        backleft.setPower(0);
        backright.setPower(0);
    }
}
