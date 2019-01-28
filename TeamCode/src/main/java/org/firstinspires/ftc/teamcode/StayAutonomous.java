package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.utils.TFODBase;
import org.firstinspires.ftc.teamcode.utils.move;


@Autonomous(name = "Stay Autonomous", group = "Autonomous Finals")
public class StayAutonomous extends LinearOpMode {

    move drive;

    DcMotor robotLift;
    DcMotor push;
    @Override
    public void runOpMode() {
        drive = new move(this);

        robotLift = hardwareMap.dcMotor.get("liftrobot");
        push = hardwareMap.dcMotor.get("push");
        robotLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robotLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        waitForStart();
        robotLift.setPower(1);
        while (robotLift.getCurrentPosition()<17350){ telemetry.addData("robot lift",robotLift.getCurrentPosition()); telemetry.update();}
        robotLift.setPower(0);



        drive.resetEncoders();
        drive.flmotor.setPower(0.5);
        drive.frmotor.setPower(-0.5);
        drive.blmotor.setPower(-1);
        drive.brmotor.setPower(1);
        while ((drive.flmotor.getCurrentPosition() - drive.frmotor.getCurrentPosition() - drive.brmotor.getCurrentPosition() + drive.brmotor.getCurrentPosition())/4f<200){ telemetry.addData("Pos", (drive.flmotor.getCurrentPosition() - drive.frmotor.getCurrentPosition() - drive.brmotor.getCurrentPosition() + drive.brmotor.getCurrentPosition())/4f); telemetry.update();}
        telemetry.addData("Hook", "Exited");
        telemetry.update();
        drive.flmotor.setPower(0);
        drive.frmotor.setPower(0);
        drive.blmotor.setPower(0);
        drive.brmotor.setPower(0);
        drive.resetEncoders();




        drive.forward(2, 0.5f);
        drive.resetEncoders();

    }


}
