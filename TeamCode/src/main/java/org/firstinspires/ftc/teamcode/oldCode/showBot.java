package org.firstinspires.ftc.teamcode.oldCode;

//import the methods that are called in the rest of the program
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


//defining that the program is to be listed under the teleop programs
@TeleOp(name = "Showbot", group = "Showbot")
public class showBot extends OpMode {

    //defines that the motors are motors
    private DcMotor Lmotor;
    private DcMotor Rmotor;

    @Override
    public void init() {

        //sets the motors to the motors in the configuration
        Lmotor = hardwareMap.dcMotor.get("left motor");
        Rmotor = hardwareMap.dcMotor.get("right motor");
        Lmotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {

        //sets the motors to the values of the joysticks (tank steering)
        //the values are negative because up on the y is negative on the controllers
        Lmotor.setPower(-gamepad1.left_stick_y*0.5);
        Rmotor.setPower(-gamepad1.right_stick_y*0.5);
    }
}
