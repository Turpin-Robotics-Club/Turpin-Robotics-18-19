package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.utils.Sensors;
import org.firstinspires.ftc.teamcode.utils.TFODBase;
import org.firstinspires.ftc.teamcode.utils.move;


@Autonomous(name = "Forward Autonomous", group = "Autonomous Finals")
public class ForwardAutonomous extends LinearOpMode {

    move drive;
    TFODBase tfod;
    @Override
    public void runOpMode() {
        drive = new move(this);
        tfod = new TFODBase(this);
        waitForStart();
        if(tfod.getVisibleObjects().size()>3)
        {
            Recognition gold = null;
            Recognition silver1 = null;
            Recognition silver2 = null;
            for (Recognition a : tfod.getVisibleObjects()){
                if((gold == null || gold.getBottom()>a.getBottom()) && a.getLabel().equals(TFODBase.LABEL_GOLD_MINERAL)){
                    gold = a;
                }

                if((silver1 == null || silver1.getBottom()>a.getBottom()) && a.getLabel().equals(TFODBase.LABEL_SILVER_MINERAL)){
                    silver2 = silver1;
                    silver1 = a;
                } else if((silver2 == null || silver2.getBottom()>a.getBottom()) && a.getLabel().equals(TFODBase.LABEL_SILVER_MINERAL)){
                    silver2 = a;
                }
            }


            if(silver1.getLeft()<gold.getLeft() && silver2.getLeft()<gold.getLeft()){
                drive.turn(30, 0.5f);
            }
            if(silver1.getLeft()>gold.getLeft() && silver2.getLeft()>gold.getLeft()){
                drive.turn(-30, 0.5f);
            }

            drive.turn(90, 0.5f);
        }
        drive.forward(30, 0.5f);
        drive.resetEncoders();



    }


}
