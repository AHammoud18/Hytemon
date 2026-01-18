package com.hypixel.hytale.procedurallib.property;

import com.hypixel.hytale.procedurallib.NoiseFunction2d;
import com.hypixel.hytale.procedurallib.NoiseFunction3d;

interface FractalFunction {
  double get(int paramInt1, int paramInt2, double paramDouble1, double paramDouble2, int paramInt3, double paramDouble3, double paramDouble4, NoiseFunction2d paramNoiseFunction2d);
  
  double get(int paramInt1, int paramInt2, double paramDouble1, double paramDouble2, double paramDouble3, int paramInt3, double paramDouble4, double paramDouble5, NoiseFunction3d paramNoiseFunction3d);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\property\FractalNoiseProperty$FractalFunction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */