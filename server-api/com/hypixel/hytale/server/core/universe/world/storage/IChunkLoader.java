package com.hypixel.hytale.server.core.universe.world.storage;

import com.hypixel.hytale.component.Holder;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public interface IChunkLoader extends Closeable {
  @Nonnull
  CompletableFuture<Holder<ChunkStore>> loadHolder(int paramInt1, int paramInt2);
  
  @Nonnull
  LongSet getIndexes() throws IOException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\storage\IChunkLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */