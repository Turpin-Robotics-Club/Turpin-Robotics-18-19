package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Scrimmage Autonomous", group = "autonomous Finals")
public class ScrimmageAutonomous extends LinearOpMode {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor liftLeft;
    private DcMotor liftRight;
    private Servo left;
    private Servo right;

    @Override
    public void runOpMode() {
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
        left.setPosition(0.5);
        right.setPosition(0.5);

        waitForStart();



        frontLeft.setPower(-1);
        frontRight.setPower(-1);
        backLeft.setPower(-1);
        backRight.setPower(-1);
        sleep(2000);
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);



    }
}
