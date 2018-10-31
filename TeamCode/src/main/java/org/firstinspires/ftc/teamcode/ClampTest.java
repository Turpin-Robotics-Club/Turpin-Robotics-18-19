package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class ClampTest extends OpMode {

    private Servo left;
    private Servo right;
    @Override
    public void init() {
        left = hardwareMap.servo.get("left_servo");
        right = hardwareMap.servo.get("right_servo");
        left.setDirection(Servo.Direction.REVERSE);
        left.setPosition(0.2);
        right.setPosition(0.2);
    }

    @Override
    public void loop() {
        if(gamepad1.a){
            left.setPosition(0.8);
            right.setPosition(0.8);
        }
    }
}
