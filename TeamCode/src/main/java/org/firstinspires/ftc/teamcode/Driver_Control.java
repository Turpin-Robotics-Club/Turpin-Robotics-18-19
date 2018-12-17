package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "mmK", group = "Tests")
public class Driver_Control extends OpMode {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;


    @Override
    public void init() {
        frontLeft = hardwareMap.dcMotor.get("front_left");
        frontRight = hardwareMap.dcMotor.get("front_right");
        backLeft = hardwareMap.dcMotor.get("back_left");
        backRight = hardwareMap.dcMotor.get("back_right");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void loop(){
        //                      Right                       Forwards            Turn
        frontLeft.setPower(-gamepad1.left_stick_x - gamepad1.left_stick_y - gamepad1.right_stick_x);
        frontRight.setPower(gamepad1.left_stick_x - gamepad1.left_stick_y + gamepad1.left_stick_x);
        backLeft.setPower(gamepad1.left_stick_x - gamepad1.left_stick_y - gamepad1.right_stick_x);
        backRight.setPower(-gamepad1.left_stick_x - gamepad1.left_stick_y + gamepad1.left_stick_x);
    }

}
