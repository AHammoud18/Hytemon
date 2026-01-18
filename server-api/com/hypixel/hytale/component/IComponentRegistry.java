package com.hypixel.hytale.component;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.event.EntityEventType;
import com.hypixel.hytale.component.event.WorldEventType;
import com.hypixel.hytale.component.spatial.SpatialResource;
import com.hypixel.hytale.component.spatial.SpatialStructure;
import com.hypixel.hytale.component.system.ISystem;
import java.util.function.Supplier;
import javax.annotation.Nonnull;

public interface IComponentRegistry<ECS_TYPE> {
  @Nonnull
  <T extends Component<ECS_TYPE>> ComponentType<ECS_TYPE, T> registerComponent(@Nonnull Class<? super T> paramClass, @Nonnull Supplier<T> paramSupplier);
  
  @Nonnull
  <T extends Component<ECS_TYPE>> ComponentType<ECS_TYPE, T> registerComponent(@Nonnull Class<? super T> paramClass, @Nonnull String paramString, @Nonnull BuilderCodec<T> paramBuilderCodec);
  
  @Nonnull
  <T extends Resource<ECS_TYPE>> ResourceType<ECS_TYPE, T> registerResource(@Nonnull Class<? super T> paramClass, @Nonnull Supplier<T> paramSupplier);
  
  @Nonnull
  <T extends Resource<ECS_TYPE>> ResourceType<ECS_TYPE, T> registerResource(@Nonnull Class<? super T> paramClass, @Nonnull String paramString, @Nonnull BuilderCodec<T> paramBuilderCodec);
  
  <T extends ISystem<ECS_TYPE>> SystemType<ECS_TYPE, T> registerSystemType(@Nonnull Class<? super T> paramClass);
  
  @Nonnull
  <T extends com.hypixel.hytale.component.system.EcsEvent> EntityEventType<ECS_TYPE, T> registerEntityEventType(@Nonnull Class<? super T> paramClass);
  
  @Nonnull
  <T extends com.hypixel.hytale.component.system.EcsEvent> WorldEventType<ECS_TYPE, T> registerWorldEventType(@Nonnull Class<? super T> paramClass);
  
  @Nonnull
  SystemGroup<ECS_TYPE> registerSystemGroup();
  
  void registerSystem(@Nonnull ISystem<ECS_TYPE> paramISystem);
  
  ResourceType<ECS_TYPE, SpatialResource<Ref<ECS_TYPE>, ECS_TYPE>> registerSpatialResource(@Nonnull Supplier<SpatialStructure<Ref<ECS_TYPE>>> paramSupplier);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\IComponentRegistry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */