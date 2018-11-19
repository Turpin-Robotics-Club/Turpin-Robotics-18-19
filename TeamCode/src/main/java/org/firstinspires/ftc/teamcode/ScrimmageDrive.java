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
    private DcMotor liftLeft;
    private DcMotor liftRight;
    private Servo left;
    private Servo right;
    private double frontLeftPower;
    private double frontRightPower;
    private double backLeftPower;
    private double backRightPower;

    private final double SPEED = 0.75;
    private final double forwardBonus = 1.5;

    @Override
    public void init() {
        frontLeft = hardwareMap.dcMotor.get("front_left");
        frontRight = hardwareMap.dcMotor.get("front_right");
        backLeft = hardwareMap.dcMotor.get("back_left");
        backRight = hardwareMap.dcMotor.get("back_right");
        liftLeft = hardwareMap.dcMotor.get("lift_left");
        liftRight = hardwareMap.dcMotor.get("lift_right");
        liftRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        left = hardwareMap.servo.get("left_servo");
        right = hardwareMap.servo.get("right_servo");
        right.setDirection(Servo.Direction.REVERSE);
        left.setPosition(0.52);
        right.setPosition(0.52);
    }

    public void loop(){
        driver();
        operator();
    }

    private void driver() {

        // Movement
        if (gamepad1.right_bumper) {
            frontLeftPower = (gamepad1.left_stick_x + (-gamepad1.left_stick_y * forwardBonus)) / 3;
            frontRightPower = (-gamepad1.left_stick_x + (-gamepad1.left_stick_y * forwardBonus)) / 3;
            backLeftPower = (-gamepad1.left_stick_x + (-gamepad1.left_stick_y * forwardBonus)) / 3;
            backRightPower = (gamepad1.left_stick_x + (-gamepad1.left_stick_y * forwardBonus)) / 3;
        } else {
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


        frontLeft.setPower(frontLeftPower * SPEED);
        frontRight.setPower(frontRightPower * SPEED);
        backLeft.setPower(backLeftPower * SPEED);
        backRight.setPower(backRightPower * SPEED);

    }
    private void operator(){

        if(gamepad2.a) {
            liftLeft.setPower(0.6);
            liftRight.setPower(0.6);
        }
        else if(gamepad2.y) {
            liftLeft.setPower(-0.6);
            liftRight.setPower(-0.6);
        }
        else {
            liftLeft.setPower(0);
            liftRight.setPower(0);
        }

        if(gamepad2.left_bumper){
            left.setPosition(0.9);
            right.setPosition(0.525) ;
        }
        if(gamepad2.right_bumper){
            left.setPosition(0.5);
            right.setPosition(0.5);
        }

    }

}