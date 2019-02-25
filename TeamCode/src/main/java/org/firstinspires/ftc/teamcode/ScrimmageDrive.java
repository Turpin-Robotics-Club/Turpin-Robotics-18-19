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
    private Servo flipServo;
    private Servo openServo;
    private DcMotor liftBot;
    private double frontLeftPower;
    private double frontRightPower;
    private double backLeftPower;
    private double backRightPower;


    private boolean slowBot = false;
    private boolean LeftStickJustChanged = false;
    private boolean stopFlip = true;
    private final double SPEED = 1.1;
    private final double forwardBonus = 0.9;

    @Override
    public void init() {
        frontLeft = hardwareMap.dcMotor.get("front_left");
        frontRight = hardwareMap.dcMotor.get("front_right");
        backLeft = hardwareMap.dcMotor.get("back_left");
        backRight = hardwareMap.dcMotor.get("back_right");
        lift = hardwareMap.dcMotor.get("lift");
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        push = hardwareMap.dcMotor.get("push");
        slap = hardwareMap.dcMotor.get("slap-mo-tron");
        liftBot = hardwareMap.dcMotor.get("liftrobot");
        slap.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        openServo = hardwareMap.servo.get("open_servo");
        //openServo.setPosition(0.70);
        liftBot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftBot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flipServo = hardwareMap.servo.get("flip_servo");

        //flipServo.setPosition(0.51);
    }

    @Override
    public void loop() {
        frontLeftPower = 0;
        frontRightPower = 0;
        backLeftPower = 0;
        backRightPower = 0;
        driver();
        Operator();
        frontLeft.setPower(Math.pow(frontLeftPower, 3) * SPEED);
        frontRight.setPower(Math.pow(frontRightPower, 3) * SPEED);
        backLeft.setPower(Math.pow(backLeftPower, 3) * SPEED);
        backRight.setPower(Math.pow(backRightPower, 3) * SPEED);
    }

    private void driver() {

        // Movement
        if(!gamepad1.left_stick_button) LeftStickJustChanged = false;
        else if(!LeftStickJustChanged){ slowBot = !slowBot; LeftStickJustChanged = true;}

        if (slowBot) {
            frontLeftPower += (gamepad1.left_stick_x + (-gamepad1.left_stick_y * forwardBonus)) / 1.5;
            frontRightPower += (-gamepad1.left_stick_x + (-gamepad1.left_stick_y * forwardBonus)) / 1.5;
            backLeftPower += (-gamepad1.left_stick_x + (-gamepad1.left_stick_y * forwardBonus)) / 1.5;
            backRightPower += (gamepad1.left_stick_x + (-gamepad1.left_stick_y * forwardBonus)) / 1.5;
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
        if (gamepad1.dpad_up) liftBot.setPower(1f);
        else if(gamepad1.dpad_down) liftBot.setPower(-1);
        else liftBot.setPower(0);
        telemetry.addData("Lift Bot Enc", liftBot.getCurrentPosition());



    }
    private void Operator(){
        if(gamepad2.dpad_down){
            lift.setPower(0.1);
            stopFlip = false;
        }else if(gamepad2.dpad_up){
            lift.setPower(-0.5);

            stopFlip = true;
        }else if(stopFlip){lift.setPower(0); }

        telemetry.addData("Stop Flip", stopFlip);
        telemetry.addData("Lift Power", lift.getPower());

        telemetry.addData("Lift counts", lift.getCurrentPosition());




        push.setPower(gamepad2.left_stick_y * 0.75);


        if(gamepad2.y){
            flipServo.setPosition(0.02);

        }
        else if(gamepad2.x){
            flipServo.setPosition(0.51);
        }

        if(gamepad2.a) slap.setPower(0.9);
        else if(gamepad2.b) slap.setPower(-0.4);
        else slap.setPower(0);


        if(gamepad2.dpad_right)openServo.setPosition(1); //close
        else if(gamepad2.dpad_left) openServo.setPosition(0.65); //open
    }


}
