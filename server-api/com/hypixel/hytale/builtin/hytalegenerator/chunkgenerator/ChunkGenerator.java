package com.hypixel.hytale.builtin.hytalegenerator.chunkgenerator;

import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedChunk;
import javax.annotation.Nonnull;

public interface ChunkGenerator {
  GeneratedChunk generate(@Nonnull ChunkRequest.Arguments paramArguments);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\chunkgenerator\ChunkGenerator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */