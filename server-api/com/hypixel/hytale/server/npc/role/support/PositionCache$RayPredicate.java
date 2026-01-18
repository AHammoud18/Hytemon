package com.hypixel.hytale.server.npc.role.support;

import com.hypixel.hytale.component.ComponentAccessor;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import javax.annotation.Nonnull;

@FunctionalInterface
public interface RayPredicate<T> {
  boolean test(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, T paramT, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\role\support\PositionCache$RayPredicate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */