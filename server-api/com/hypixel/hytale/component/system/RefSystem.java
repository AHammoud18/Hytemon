package com.hypixel.hytale.component.system;

import com.hypixel.hytale.component.AddReason;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.RemoveReason;
import com.hypixel.hytale.component.Store;
import javax.annotation.Nonnull;

public abstract class RefSystem<ECS_TYPE> extends System<ECS_TYPE> implements QuerySystem<ECS_TYPE> {
  public abstract void onEntityAdded(@Nonnull Ref<ECS_TYPE> paramRef, @Nonnull AddReason paramAddReason, @Nonnull Store<ECS_TYPE> paramStore, @Nonnull CommandBuffer<ECS_TYPE> paramCommandBuffer);
  
  public abstract void onEntityRemove(@Nonnull Ref<ECS_TYPE> paramRef, @Nonnull RemoveReason paramRemoveReason, @Nonnull Store<ECS_TYPE> paramStore, @Nonnull CommandBuffer<ECS_TYPE> paramCommandBuffer);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\system\RefSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */