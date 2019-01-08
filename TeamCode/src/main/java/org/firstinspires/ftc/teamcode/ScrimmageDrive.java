package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Scrimmage Drive", group = "Teleop Finals")
public class ScrimmageDrive extends OpMode {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor lift;
    private DcMotor push;
    private DcMotor slap;
    private Servo flip;
    private DcMotor liftBot;
    private double frontLeftPower;
    private double frontRightPower;
    private double backLeftPower;
    private double backRightPower;

    private final double SPEED = 0.9;
    private final double forwardBonus = 1.5;

    @Override
    public void init() {
        frontLeft = hardwareMap.dcMotor.get("front_left");
        frontRight = hardwareMap.dcMotor.get("front_right");
        backLeft = hardwareMap.dcMotor.get("back_left");
        backRight = hardwareMap.dcMotor.get("back_right");
        lift = hardwareMap.dcMotor.get("lift");
        push = hardwareMap.dcMotor.get("push");
        slap = hardwareMap.dcMotor.get("slap-mo-tron");
        liftBot = hardwareMap.dcMotor.get("liftrobot");
        slap.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        flip = hardwareMap.servo.get("flip_servo");

        flip.setPosition(0.5);
    }

    public void loop(){
        frontLeftPower = 0;
        frontRightPower = 0;
        backLeftPower = 0;
        backRightPower = 0;
        driver();
        operator();
        frontLeft.setPower(Math.pow(frontLeftPower, 3) * SPEED);
        frontRight.setPower(Math.pow(frontRightPower, 3) * SPEED);
        backLeft.setPower(Math.pow(backLeftPower, 3) * SPEED);
        backRight.setPower(Math.pow(backRightPower, 3) * SPEED);
    }

    private void driver() {

        // Movement
        if (gamepad1.right_bumper) {
            frontLeftPower += (gamepad1.left_stick_x + (-gamepad1.left_stick_y * forwardBonus)) / 2;
            frontRightPower += (-gamepad1.left_stick_x + (-gamepad1.left_stick_y * forwardBonus)) / 2;
            backLeftPower += (-gamepad1.left_stick_x + (-gamepad1.left_stick_y * forwardBonus)) / 2;
            backRightPower += (gamepad1.left_stick_x + (-gamepad1.left_stick_y * forwardBonus)) / 2;
        } else {
            frontLeftPower += (gamepad1.left_stick_x + (-gamepad1.left_stick_y * forwardBonus));
            frontRightPower += (-gamepad1.left_stick_x + (-gamepad1.left_stick_y * forwardBonus));
            backLeftPower += (-gamepad1.left_stick_x + (-gamepad1.left_stick_y * forwardBonus));
            backRightPower += (gamepad1.left_stick_x + (-gamepad1.left_stick_y * forwardBonus));
        }


        // Turning
        frontLeftPower += gamepad1.right_stick_x;
        frontRightPower += -gamepad1.right_stick_x;
        backLeftPower += gamepad1.right_stick_x;
        backRightPower += -gamepad1.right_stick_x;


        //lift robot
        if (gamepad1.y)
            liftBot.setPower(1f);
        else if(gamepad1.a)
            liftBot.setPower(-1);



    }
    private void operator(){

        lift.setPower(Math.pow(gamepad2.right_stick_y,3)*0.5);

        //operator/driver combined controls
        frontLeftPower += (gamepad2.left_stick_x) / 2;
        frontRightPower += (-gamepad2.left_stick_x) / 2;
        backLeftPower += (-gamepad2.left_stick_x) / 2;
        backRightPower += (gamepad2.left_stick_x) / 2;




        push.setPower(Math.pow(gamepad2.left_stick_y,3)*0.5);

        if(gamepad2.left_bumper){
            flip.setPosition(0.8);

        }
        else if(gamepad2.left_trigger>0.2){
            flip.setPosition(0.2);
        }

        if(gamepad2.a) slap.setPower(0.9);
        else if(gamepad2.b) slap.setPower(-0.4);
        else slap.setPower(0);
    }

}
