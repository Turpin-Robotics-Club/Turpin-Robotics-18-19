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
        right.setDirection(Servo.Direction.REVERSE);
        left.setPosition(0.52);
        right.setPosition(0.52);
    }

    @Override
    public void loop() {
        if(gamepad1.a){
            left.setPosition(0.56);
            right.setPosition(0.56);
        }
        else{
            left.setPosition(0.525);
            right.setPosition(0.525);
        }

    }
}
