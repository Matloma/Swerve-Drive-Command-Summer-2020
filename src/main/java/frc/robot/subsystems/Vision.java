/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import io.github.pseudoresonance.pixy2api.*;
import io.github.pseudoresonance.pixy2api.Pixy2CCC.Block;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import java.util.ArrayList;

public class Vision extends SubsystemBase {
  private Pixy2 pixy;
  boolean isCamera = false;
  int state = -1;
  /**
   * Creates a new Vision.
   */
  public Vision(SwerveDrive driveTrain) {
    pixy = Pixy2.createInstance(Pixy2.LinkType.SPI);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void track(SwerveDrive driveTrain){  

    if(!isCamera)
      state = pixy.init(1);
    isCamera = state>=0;


    SmartDashboard.putBoolean("Camera", isCamera);
    pixy.getCCC().getBlocks(false, 255, 255);
    ArrayList<Block> blocks = pixy.getCCC().getBlocks();
    if(blocks.size() > 0){
      double xcoord = blocks.get(0).getX();
      double ycoord = blocks.get(0).getY();
      String data = blocks.get(0).toString();
      SmartDashboard.putBoolean("present", true);
      SmartDashboard.putNumber("X-Coord", xcoord);
      SmartDashboard.putNumber("Y-Coord", ycoord);
      SmartDashboard.putString("Data", data);
      
      if(xcoord<pixy.getFrameWidth()/2-Constants.pixyPrecisionInPixels)   //This allows for a precision of 10 pixels on the Pixy2. Can be modified at any point during testing
        driveTrain.drive(0, 0, -.15);
      else if(xcoord>pixy.getFrameWidth()/2+Constants.pixyPrecisionInPixels)
        driveTrain.drive(0, 0, .15);
      else if(xcoord>pixy.getFrameWidth()/2-Constants.pixyPrecisionInPixels&&xcoord<pixy.getFrameWidth()/2+Constants.pixyPrecisionInPixels)
        driveTrain.stop();
    }
    else 
      SmartDashboard.putBoolean("present", false);
    SmartDashboard.putNumber("Blocks", blocks.size());
  }

}
