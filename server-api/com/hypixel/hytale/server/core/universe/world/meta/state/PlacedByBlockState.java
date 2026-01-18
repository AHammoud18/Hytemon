package com.hypixel.hytale.server.core.universe.world.meta.state;

import com.hypixel.hytale.component.ComponentAccessor;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import javax.annotation.Nonnull;

public interface PlacedByBlockState {
  void placedBy(@Nonnull Ref<EntityStore> paramRef, @Nonnull String paramString, @Nonnull BlockState paramBlockState, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\meta\state\PlacedByBlockState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */