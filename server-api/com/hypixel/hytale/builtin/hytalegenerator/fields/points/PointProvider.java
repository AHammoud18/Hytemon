package com.hypixel.hytale.builtin.hytalegenerator.fields.points;

import com.hypixel.hytale.math.vector.Vector2d;
import com.hypixel.hytale.math.vector.Vector2i;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3i;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nonnull;

public interface PointProvider {
  List<Vector3i> points3i(@Nonnull Vector3i paramVector3i1, @Nonnull Vector3i paramVector3i2);
  
  List<Vector2i> points2i(@Nonnull Vector2i paramVector2i1, @Nonnull Vector2i paramVector2i2);
  
  List<Integer> points1i(int paramInt1, int paramInt2);
  
  void points3i(@Nonnull Vector3i paramVector3i1, @Nonnull Vector3i paramVector3i2, @Nonnull Consumer<Vector3i> paramConsumer);
  
  void points2i(@Nonnull Vector2i paramVector2i1, @Nonnull Vector2i paramVector2i2, @Nonnull Consumer<Vector2i> paramConsumer);
  
  void points1i(int paramInt1, int paramInt2, @Nonnull Consumer<Integer> paramConsumer);
  
  List<Vector3d> points3d(@Nonnull Vector3d paramVector3d1, @Nonnull Vector3d paramVector3d2);
  
  List<Vector2d> points2d(@Nonnull Vector2d paramVector2d1, @Nonnull Vector2d paramVector2d2);
  
  List<Double> points1d(double paramDouble1, double paramDouble2);
  
  void points3d(@Nonnull Vector3d paramVector3d1, @Nonnull Vector3d paramVector3d2, @Nonnull Consumer<Vector3d> paramConsumer);
  
  void points2d(@Nonnull Vector2d paramVector2d1, @Nonnull Vector2d paramVector2d2, @Nonnull Consumer<Vector2d> paramConsumer);
  
  void points1d(double paramDouble1, double paramDouble2, @Nonnull Consumer<Double> paramConsumer);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\fields\points\PointProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */