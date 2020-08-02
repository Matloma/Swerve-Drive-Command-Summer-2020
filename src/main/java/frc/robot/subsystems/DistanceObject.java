/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

/**
 * Add your docs here.
 */
public class DistanceObject {

    private double theta;
    private double distance;
    private double height;

    //Constants
    private final double v = 2.6;
    private final double hi = 1;

    public DistanceObject(double initTheta){
        theta = initTheta;
        distance = findDistance(theta);
        height = 2.49 - hi; 
    }

    private double findDistance(double t){
        double d = 0;
        d = v*Math.cos(Math.toRadians(t));
        d *= ( ( (-1)*(v)*(Math.sin(Math.toRadians(t))) + Math.sqrt( (Math.pow( ((v)*(Math.sin(Math.toRadians(theta)))), 2)) + 4*(4.905)*(-height)))/-9.81);
        return d;
    }

    public double getTheta(){
        return theta;
    }

    public double getDistance(){
        return distance;
    }
}
