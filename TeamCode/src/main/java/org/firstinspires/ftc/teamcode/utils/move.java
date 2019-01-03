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

    }

    /**
     * moves the robot to the gold mineral
     * @return index of the gold mineral (0=L, 1=C, 2=R)
     */
    public int moveToMineral(){
        //will exist eventually
        return -1;
    }

}
