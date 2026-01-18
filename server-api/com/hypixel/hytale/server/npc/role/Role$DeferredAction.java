package com.hypixel.hytale.server.npc.role;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import javax.annotation.Nonnull;

@FunctionalInterface
public interface DeferredAction {
  boolean tick(@Nonnull Ref<EntityStore> paramRef, @Nonnull Role paramRole, double paramDouble, @Nonnull Store<EntityStore> paramStore);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\role\Role$DeferredAction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */