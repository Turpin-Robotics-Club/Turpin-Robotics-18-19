package org.firstinspires.ftc.teamcode.oldCode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.oldCode.oldUtils.oldSensors;

@TeleOp(name = "VuMarlr test", group = "Tests")
public class vuTest extends OpMode {

    VuforiaLocalizer vuforia;
    VuforiaTrackable relicTemplate;
    OpenGLMatrix pose;
    public void init()
    {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AYt3nnz/////AAAAGeWqnGxuREQ8gPunRf7bkzYaJ6lsas+H/ryI/7UQ6Kg/QpCi1ObUnVT96byceD0lMQsIV4bqROkXYKwfjL+79oOM19r9qKF3OnRKItM47YmGatBI9Z0u2rkFRRz1rd/ESSZxvBKLsnVn5uaNvTIgkMJ/Lh0HCl0aQfAf1khSVuZR/6mlcAwf++ejAl+lXPdk716k7fXZvnvEDAkWu7GqG2esiLDoXPcsrWIKAbv9UAwSLIvxVIzHTJBgncJ5a3etLPI0bxwlk/1AZb4ZZ6iDFXLoyv7suXac2ek30Tar6UdJ1EXSxdOMlCZRfes8HdpbmBcyElEmC8+mBsJhaaMN+erUF6Es5eCgilirNZ/Rbf0S";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = vuforia.loadTrackablesFromAsset("RelicVuMark");

        relicTemplate = relicTrackables.get(0);
        relicTrackables.activate();
        pose = ((VuforiaTrackableDefaultListener)relicTemplate.getListener()).getPose();
    }
    public void loop()
    {
        oldSensors.vuMark();
        telemetry.addData("angle1", oldSensors.angle().firstAngle);
        telemetry.addData("angle2", oldSensors.angle().secondAngle);
        telemetry.addData("angle3", oldSensors.angle().thirdAngle);
    }
}
