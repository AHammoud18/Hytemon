/*    */ package com.hypixel.hytale.server.core.universe.world;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.math.vector.Transform;
/*    */ import com.hypixel.hytale.math.vector.Vector3f;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.spawn.ISpawnProvider;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SpawnUtil
/*    */ {
/*    */   @Nullable
/*    */   public static TransformComponent applyFirstSpawnTransform(@Nonnull Holder<EntityStore> holder, @Nonnull World world, @Nonnull WorldConfig worldConfig, @Nonnull UUID playerUuid) {
/* 33 */     ISpawnProvider spawnProvider = worldConfig.getSpawnProvider();
/* 34 */     if (spawnProvider == null) {
/* 35 */       return null;
/*    */     }
/*    */     
/* 38 */     Transform spawnPoint = spawnProvider.getSpawnPoint(world, playerUuid);
/*    */ 
/*    */ 
/*    */     
/* 42 */     Vector3f bodyRotation = new Vector3f(0.0F, spawnPoint.getRotation().getYaw(), 0.0F);
/* 43 */     TransformComponent transformComponent = new TransformComponent(spawnPoint.getPosition(), bodyRotation);
/* 44 */     holder.addComponent(TransformComponent.getComponentType(), (Component)transformComponent);
/*    */ 
/*    */     
/* 47 */     HeadRotation headRotationComponent = (HeadRotation)holder.ensureAndGetComponent(HeadRotation.getComponentType());
/* 48 */     headRotationComponent.teleportRotation(spawnPoint.getRotation());
/*    */     
/* 50 */     return transformComponent;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void applyTransform(@Nonnull Holder<EntityStore> holder, @Nonnull Transform transform) {
/* 61 */     TransformComponent transformComponent = (TransformComponent)holder.getComponent(TransformComponent.getComponentType());
/* 62 */     assert transformComponent != null;
/*    */     
/* 64 */     transformComponent.setPosition(transform.getPosition());
/* 65 */     transformComponent.getRotation().setYaw(transform.getRotation().getYaw());
/*    */     
/* 67 */     HeadRotation headRotationComponent = (HeadRotation)holder.ensureAndGetComponent(HeadRotation.getComponentType());
/* 68 */     headRotationComponent.teleportRotation(transform.getRotation());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\SpawnUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */