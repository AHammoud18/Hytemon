package com.hypixel.hytale.component.system;

import com.hypixel.hytale.component.AddReason;
import com.hypixel.hytale.component.Holder;
import com.hypixel.hytale.component.RemoveReason;
import com.hypixel.hytale.component.Store;
import javax.annotation.Nonnull;

public abstract class HolderSystem<ECS_TYPE> extends System<ECS_TYPE> implements QuerySystem<ECS_TYPE> {
  public abstract void onEntityAdd(@Nonnull Holder<ECS_TYPE> paramHolder, @Nonnull AddReason paramAddReason, @Nonnull Store<ECS_TYPE> paramStore);
  
  public abstract void onEntityRemoved(@Nonnull Holder<ECS_TYPE> paramHolder, @Nonnull RemoveReason paramRemoveReason, @Nonnull Store<ECS_TYPE> paramStore);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\system\HolderSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */