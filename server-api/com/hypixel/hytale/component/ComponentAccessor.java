package com.hypixel.hytale.component;

import com.hypixel.hytale.component.event.EntityEventType;
import com.hypixel.hytale.component.event.WorldEventType;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface ComponentAccessor<ECS_TYPE> {
  @Nullable
  <T extends Component<ECS_TYPE>> T getComponent(@Nonnull Ref<ECS_TYPE> paramRef, @Nonnull ComponentType<ECS_TYPE, T> paramComponentType);
  
  @Nonnull
  <T extends Component<ECS_TYPE>> T ensureAndGetComponent(@Nonnull Ref<ECS_TYPE> paramRef, @Nonnull ComponentType<ECS_TYPE, T> paramComponentType);
  
  @Nonnull
  Archetype<ECS_TYPE> getArchetype(@Nonnull Ref<ECS_TYPE> paramRef);
  
  @Nonnull
  <T extends Resource<ECS_TYPE>> T getResource(@Nonnull ResourceType<ECS_TYPE, T> paramResourceType);
  
  @Nonnull
  ECS_TYPE getExternalData();
  
  <T extends Component<ECS_TYPE>> void putComponent(@Nonnull Ref<ECS_TYPE> paramRef, @Nonnull ComponentType<ECS_TYPE, T> paramComponentType, @Nonnull T paramT);
  
  <T extends Component<ECS_TYPE>> void addComponent(@Nonnull Ref<ECS_TYPE> paramRef, @Nonnull ComponentType<ECS_TYPE, T> paramComponentType, @Nonnull T paramT);
  
  <T extends Component<ECS_TYPE>> T addComponent(@Nonnull Ref<ECS_TYPE> paramRef, @Nonnull ComponentType<ECS_TYPE, T> paramComponentType);
  
  Ref<ECS_TYPE>[] addEntities(@Nonnull Holder<ECS_TYPE>[] paramArrayOfHolder, @Nonnull AddReason paramAddReason);
  
  @Nullable
  Ref<ECS_TYPE> addEntity(@Nonnull Holder<ECS_TYPE> paramHolder, @Nonnull AddReason paramAddReason);
  
  @Nonnull
  Holder<ECS_TYPE> removeEntity(@Nonnull Ref<ECS_TYPE> paramRef, @Nonnull Holder<ECS_TYPE> paramHolder, @Nonnull RemoveReason paramRemoveReason);
  
  <T extends Component<ECS_TYPE>> void removeComponent(@Nonnull Ref<ECS_TYPE> paramRef, @Nonnull ComponentType<ECS_TYPE, T> paramComponentType);
  
  <T extends Component<ECS_TYPE>> void tryRemoveComponent(@Nonnull Ref<ECS_TYPE> paramRef, @Nonnull ComponentType<ECS_TYPE, T> paramComponentType);
  
  <Event extends com.hypixel.hytale.component.system.EcsEvent> void invoke(@Nonnull Ref<ECS_TYPE> paramRef, @Nonnull Event paramEvent);
  
  <Event extends com.hypixel.hytale.component.system.EcsEvent> void invoke(@Nonnull EntityEventType<ECS_TYPE, Event> paramEntityEventType, @Nonnull Ref<ECS_TYPE> paramRef, @Nonnull Event paramEvent);
  
  <Event extends com.hypixel.hytale.component.system.EcsEvent> void invoke(@Nonnull Event paramEvent);
  
  <Event extends com.hypixel.hytale.component.system.EcsEvent> void invoke(@Nonnull WorldEventType<ECS_TYPE, Event> paramWorldEventType, @Nonnull Event paramEvent);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\ComponentAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */