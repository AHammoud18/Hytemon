package com.hypixel.hytale.component.spatial;

import com.hypixel.hytale.math.vector.Vector3d;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface SpatialStructure<T> {
  int size();
  
  void rebuild(@Nonnull SpatialData<T> paramSpatialData);
  
  @Nullable
  T closest(@Nonnull Vector3d paramVector3d);
  
  void collect(@Nonnull Vector3d paramVector3d, double paramDouble, @Nonnull List<T> paramList);
  
  void collectCylinder(@Nonnull Vector3d paramVector3d, double paramDouble1, double paramDouble2, @Nonnull List<T> paramList);
  
  void collectBox(@Nonnull Vector3d paramVector3d1, @Nonnull Vector3d paramVector3d2, @Nonnull List<T> paramList);
  
  void ordered(@Nonnull Vector3d paramVector3d, double paramDouble, @Nonnull List<T> paramList);
  
  void ordered3DAxis(@Nonnull Vector3d paramVector3d, double paramDouble1, double paramDouble2, double paramDouble3, @Nonnull List<T> paramList);
  
  @Nonnull
  String dump();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\spatial\SpatialStructure.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */