package com.hypixel.hytale.builtin.buildertools.snapshot;

import com.hypixel.hytale.component.ComponentAccessor;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import javax.annotation.Nullable;

public interface SelectionSnapshot<T extends SelectionSnapshot<?>> {
  @Nullable
  T restore(Ref<EntityStore> paramRef, Player paramPlayer, World paramWorld, ComponentAccessor<EntityStore> paramComponentAccessor);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\snapshot\SelectionSnapshot.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */