package com.hypixel.hytale.component.system;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class RefChangeSystem<ECS_TYPE, T extends Component<ECS_TYPE>> extends System<ECS_TYPE> implements QuerySystem<ECS_TYPE> {
  @Nonnull
  public abstract ComponentType<ECS_TYPE, T> componentType();
  
  public abstract void onComponentAdded(@Nonnull Ref<ECS_TYPE> paramRef, @Nonnull T paramT, @Nonnull Store<ECS_TYPE> paramStore, @Nonnull CommandBuffer<ECS_TYPE> paramCommandBuffer);
  
  public abstract void onComponentSet(@Nonnull Ref<ECS_TYPE> paramRef, @Nullable T paramT1, @Nonnull T paramT2, @Nonnull Store<ECS_TYPE> paramStore, @Nonnull CommandBuffer<ECS_TYPE> paramCommandBuffer);
  
  public abstract void onComponentRemoved(@Nonnull Ref<ECS_TYPE> paramRef, @Nonnull T paramT, @Nonnull Store<ECS_TYPE> paramStore, @Nonnull CommandBuffer<ECS_TYPE> paramCommandBuffer);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\system\RefChangeSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */