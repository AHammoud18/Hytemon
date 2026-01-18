package com.hypixel.hytale.procedurallib.logic.point;

import com.hypixel.hytale.procedurallib.logic.ResultBuffer;

public interface IPointGenerator {
  ResultBuffer.ResultBuffer2d nearest2D(int paramInt, double paramDouble1, double paramDouble2);
  
  ResultBuffer.ResultBuffer3d nearest3D(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3);
  
  ResultBuffer.ResultBuffer2d transition2D(int paramInt, double paramDouble1, double paramDouble2);
  
  ResultBuffer.ResultBuffer3d transition3D(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3);
  
  void collect(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, PointConsumer2d paramPointConsumer2d);
  
  double getInterval();
  
  @FunctionalInterface
  public static interface PointConsumer2d {
    void accept(double param1Double1, double param1Double2);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\point\IPointGenerator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */