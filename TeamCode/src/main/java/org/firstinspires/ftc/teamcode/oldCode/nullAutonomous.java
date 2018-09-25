package org.firstinspires.ftc.teamcode.oldCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.oldCode.oldUtils.oldRobotConstants;
import org.firstinspires.ftc.teamcode.oldCode.oldUtils.oldSensors;
import org.firstinspires.ftc.teamcode.oldCode.oldUtils.oldMove;

@Autonomous(name="AB AB 0", group="Autonomous Finals")
//@Disabled
public class nullAutonomous extends LinearOpMode {
    @Override
    public void runOpMode(){

        oldMove drive = new oldMove(this);
        oldSensors.vuMark();
        drive.liftZero();
        while (opModeIsActive())
        {
            telemetry.addData("reddish",oldMove.getReddish());
            telemetry.addData("VuMark", oldRobotConstants.vuMark/*== RelicRecoveryVuMark.RIGHT*/);
            telemetry.addData("Heading", oldSensors.readGyro());
            telemetry.addData("Gyro Heading", oldSensors.angles.thirdAngle);
            telemetry.addData("Offset rate", oldSensors.gyrochange);
            telemetry.update();
        }

    }
}
