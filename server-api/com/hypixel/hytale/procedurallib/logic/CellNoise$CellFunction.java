package com.hypixel.hytale.procedurallib.logic;

import com.hypixel.hytale.procedurallib.logic.cell.CellDistanceFunction;
import com.hypixel.hytale.procedurallib.property.NoiseProperty;

public interface CellFunction {
  double eval(int paramInt1, int paramInt2, double paramDouble1, double paramDouble2, ResultBuffer.ResultBuffer2d paramResultBuffer2d, CellDistanceFunction paramCellDistanceFunction, NoiseProperty paramNoiseProperty);
  
  double eval(int paramInt1, int paramInt2, double paramDouble1, double paramDouble2, double paramDouble3, ResultBuffer.ResultBuffer3d paramResultBuffer3d, CellDistanceFunction paramCellDistanceFunction, NoiseProperty paramNoiseProperty);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\CellNoise$CellFunction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */