package org.firstinspires.ftc.teamcode.oldCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.oldCode.oldUtils.oldRobotConstants;
import org.firstinspires.ftc.teamcode.oldCode.oldUtils.oldMove;

@Autonomous(name="A C 0", group="Old Code")
//@Disabled
public class driveForward extends LinearOpMode{



    public void runOpMode()
    {


        oldMove drive = new oldMove(this);
        int count = 0;
        while (oldRobotConstants.vuMark==RelicRecoveryVuMark.UNKNOWN&&opModeIsActive()&&count<20)
        {
            drive.forward(3,0.23);
            count++;
        }
        drive.forward(26-(2.5*count),.2);
        drive.right(-9, -0.5);
        drive.toColumn();
        drive.forward(-2 ,-0.3);
        drive.liftZero();
        while (opModeIsActive());
    }
}
