package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;


public class move {
    //Drive Motors
    private DcMotor flmotor;
    private DcMotor frmotor;
    private DcMotor blmotor;
    private DcMotor brmotor;


    private OpMode opMode;

    ColorSensor colorSensor;

    public move(OpMode _opMode){
        opMode = _opMode;
        flmotor = opMode.hardwareMap.get(DcMotor.class, "front_left");
        frmotor = opMode.hardwareMap.get(DcMotor.class, "front_right");
        blmotor = opMode.hardwareMap.get(DcMotor.class, "back_left");
        brmotor = opMode.hardwareMap.get(DcMotor.class, "back_right");
        flmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        blmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        blmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        brmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flmotor.setDirection(DcMotorSimple.Direction.REVERSE);
        blmotor.setDirection(DcMotorSimple.Direction.REVERSE);
        Sensors.initialize(opMode);
    }

    public void forward(float distance, float power){

        double CIRCUMFERENCE = Math.PI * RobotConstants.wheelDiameter;
        double ROTATIONS = distance / CIRCUMFERENCE;
        double COUNTS = RobotConstants.encoderCPR * ROTATIONS * RobotConstants.gearRatio;
        double Kp = 0.001;
        double Ki = 0.00005;
        double Kd = 0.00005;

        //one component for integral
        ArrayList<Double> flPastCountDifferences = new ArrayList<Double>();
        ArrayList<Double> frPastCountDifferences = new ArrayList<Double>();
        ArrayList<Double> blPastCountDifferences = new ArrayList<Double>();
        ArrayList<Double> brPastCountDifferences = new ArrayList<Double>();

        //other component for integral
        ArrayList<Double> PastCountTimes = new ArrayList<Double>();



        ElapsedTime timeTotal = new ElapsedTime();
        timeTotal.reset();
        ElapsedTime timeTick = new ElapsedTime();
        timeTick.reset();


        resetEncoders();

        double flpower;
        double frpower;
        double blpower;
        double brpower;

        double avgDist = 0;

        flPastCountDifferences.add(flmotor.getCurrentPosition()-avgDist);
        frPastCountDifferences.add(frmotor.getCurrentPosition()-avgDist);
        blPastCountDifferences.add(blmotor.getCurrentPosition()-avgDist);
        brPastCountDifferences.add(brmotor.getCurrentPosition()-avgDist);
        PastCountTimes.add(timeTick.seconds());
        do{
            try {

                avgDist = (flmotor.getCurrentPosition() + frmotor.getCurrentPosition() + blmotor.getCurrentPosition() + brmotor.getCurrentPosition()) / 4f;
                opMode.telemetry.addData("avgDist", avgDist);
                opMode.telemetry.addData("Counts", COUNTS);
                opMode.telemetry.update();
                /*
                opMode.telemetry.addData("flPID", (Kp * (flmotor.getCurrentPosition() - avgDist))+"/"+(Ki * RobotConstants.integral(flPastCountDifferences, PastCountTimes))+"/"+(Kd * (flPastCountDifferences.get(flPastCountDifferences.size() - 1) / PastCountTimes.get(PastCountTimes.size() - 1))));
                opMode.telemetry.addData("frPID", (Kp * (frmotor.getCurrentPosition() - avgDist))+"/"+(Ki * RobotConstants.integral(frPastCountDifferences, PastCountTimes))+"/"+(Kd * (frPastCountDifferences.get(frPastCountDifferences.size() - 1) / PastCountTimes.get(PastCountTimes.size() - 1))));
                opMode.telemetry.addData("blPID", (Kp * (blmotor.getCurrentPosition() - avgDist))+"/"+(Ki * RobotConstants.integral(blPastCountDifferences, PastCountTimes))+"/"+(Kd * (blPastCountDifferences.get(blPastCountDifferences.size() - 1) / PastCountTimes.get(PastCountTimes.size() - 1))));
                opMode.telemetry.addData("brPID", (Kp * (brmotor.getCurrentPosition() - avgDist))+"/"+(Ki * RobotConstants.integral(brPastCountDifferences, PastCountTimes))+"/"+(Kd * (brPastCountDifferences.get(brPastCountDifferences.size() - 1) / PastCountTimes.get(PastCountTimes.size() - 1))));

                while (sumArrayList(PastCountTimes) < timeTotal.seconds() - 3) {
                    flPastCountDifferences.remove(0);
                    frPastCountDifferences.remove(0);
                    blPastCountDifferences.remove(0);
                    brPastCountDifferences.remove(0);
                    PastCountTimes.remove(0);
                }
                flPastCountDifferences.add(flmotor.getCurrentPosition() - avgDist);
                frPastCountDifferences.add(frmotor.getCurrentPosition() - avgDist);
                blPastCountDifferences.add(blmotor.getCurrentPosition() - avgDist);
                brPastCountDifferences.add(brmotor.getCurrentPosition() - avgDist);
                PastCountTimes.add(timeTick.seconds());


                flpower = power + ((Kp * (flmotor.getCurrentPosition() - avgDist)) + (Ki * RobotConstants.integral(flPastCountDifferences, PastCountTimes)) + (Kd * (flPastCountDifferences.get(flPastCountDifferences.size() - 1) / PastCountTimes.get(PastCountTimes.size() - 1))));
                frpower = power + ((Kp * (frmotor.getCurrentPosition() - avgDist)) + (Ki * RobotConstants.integral(frPastCountDifferences, PastCountTimes)) + (Kd * (frPastCountDifferences.get(frPastCountDifferences.size() - 1) / PastCountTimes.get(PastCountTimes.size() - 1))));
                blpower = power + ((Kp * (blmotor.getCurrentPosition() - avgDist)) + (Ki * RobotConstants.integral(blPastCountDifferences, PastCountTimes)) + (Kd * (blPastCountDifferences.get(blPastCountDifferences.size() - 1) / PastCountTimes.get(PastCountTimes.size() - 1))));
                brpower = power + ((Kp * (brmotor.getCurrentPosition() - avgDist)) + (Ki * RobotConstants.integral(brPastCountDifferences, PastCountTimes)) + (Kd * (brPastCountDifferences.get(brPastCountDifferences.size() - 1) / PastCountTimes.get(PastCountTimes.size() - 1))));



                opMode.telemetry.addData("flpower", flpower);
                opMode.telemetry.addData("frpower", frpower);
                opMode.telemetry.addData("blpower", blpower);
                opMode.telemetry.addData("brpower", brpower);
                opMode.telemetry.update();

                flmotor.setPower(flpower);
                frmotor.setPower(frpower);
                blmotor.setPower(blpower);
                brmotor.setPower(brpower);
                timeTick.reset();
                */


                flmotor.setPower(power);
                frmotor.setPower(power);
                blmotor.setPower(power);
                brmotor.setPower(power);
            }catch(Exception e) {

                opMode.telemetry.addData("ERROR", e);
                opMode.telemetry.update();
            }

        }while(avgDist<COUNTS);

    }

    /**
     * moves the robot to the gold mineral
     * @return index of the gold mineral (0=L, 1=C, 2=R)
     */
    public int moveToMineral(){
        //will exist eventually
        return -1;
    }

    public void turn(float degrees, float power){

        double target = Sensors.realGyro() + degrees;
        while (target>360)target-=360;
        //by default, right
        while(Sensors.realGyro()>target+10 || Sensors.realGyro()<target-10){
            if(degrees>0){
                flmotor.setPower(power);
                frmotor.setPower(-power);
                blmotor.setPower(power);
                brmotor.setPower(-power);
            }else if(degrees<0){
                flmotor.setPower(-power);
                frmotor.setPower(power);
                blmotor.setPower(-power);
                brmotor.setPower(power);
            }
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.addData("Current", Sensors.readGyro());
            opMode.telemetry.addData("Real", Sensors.realGyro());
            opMode.telemetry.update();
        }
    }

    public void resetEncoders()
    {
        flmotor.setPower(0);
        frmotor.setPower(0);
        blmotor.setPower(0);
        brmotor.setPower(0);
        flmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        blmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        blmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        brmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private double sumArrayList(ArrayList<Double> a){
        double out = 0;
        for(double b:a) out+=b;
        return out;
    }

    public void pause(int milliseconds)
    {
        ElapsedTime tim = new ElapsedTime();
        tim.reset();
        try {
            while (tim.milliseconds()<milliseconds) wait(5000);
        }catch (Exception e){
            opMode.telemetry.addData("ERROR", e.toString());
        }

    }

}
