package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "Clamp Test", group = "Tests")
public class ClampTest extends OpMode {

    private Servo left;
    private Servo right;
    @Override
    public void init() {
        left = hardwareMap.servo.get("left_servo");
        right = hardwareMap.servo.get("right_servo");
        left.setDirection(Servo.Direction.REVERSE);
        left.setPosition(0.6);
        right.setPosition(0);
    }

    @Override
    public void loop() {
        if(gamepad1.a){
            left.setPosition(0.85);
            right.setPosition(0.35);
        }
        else{
            left.setPosition(0.6);
            right.setPosition(0);
        }

    }
}
