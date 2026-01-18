package com.hypixel.hytale.server.npc.instructions;

import com.hypixel.hytale.component.ComponentAccessor;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.movement.Steering;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
import com.hypixel.hytale.server.npc.util.IAnnotatedComponent;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface Motion extends RoleStateChange, IAnnotatedComponent {
  default void preComputeSteering(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nullable InfoProvider provider, @Nonnull Store<EntityStore> store) {}
  
  default void activate(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {}
  
  default void deactivate(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {}
  
  boolean computeSteering(@Nonnull Ref<EntityStore> paramRef, @Nonnull Role paramRole, @Nullable InfoProvider paramInfoProvider, double paramDouble, @Nonnull Steering paramSteering, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\instructions\Motion.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */