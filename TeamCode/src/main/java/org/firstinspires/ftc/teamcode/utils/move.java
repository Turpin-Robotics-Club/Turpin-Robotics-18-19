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
        Sensors.initialize(opMode);
    }

    public void forward(float distance, float power){

        double CIRCUMFERENCE = Math.PI * RobotConstants.wheelDiameter;
        double ROTATIONS = distance / CIRCUMFERENCE;
        double COUNTS = RobotConstants.encoderCPR * ROTATIONS * RobotConstants.gearRatio;
        double Kp = 0.1;
        double Ki = 0.05;
        double Kd = 0.05;

        ArrayList<Double> flPastCountDifferences = new ArrayList<Double>();
        ArrayList<Double> PastCountTimes = new ArrayList<Double>();

        ArrayList<Double> frPastCountDifferences = new ArrayList<Double>();


        ArrayList<Double> blPastCountDifferences = new ArrayList<Double>();


        ArrayList<Double> brPastCountDifferences = new ArrayList<Double>();




        ElapsedTime timeTotal = new ElapsedTime();
        ElapsedTime timeTick = new ElapsedTime();
        timeTick.reset();

        final float distFromEncMult = 0.02f;
        final float distFromVelMult = 0.1f;
        resetEncoders();
        double flpower = power;
        float flPrevEnc = 0;
        double frpower = power;
        float frPrevEnc = 0;
        double blpower = power;
        float blPrevEnc = 0;
        double brpower = power;
        float brPrevEnc = 0;
        float prevEncAvg = 0;
        double avgDist = 0;
        do{
            avgDist = (flmotor.getCurrentPosition() + frmotor.getCurrentPosition() + blmotor.getCurrentPosition() + brmotor.getCurrentPosition())/4;

            flPastCountDifferences.add(flmotor.getCurrentPosition()-avgDist);
            frPastCountDifferences.add(frmotor.getCurrentPosition()-avgDist);
            blPastCountDifferences.add(blmotor.getCurrentPosition()-avgDist);
            brPastCountDifferences.add(brmotor.getCurrentPosition()-avgDist);
            PastCountTimes.add(timeTick.seconds());


            flpower = power + (Kp*(flmotor.getCurrentPosition()-avgDist)) + (Ki * RobotConstants.integral(flPastCountDifferences, flPastCountDifferences)) + (Kd*(flPastCountDifferences.get(flPastCountDifferences.size()-1)/PastCountTimes.get(PastCountTimes.size()-1)));
            flpower = power + (Kp*(frmotor.getCurrentPosition()-avgDist)) + (Ki * RobotConstants.integral(frPastCountDifferences, frPastCountDifferences)) + (Kd*(frPastCountDifferences.get(frPastCountDifferences.size()-1)/PastCountTimes.get(PastCountTimes.size()-1)));
            flpower = power + (Kp*(blmotor.getCurrentPosition()-avgDist)) + (Ki * RobotConstants.integral(blPastCountDifferences, blPastCountDifferences)) + (Kd*(blPastCountDifferences.get(blPastCountDifferences.size()-1)/PastCountTimes.get(PastCountTimes.size()-1)));
            flpower = power + (Kp*(brmotor.getCurrentPosition()-avgDist)) + (Ki * RobotConstants.integral(brPastCountDifferences, brPastCountDifferences)) + (Kd*(brPastCountDifferences.get(brPastCountDifferences.size()-1)/PastCountTimes.get(PastCountTimes.size()-1)));




            flPrevEnc = flmotor.getCurrentPosition();
            frPrevEnc = frmotor.getCurrentPosition();
            blPrevEnc = blmotor.getCurrentPosition();
            brPrevEnc = brmotor.getCurrentPosition();
            timeTick.reset();
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

    public void turn(float degrees, float power){


        while((degrees>0 ? Sensors.readGyro() < degrees || (Sensors.readGyro() > 350 || Sensors.readGyro() < 10) : Sensors.readGyro() > 360+degrees || (Sensors.readGyro() > 350 || Sensors.readGyro() < 10))){
            if(degrees>0){
                flmotor.setPower(-power);
                frmotor.setPower(power);
                blmotor.setPower(-power);
                brmotor.setPower(power);
            }else if(degrees<0){
                flmotor.setPower(power);
                frmotor.setPower(-power);
                blmotor.setPower(power);
                brmotor.setPower(-power);
            }
        }
    }

    public void resetEncoders()
    {
        flmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        blmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}
