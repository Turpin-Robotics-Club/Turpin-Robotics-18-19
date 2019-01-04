package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class move {
    //Drive Motors
    private DcMotor flmotor;
    private DcMotor frmotor;
    private DcMotor blmotor;
    private DcMotor brmotor;

    private static Telemetry telemetry;
    private static OpMode opMode;

    ColorSensor colorSensor;

    public move(OpMode _opMode){
        opMode = _opMode;
        flmotor = opMode.hardwareMap.get(DcMotor.class, "front_left");
        frmotor = opMode.hardwareMap.get(DcMotor.class, "front_right");
        blmotor = opMode.hardwareMap.get(DcMotor.class, "back_left");
        brmotor = opMode.hardwareMap.get(DcMotor.class, "back_right");
        flmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        blmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        brmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flmotor.setDirection(DcMotorSimple.Direction.REVERSE);
        blmotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void forward(float distance, float power){

        final float distFromEncMult = 0.02f;
        final float distFromVelMult = 0.1f;
        resetEncoders();
        float flpower = power;
        float flPrevEnc = 0;
        float frpower = power;
        float frPrevEnc = 0;
        float blpower = power;
        float blPrevEnc = 0;
        float brpower = power;
        float brPrevEnc = 0;
        float prevEncAvg = 0;
        float avgDist = 0;
        do{
            avgDist = (flmotor.getCurrentPosition() + frmotor.getCurrentPosition() + blmotor.getCurrentPosition() + brmotor.getCurrentPosition())/4;
            flpower = power + ((flmotor.getCurrentPosition()-avgDist)*distFromEncMult) + (((avgDist - prevEncAvg) - (flmotor.getCurrentPosition() - flPrevEnc))*distFromVelMult);
            frpower = power + ((frmotor.getCurrentPosition()-avgDist)*distFromEncMult) + (((avgDist - prevEncAvg) - (frmotor.getCurrentPosition() - frPrevEnc))*distFromVelMult);
            blpower = power + ((blmotor.getCurrentPosition()-avgDist)*distFromEncMult) + (((avgDist - prevEncAvg) - (blmotor.getCurrentPosition() - blPrevEnc))*distFromVelMult);
            brpower = power + ((brmotor.getCurrentPosition()-avgDist)*distFromEncMult) + (((avgDist - prevEncAvg) - (brmotor.getCurrentPosition() - brPrevEnc))*distFromVelMult);
            flPrevEnc = flmotor.getCurrentPosition();
            frPrevEnc = frmotor.getCurrentPosition();
            blPrevEnc = blmotor.getCurrentPosition();
            brPrevEnc = brmotor.getCurrentPosition();
        }while(avgDist<distance);

    }

    /**
     * moves the robot to the gold mineral
     * @return index of the gold mineral (0=L, 1=C, 2=R)
     */
    public int moveToMineral(){
        //will exist eventually
        return -1;
    }


    public void resetEncoders()
    {
        flmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        blmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}
