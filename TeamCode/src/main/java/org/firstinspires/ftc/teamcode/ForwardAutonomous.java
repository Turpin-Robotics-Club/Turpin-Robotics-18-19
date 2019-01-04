package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utils.move;


@TeleOp(name = "Forward Autonomous", group = "Autonomous Finals")
public class ForwardAutonomous extends LinearOpMode {

    move drive;
    @Override
    public void runOpMode() {
        drive = new move(this);

        waitForStart();


        drive.forward(10000, 0.5f);

    }


}
