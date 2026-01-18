package com.hypixel.hytale.component;

import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public interface IResourceStorage {
  @Nonnull
  <T extends Resource<ECS_TYPE>, ECS_TYPE> CompletableFuture<T> load(@Nonnull Store<ECS_TYPE> paramStore, @Nonnull ComponentRegistry.Data<ECS_TYPE> paramData, @Nonnull ResourceType<ECS_TYPE, T> paramResourceType);
  
  @Nonnull
  <T extends Resource<ECS_TYPE>, ECS_TYPE> CompletableFuture<Void> save(@Nonnull Store<ECS_TYPE> paramStore, @Nonnull ComponentRegistry.Data<ECS_TYPE> paramData, @Nonnull ResourceType<ECS_TYPE, T> paramResourceType, T paramT);
  
  @Nonnull
  <T extends Resource<ECS_TYPE>, ECS_TYPE> CompletableFuture<Void> remove(@Nonnull Store<ECS_TYPE> paramStore, @Nonnull ComponentRegistry.Data<ECS_TYPE> paramData, @Nonnull ResourceType<ECS_TYPE, T> paramResourceType);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\IResourceStorage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */