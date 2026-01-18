package com.hypixel.hytale.server.core.universe.playerdata;

import com.hypixel.hytale.component.Holder;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public interface PlayerStorage {
  @Nonnull
  CompletableFuture<Holder<EntityStore>> load(@Nonnull UUID paramUUID);
  
  @Nonnull
  CompletableFuture<Void> save(@Nonnull UUID paramUUID, @Nonnull Holder<EntityStore> paramHolder);
  
  @Nonnull
  CompletableFuture<Void> remove(@Nonnull UUID paramUUID);
  
  @Nonnull
  Set<UUID> getPlayers() throws IOException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\playerdata\PlayerStorage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */