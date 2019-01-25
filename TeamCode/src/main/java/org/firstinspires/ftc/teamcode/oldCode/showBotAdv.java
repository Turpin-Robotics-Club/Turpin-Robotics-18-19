package org.firstinspires.ftc.teamcode.oldCode;

//import the methods that are called in the rest of the program
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


//defining that the program is to be listed under the teleop programs
//@TeleOp(name = "Showbot Advanced", group = "Showbot")
public class showBotAdv extends OpMode {

    //defines that the motors are motors
    private DcMotor Lmotor;
    private DcMotor Rmotor;

    //the values of the motors - not set until the end of the program
    private double Lmotorvalue;
    private double Rmotorvalue;


    @Override
    public void init() {

        //sets the motors to the motors in the configuration
        Lmotor = hardwareMap.dcMotor.get("left motor");
        Rmotor = hardwareMap.dcMotor.get("right motor");
        Lmotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {

        //the left joystick controls the forwards and backwards movement
        Lmotorvalue = -gamepad1.left_stick_y;
        Rmotorvalue = -gamepad1.left_stick_y;

        //modify the values based on the right stick for turning
        //right on the controllers is positive
        Lmotorvalue = Lmotorvalue + gamepad1.right_stick_x;
        Rmotorvalue = Rmotorvalue - gamepad1.right_stick_x;

        //read the raw values of the sticks to the screen
        telemetry.addData("Left Stick Y", gamepad1.left_stick_y);
        telemetry.addData("Right Stick X", gamepad1.right_stick_x);

        //set the motors to the power
        Lmotor.setPower(Lmotorvalue*1.0);
        Rmotor.setPower(Rmotorvalue*1.0);
    }
}
