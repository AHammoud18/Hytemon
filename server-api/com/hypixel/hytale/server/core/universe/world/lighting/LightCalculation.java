package com.hypixel.hytale.server.core.universe.world.lighting;

import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
import javax.annotation.Nonnull;

public interface LightCalculation {
  void init(@Nonnull WorldChunk paramWorldChunk);
  
  @Nonnull
  CalculationResult calculateLight(@Nonnull Vector3i paramVector3i);
  
  boolean invalidateLightAtBlock(@Nonnull WorldChunk paramWorldChunk, int paramInt1, int paramInt2, int paramInt3, @Nonnull BlockType paramBlockType, int paramInt4, int paramInt5);
  
  boolean invalidateLightInChunkSections(@Nonnull WorldChunk paramWorldChunk, int paramInt1, int paramInt2);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\lighting\LightCalculation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */