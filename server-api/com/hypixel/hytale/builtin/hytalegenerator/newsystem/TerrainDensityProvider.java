package com.hypixel.hytale.builtin.hytalegenerator.newsystem;

import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
import com.hypixel.hytale.math.vector.Vector3i;
import javax.annotation.Nonnull;

@FunctionalInterface
public interface TerrainDensityProvider {
  double get(@Nonnull Vector3i paramVector3i, @Nonnull WorkerIndexer.Id paramId);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\TerrainDensityProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */