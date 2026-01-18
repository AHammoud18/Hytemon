package com.hypixel.hytale.server.npc.blackboard.view.interaction;

import com.hypixel.hytale.component.ComponentAccessor;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import javax.annotation.Nonnull;

public interface ReservationProvider {
  @Nonnull
  ReservationStatus getReservationStatus(@Nonnull Ref<EntityStore> paramRef1, @Nonnull Ref<EntityStore> paramRef2, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\blackboard\view\interaction\ReservationProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */