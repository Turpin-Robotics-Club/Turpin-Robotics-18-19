package org.firstinspires.ftc.teamcode.oldCode.oldUtils;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import static android.os.SystemClock.sleep;
import static org.firstinspires.ftc.teamcode.oldCode.oldUtils.oldMove.pause;


public class oldSensors {

    public static double gyrochange;

    private static ElapsedTime runtime = new ElapsedTime();
    public static double gyroInitial = 0;
    public static BNO055IMU gyro;
    public static boolean red;
    public static Orientation angles;
    private static LinearOpMode opMode;
    private static Telemetry telemetry;
    private static VuforiaLocalizer vuforia;
    OpenGLMatrix lastLocation = null;
    static OpenGLMatrix pose;
    static VuforiaTrackable relicTemplate;
    //left: 0x6c     right:0x5c
    private static ColorSensor colorLeft;
    private static ColorSensor colorRight;
    private static HardwareMap hardware_map;

    /**
     *
     * @param _opMode to get FTC data
     * @param reds whether or not we are on the red team
     */
    static void initialize(OpMode _opMode, boolean reds) {
        if (_opMode instanceof LinearOpMode) {
            opMode = (LinearOpMode) _opMode;
        } else {
            return;
        }



        hardware_map = opMode.hardwareMap;


        colorLeft = hardware_map.get(ColorSensor.class, "racism");
        colorRight = hardware_map.get(ColorSensor.class, "racism2");
        colorLeft.enableLed(true);
        colorRight.enableLed(true);


        red = reds;

        telemetry = opMode.telemetry;


        BNO055IMU.Parameters IMUparams = new BNO055IMU.Parameters();
        IMUparams.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        IMUparams.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        IMUparams.calibrationDataFile = "Calibfile.json";
        IMUparams.loggingEnabled      = true;
        IMUparams.loggingTag          = "IMU";
        IMUparams.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        gyro = hardware_map.get(BNO055IMU.class, "imu");



        runtime.reset();

        gyro.initialize(IMUparams);
        sleep(5000);
        pause(100);
        angles   = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        telemetry.addData("angle", angles);


        //+0 for the orientation of the rev module
        if(realGyro()+180>360)
            gyroInitial = realGyro()+90 -360;
        else
            gyroInitial = realGyro()+90;


        ((LinearOpMode) _opMode).waitForStart();
        gyroDriftRead();
        initVuforia();


    }

    public static int readColor()
    {

        if(red)
        {

            if(colorRight.red()==colorRight.blue()) return 0;
            if(colorRight.red()<colorRight.blue()) return -1;
            if(colorRight.red()>colorRight.blue()) return 1;
        }
        else
        {
            if(colorRight.red()==colorRight.blue()) return 0;
            if(colorRight.red()<colorRight.blue()) return 1;
            if(colorRight.red()>colorRight.blue()) return -1;
        }
        return 0;
        //return (red ? colorRight.red()>colorRight.blue() : colorLeft.blue()>colorLeft.red());
    }

    public static void vuMark()
    {
        //if(RelicRecoveryVuMark.from(relicTemplate)!=RelicRecoveryVuMark.UNKNOWN)
        oldRobotConstants.vuMark = RelicRecoveryVuMark.from(relicTemplate);

    }
    public static Orientation angle()
    {
        RelicRecoveryVuMark mark = oldRobotConstants.vuMark;
        if(mark != RelicRecoveryVuMark.UNKNOWN)
        pose = ((VuforiaTrackableDefaultListener)relicTemplate.getListener()).getPose();
        return Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

    }

    public static VectorF position()
    {
        RelicRecoveryVuMark mark = RelicRecoveryVuMark.from(relicTemplate);
        if(mark != RelicRecoveryVuMark.UNKNOWN)
            pose = ((VuforiaTrackableDefaultListener)relicTemplate.getListener()).getPose();
        if (pose != null) {
            return pose.getTranslation();
        }
        return null;
    }





    private static void gyroDriftRead() {

        if (realGyro() == gyroInitial)
            gyrochange=0;
        else if (realGyro()<gyroInitial && realGyro()>gyroInitial-180)
            gyrochange = (realGyro() - gyroInitial) / runtime.seconds();
        else if (realGyro()>gyroInitial+180 && realGyro()<gyroInitial+360)
            gyrochange = -(gyroInitial + (360-realGyro())) / runtime.seconds();
        else if (realGyro()>gyroInitial && realGyro()<gyroInitial+180)
            gyrochange = (realGyro() - gyroInitial) / runtime.seconds();
        else if (realGyro()<gyroInitial+180 && realGyro()>gyroInitial-360)
            gyrochange = (gyroInitial + (360-realGyro())) / runtime.seconds();
    }

    public static double readGyro() {
        angles   = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        if ((gyrochange * (runtime.seconds())) + realGyro()-gyroInitial<0)
            return (gyrochange * (runtime.seconds())) + realGyro()  -gyroInitial  +360;
        else if ((gyrochange * (runtime.seconds())) + realGyro()-gyroInitial>360)
            return (gyrochange * (runtime.seconds())) + realGyro()  -gyroInitial  -360;
        else
            return (gyrochange * (runtime.seconds())) + realGyro()-gyroInitial;
    }

    public static void resetGyro()
    {
        gyroInitial=realGyro()+180;
        runtime.reset();
        gyrochange = 0;
    }

    private static double realGyro()
    {

        return 180-angles.firstAngle;

    }

    public static void initVuforia()
    {
        int cameraMonitorViewId = hardware_map.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardware_map.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AYt3nnz/////AAAAGeWqnGxuREQ8gPunRf7bkzYaJ6lsas+H/ryI/7UQ6Kg/QpCi1ObUnVT96byceD0lMQsIV4bqROkXYKwfjL+79oOM19r9qKF3OnRKItM47YmGatBI9Z0u2rkFRRz1rd/ESSZxvBKLsnVn5uaNvTIgkMJ/Lh0HCl0aQfAf1khSVuZR/6mlcAwf++ejAl+lXPdk716k7fXZvnvEDAkWu7GqG2esiLDoXPcsrWIKAbv9UAwSLIvxVIzHTJBgncJ5a3etLPI0bxwlk/1AZb4ZZ6iDFXLoyv7suXac2ek30Tar6UdJ1EXSxdOMlCZRfes8HdpbmBcyElEmC8+mBsJhaaMN+erUF6Es5eCgilirNZ/Rbf0S";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = vuforia.loadTrackablesFromAsset("RelicVuMark");

        relicTemplate = relicTrackables.get(0);
        relicTrackables.activate();
        pose = ((VuforiaTrackableDefaultListener)relicTemplate.getListener()).getPose();
    }

/*    public static class gyroThread extends Thread{
        private static ElapsedTime gyroTime = new ElapsedTime();
        boolean end = false;
        public void run()
        {
            while (!end)
            {
                if(gyroTime.milliseconds() >= 20)
                {
                    gyroTime.reset();
                    angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
                }
            }
        }

        public void end( )
        {
            end=true;
        }

    }
*/
}
