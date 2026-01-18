package com.hypixel.hytale.server.core.modules.projectile.config;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import javax.annotation.Nonnull;

@FunctionalInterface
public interface BounceConsumer {
  void onBounce(@Nonnull Ref<EntityStore> paramRef, @Nonnull Vector3d paramVector3d, @Nonnull CommandBuffer<EntityStore> paramCommandBuffer);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\projectile\config\BounceConsumer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */