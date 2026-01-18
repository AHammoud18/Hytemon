package com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth;

import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.MaterialProvider;
import javax.annotation.Nullable;

public abstract class Layer<V> {
  public abstract int getThicknessAt(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, double paramDouble);
  
  @Nullable
  public abstract MaterialProvider<V> getMaterialProvider();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\materialproviders\spaceanddepth\SpaceAndDepthMaterialProvider$Layer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */