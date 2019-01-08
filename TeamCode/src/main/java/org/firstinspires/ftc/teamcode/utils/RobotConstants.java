package org.firstinspires.ftc.teamcode.utils;

import java.util.ArrayList;

public class RobotConstants {
    static final double wheelDiameter = 4.25;
    static final int encoderCPR = 1140;
    static final double gearRatio = 1;

    public static double integral(ArrayList<Double> a, ArrayList<Double> b){
        double integral = 0;
        for (int i=0; i<a.size()-1; i++){
            integral += a.get(i) * b.get(i);
        }

        return integral;
    }
}
