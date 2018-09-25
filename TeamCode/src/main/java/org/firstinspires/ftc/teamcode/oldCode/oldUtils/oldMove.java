package org.firstinspires.ftc.teamcode.oldCode.oldUtils;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

import static java.lang.Thread.sleep;

public class oldMove {

    private DcMotor flmotor;
    private DcMotor frmotor;
    private DcMotor blmotor;
    private DcMotor brmotor;
    private DcMotor liftMotor;
    private DcMotor liftMotor2;
    private DcMotor liftMotor3;
    private Servo ljewel;
    private Servo rjewel;
    private Servo relicServo;
    private Servo clamp;
    private Servo clamp2;

    private static Telemetry telemetry;
    private static DigitalChannel reddish;
    public static boolean getReddish(){return reddish.getState();}
    private static LinearOpMode opMode;
    public boolean red;
    private double initGyroPos = 0;
    private double stabilityMultiplier = 0.0001;
    private double spinRate = 0.002;


    ColorSensor colorSensor;
    ColorSensor colorSensor2;


    public static void pause(int milliseconds)
    {
        ElapsedTime tim = new ElapsedTime();
        tim.reset();
        while (tim.milliseconds()<milliseconds&&opMode.opModeIsActive());
    }

    /**
     * Initializes motor variables
     * @param op The instance of the calling LinearOpMode
     *
     */
    public oldMove(LinearOpMode op) {
        opMode = op;
        oldMove.telemetry = op.telemetry;
        HardwareMap hardware_map = op.hardwareMap;
        reddish = hardware_map.get(DigitalChannel.class,"touch");
        clamp = hardware_map.servo.get("clamp");
        clamp2 = hardware_map.servo.get("clamp2");
        ljewel = hardware_map.servo.get("raisin");
        rjewel = hardware_map.servo.get("raisin2");
        relicServo = hardware_map.servo.get("relic2");
        liftMotor = hardware_map.dcMotor.get("lift");
        liftMotor2 = hardware_map.dcMotor.get("lift2");
        liftMotor3 = hardware_map.dcMotor.get("lift3");
        liftMotor2.setDirection(DcMotorSimple.Direction.REVERSE);
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        red = reddish.getState();
        if (red) {
            flmotor = hardware_map.get(DcMotor.class, "front_left");
            frmotor = hardware_map.get(DcMotor.class, "front_right");
            blmotor = hardware_map.get(DcMotor.class, "back_left");
            brmotor = hardware_map.get(DcMotor.class, "back_right");
            flmotor.setDirection(DcMotorSimple.Direction.REVERSE);
            blmotor.setDirection(DcMotorSimple.Direction.REVERSE);
            telemetry.addData("Red",red);
        } else {
            frmotor = hardware_map.get(DcMotor.class, "front_left");
            flmotor = hardware_map.get(DcMotor.class, "front_right");
            brmotor = hardware_map.get(DcMotor.class, "back_left");
            blmotor = hardware_map.get(DcMotor.class, "back_right");
            frmotor.setDirection(DcMotorSimple.Direction.REVERSE);
            brmotor.setDirection(DcMotorSimple.Direction.REVERSE);
            telemetry.addData("Red",red);
        }
        telemetry.addData("reddish", reddish.getState());
        telemetry.update();



        oldSensors.initialize(opMode, red);
        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftMotor3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        resetEncoders();
        clamp.setPosition(0.945);
        clamp2.setPosition(1-0.89); //close

        if(red) rjewel.setPosition(0);
        if(!red) ljewel.setPosition(1);
        //relicServo.setPosition(0.11);
        pause(1000);
        //ljewel.setPosition(0.4);
        hitJewel();
        raisein();
        liftMotor.setPower(1);
        liftMotor2.setPower(1);
        telemetry.addData("red", oldSensors.readColor());
        pause(400);


        pause(0);
        liftMotor.setPower(0);
        liftMotor2.setPower(0);
        pause(1000);
    }


    public void resetEncoders()
    {
        flmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        blmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    private void hitJewel(){

            switch (oldSensors.readColor()) {


                case 1:
                    forward(2, .3);
                    rjewel.setPosition(1);
                    ljewel.setPosition(0);
                    pause(500);
                    forward(-1,-.3);
                    break;
                case -1:
                    turnRight(3, .3);
                    rjewel.setPosition(1);
                    ljewel.setPosition(0);
                    pause(500);
                    turnRight(-5, .3);
                    break;
                default: break;
            }


    }

    public void freeze()
    {
        int flpos = flmotor.getCurrentPosition();
        int frpos = frmotor.getCurrentPosition();
        int blpos = blmotor.getCurrentPosition();
        int brpos = brmotor.getCurrentPosition();

        flmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        blmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        brmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        flmotor.setTargetPosition(flpos);
        frmotor.setTargetPosition(frpos);
        blmotor.setTargetPosition(blpos);
        brmotor.setTargetPosition(brpos);
        pause(1000);
        while(
                !(flpos+5 >flmotor.getCurrentPosition()&&flpos-5<flmotor.getCurrentPosition()) &&
                !(frpos+5 >frmotor.getCurrentPosition()&&frpos-5<frmotor.getCurrentPosition()) &&
                !(blpos+5 >blmotor.getCurrentPosition()&&blpos-5<blmotor.getCurrentPosition()) &&
                !(brpos+5 >brmotor.getCurrentPosition()&&brpos-5<brmotor.getCurrentPosition())
                );
        resetEncoders();
    }
    public void holdDirection()
    {
        /*
        initGyroPos = oldSensors.readGyro();
        if (oldSensors.readGyro() > initGyroPos) {
            flmotor.setPower(flmotor.getPower() + (Math.abs((oldSensors.gyro.rawZ() - initGyroPos)) * stabilityMultiplier));
            blmotor.setPower(blmotor.getPower() + (Math.abs((oldSensors.gyro.rawZ() - initGyroPos)) * stabilityMultiplier));
            frmotor.setPower(frmotor.getPower() - (Math.abs((oldSensors.gyro.rawZ() - initGyroPos)) * stabilityMultiplier));
            brmotor.setPower(brmotor.getPower() - (Math.abs((oldSensors.gyro.rawZ() - initGyroPos)) * stabilityMultiplier));
        }
        if (oldSensors.readGyro() < initGyroPos) {
            flmotor.setPower(flmotor.getPower() - (Math.abs((oldSensors.gyro.rawZ() - initGyroPos)) * stabilityMultiplier));
            blmotor.setPower(blmotor.getPower() - (Math.abs((oldSensors.gyro.rawZ() - initGyroPos)) * stabilityMultiplier));
            frmotor.setPower(frmotor.getPower() + (Math.abs((oldSensors.gyro.rawZ() - initGyroPos)) * stabilityMultiplier));
            brmotor.setPower(brmotor.getPower() + (Math.abs((oldSensors.gyro.rawZ() - initGyroPos)) * stabilityMultiplier));
        }
        telemetry.addData("Gyro Z", oldSensors.gyro.rawZ());
        telemetry.update();
        */
    }



    public void turnRight(double degrees, double power){
        double angleMod = 0.2;
        resetEncoders();
        double CIRCUMFERENCE = Math.PI * oldRobotConstants.wheelDiameter;
        double ROTATIONS = angleMod * degrees / CIRCUMFERENCE;
        double COUNTS = oldRobotConstants.encoderCPR * ROTATIONS * oldRobotConstants.gearRatio;

        flmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        blmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        brmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);



        if (degrees < 0) {

            flmotor.setPower(-power*oldRobotConstants.flpower);
            frmotor.setPower(power*oldRobotConstants.frpower);
            blmotor.setPower(-power*oldRobotConstants.blpower);
            brmotor.setPower(power*oldRobotConstants.brpower);

            while (opMode.opModeIsActive() && flmotor.getCurrentPosition() > COUNTS) {
                telemetry.addData("front left counts", flmotor.getCurrentPosition());
                telemetry.addData("target", COUNTS);
                telemetry.update();
            }
        } else {

            flmotor.setPower(power*oldRobotConstants.flpower);
            frmotor.setPower(-power*oldRobotConstants.frpower);
            blmotor.setPower(power*oldRobotConstants.blpower);
            brmotor.setPower(-power*oldRobotConstants.brpower);

            while (opMode.opModeIsActive() && flmotor.getCurrentPosition() < COUNTS) {
                telemetry.addData("front left counts", flmotor.getCurrentPosition());
                telemetry.addData("target", COUNTS);
                telemetry.update();
            }
        }

        resetEncoders();
        telemetry.addData("it has", "begun");
        telemetry.update();
        //pause(200);

    }

    /**
     * Moves the robot forward or backward
     *
     * @param distance Distance (in inches) for the robot to go. Positive for forward, negative for backward
     * @param power    The power level for the robot to oldMove at. Should be an interval of [0.0, 1.0]
     *
     */
    public void forward(double distance, double power){
        resetEncoders();
        double CIRCUMFERENCE = Math.PI * oldRobotConstants.wheelDiameter;
        double ROTATIONS = distance / CIRCUMFERENCE;
        double COUNTS = oldRobotConstants.encoderCPR * ROTATIONS * oldRobotConstants.gearRatio;

        flmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        blmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        brmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        flmotor.setTargetPosition((int) COUNTS);
        frmotor.setTargetPosition((int) COUNTS);
        blmotor.setTargetPosition((int) COUNTS);
        brmotor.setTargetPosition((int) COUNTS);


        flmotor.setPower(power*oldRobotConstants.flpower);
        frmotor.setPower(power*oldRobotConstants.frpower);
        blmotor.setPower(power*oldRobotConstants.blpower);
        brmotor.setPower(power*oldRobotConstants.brpower);

        ElapsedTime pause = new ElapsedTime();
        pause.reset();
        if (distance < 0) {

            while (opMode.opModeIsActive() && flmotor.getCurrentPosition() > COUNTS&&pause.milliseconds()<5000) {
                oldSensors.vuMark();
                telemetry.addData("VuMark", oldRobotConstants.vuMark);
                telemetry.addData("front left counts", flmotor.getCurrentPosition());
                telemetry.addData("target", COUNTS);
                telemetry.update();
            }
        } else {
            while (opMode.opModeIsActive() && flmotor.getCurrentPosition() < COUNTS&& pause.milliseconds()<5000) {
                oldSensors.vuMark();
                telemetry.addData("VuMark", oldRobotConstants.vuMark);
                telemetry.addData("front left counts", flmotor.getCurrentPosition());
                telemetry.addData("target", COUNTS);
                telemetry.update();
            }
        }

        resetEncoders();
        telemetry.addData("it has", "begun");
        telemetry.update();
        pause(200);


    }




    /**
     * Move the robot right or negative right
     *
     * @param distance Distance (in inches) for the robot to oldMove side to side. Positive for left, negative for right
     * @param power    The power level for the robot to oldMove at. Should be an interval of [0.0, 1.0]
     */
    public void right(double distance, double power){

        resetEncoders();
        double CIRCUMFERENCE = Math.PI * oldRobotConstants.wheelDiameter;
        double ROTATIONS = distance / CIRCUMFERENCE;
        double COUNTS = oldRobotConstants.encoderCPR * ROTATIONS * oldRobotConstants.gearRatio * oldRobotConstants.sidewaysModifier;

        flmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        blmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        brmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        flmotor.setPower(power*oldRobotConstants.flpower);
        frmotor.setPower(-power*oldRobotConstants.frpower);
        blmotor.setPower(-power*oldRobotConstants.blpower);
        brmotor.setPower(power*oldRobotConstants.brpower);

        if (distance < 0) {

            while (opMode.opModeIsActive() && flmotor.getCurrentPosition() > COUNTS) {
                telemetry.addData("front left counts", flmotor.getCurrentPosition());
                telemetry.addData("target", COUNTS);
                telemetry.update();
            }
        } else {
            while (opMode.opModeIsActive() && flmotor.getCurrentPosition() < COUNTS) {
                telemetry.addData("front left counts", flmotor.getCurrentPosition());
                telemetry.addData("target", COUNTS);
                telemetry.update();
            }
        }

        resetEncoders();
        telemetry.addData("it has", "begun");
        telemetry.update();
        pause(200);









    }

    /**
     * moves the robot to a column based on the red/blue value of oldMove.java and the VuMark value from oldRobotConstants
     */
    public void toColumn()
    {
        RelicRecoveryVuMark relic = oldRobotConstants.vuMark;
        if(!red && relic == RelicRecoveryVuMark.LEFT) relic = RelicRecoveryVuMark.RIGHT;
        else if(!red && relic == RelicRecoveryVuMark.RIGHT) relic = RelicRecoveryVuMark.LEFT;
        if(relic == RelicRecoveryVuMark.CENTER);
        if(relic == RelicRecoveryVuMark.LEFT)
        {
            right(-7.5,-0.5);
        }
        if(relic == RelicRecoveryVuMark.RIGHT)
        {
            right(7.5,0.5);
        }
        release();
        pause(400);
        forward(8, 0.75);

    }


    public void release()
    {
        clamp.setPosition(0.98);
        clamp2.setPosition(1-0.94); //open
    }

    public void lowerRaisin()
    {
        if(red)
        {
            rjewel.setPosition(0);
        }
        else{ljewel.setPosition(1);}
    }

    public void raisein() {
        rjewel.setPosition(1);
        ljewel.setPosition(0);
    }

    public void liftZero()
    {
        liftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        liftMotor2.setDirection(DcMotorSimple.Direction.FORWARD);
        liftMotor3.setDirection(DcMotorSimple.Direction.FORWARD);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setTargetPosition(0);
        liftMotor2.setTargetPosition(0);
        liftMotor3.setTargetPosition(0);
        liftMotor.setPower(1);
        liftMotor2.setPower(1);
        liftMotor3.setPower(1);
    }
}
