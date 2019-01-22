package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utils.Sensors;
import org.firstinspires.ftc.teamcode.utils.move;


@Autonomous(name = "Forward Autonomous", group = "Autonomous Finals")
public class ForwardAutonomous extends LinearOpMode {

    move drive;
    @Override
    public void runOpMode() {
        drive = new move(this);

        waitForStart();
        telemetry.addData("Gyro", Sensors.readGyro());
        telemetry.addData("Real Gyro", Sensors.realGyro());
        telemetry.update();
        sleep(5000);
        drive.turn(90, 0.3f);
        drive.resetEncoders();
        telemetry.addData("Gyro", Sensors.readGyro());
        telemetry.addData("Real Gyro", Sensors.realGyro());
        telemetry.update();
        sleep(5000);
        //drive.forward(10000, 0.5f);

    }


}
