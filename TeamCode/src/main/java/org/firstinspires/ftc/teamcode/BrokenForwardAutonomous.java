package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.utils.TFODBase;
import org.firstinspires.ftc.teamcode.utils.move;


@Autonomous(name = "Broken Autonomous", group = "Autonomous Finals")
public class BrokenForwardAutonomous extends LinearOpMode {

    move drive;
    TFODBase tfod;
    DcMotor robotLift;
    DcMotor push;
    @Override
    public void runOpMode() {
        drive = new move(this);
        tfod = new TFODBase(this);
        robotLift = hardwareMap.dcMotor.get("liftrobot");
        push = hardwareMap.dcMotor.get("push");
        robotLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robotLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Recognition gold = null;


        waitForStart();
        robotLift.setPower(1);
        while (robotLift.getCurrentPosition()<17350){ telemetry.addData("robot lift",robotLift.getCurrentPosition()); telemetry.update();}
        robotLift.setPower(0);



        drive.resetEncoders();
        drive.flmotor.setPower(0.25);
        drive.frmotor.setPower(-0.25);
        drive.blmotor.setPower(-1);
        drive.brmotor.setPower(1);
        while ((drive.flmotor.getCurrentPosition() - drive.frmotor.getCurrentPosition() - drive.brmotor.getCurrentPosition() + drive.brmotor.getCurrentPosition())/4f<175){ telemetry.addData("Pos", (drive.flmotor.getCurrentPosition() - drive.frmotor.getCurrentPosition() - drive.brmotor.getCurrentPosition() + drive.brmotor.getCurrentPosition())/4f); telemetry.update();}
        telemetry.addData("Hook", "Exited");
        telemetry.update();
        drive.flmotor.setPower(0);
        drive.frmotor.setPower(0);
        drive.blmotor.setPower(0);
        drive.brmotor.setPower(0);
        drive.resetEncoders();


        //turn right
        drive.resetEncoders();
        drive.flmotor.setPower(0.5);
        drive.frmotor.setPower(0);
        drive.blmotor.setPower(0.5);
        drive.brmotor.setPower(0);
        while ((drive.flmotor.getCurrentPosition() - drive.frmotor.getCurrentPosition() - drive.brmotor.getCurrentPosition() + drive.brmotor.getCurrentPosition())/4f<100){ telemetry.addData("Pos", (drive.flmotor.getCurrentPosition() - drive.frmotor.getCurrentPosition() - drive.brmotor.getCurrentPosition() + drive.brmotor.getCurrentPosition())/4f); telemetry.update();}

        drive.flmotor.setPower(0);
        drive.frmotor.setPower(0);
        drive.blmotor.setPower(0);
        drive.brmotor.setPower(0);
        drive.resetEncoders();

        for(int i=0;i<100;i+=2) {
            if (tfod.getVisibleObjects().size() > 0) {


                for (Recognition a : tfod.getVisibleObjects()) {
                    sleep(25);
                    if (gold == null && a.getBottom() > 1050 && a.getLabel().equals(TFODBase.LABEL_GOLD_MINERAL))
                        gold = a;
                    else if (gold != null && (gold.getBottom() < a.getBottom()) && a.getLabel().equals(TFODBase.LABEL_GOLD_MINERAL)) {
                        gold = a;
                    }


                }
            }
            sleep(15);

                drive.flmotor.setPower(0);
                drive.frmotor.setPower(0.15);
                drive.blmotor.setPower(0.);
                drive.brmotor.setPower(0.15);
                --i;


        }
        /*
            if(gold == null){
                telemetry.addData("Direction", "Left");
                telemetry.update();
                sleep(1000);
                drive.turn(30, 0.35f);
            }
            else if(gold.getLeft()>325) {
                telemetry.addData("Direction", "Right");
                telemetry.update();
                sleep(1000);
                drive.turn(80, 0.35f);
            }else if(gold.getLeft()<325){
                telemetry.addData("Direction", "Center");
                telemetry.update();
                sleep(1000);
            }

        */

        drive.forward(20, 0.5f);
        drive.resetEncoders();

        push.setPower(-0.8);
        sleep(800);
        push.setPower(0);


    }


}
