package com.hypixel.hytale.server.npc.blackboard.view.attitude;

import com.hypixel.hytale.component.ComponentAccessor;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.asset.type.attitude.Attitude;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.role.Role;
import javax.annotation.Nonnull;

public interface IAttitudeProvider {
  public static final int OVERRIDE_PRIORITY = 0;
  
  Attitude getAttitude(@Nonnull Ref<EntityStore> paramRef1, @Nonnull Role paramRole, @Nonnull Ref<EntityStore> paramRef2, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\blackboard\view\attitude\IAttitudeProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */