/*    */ package com.hypixel.hytale.server.core.universe.world.spawn;
/*    */ 
/*    */ import com.hypixel.hytale.codec.lookup.BuilderCodecMapCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.vector.Transform;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.entity.Entity;
/*    */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public interface ISpawnProvider
/*    */ {
/*    */   static {
/* 19 */     if (null.$assertionsDisabled);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 25 */   public static final BuilderCodecMapCodec<ISpawnProvider> CODEC = new BuilderCodecMapCodec(true);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default Transform getSpawnPoint(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 35 */     UUIDComponent uuidComponent = (UUIDComponent)componentAccessor.getComponent(ref, UUIDComponent.getComponentType());
/* 36 */     if (!null.$assertionsDisabled && uuidComponent == null) throw new AssertionError();
/*    */     
/* 38 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/* 39 */     return getSpawnPoint(world, uuidComponent.getUuid());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated(forRemoval = true)
/*    */   default Transform getSpawnPoint(@Nonnull Entity entity) {
/* 51 */     return getSpawnPoint(entity.getWorld(), entity.getUuid());
/*    */   }
/*    */   
/*    */   Transform getSpawnPoint(@Nonnull World paramWorld, @Nonnull UUID paramUUID);
/*    */   
/*    */   @Deprecated
/*    */   Transform[] getSpawnPoints();
/*    */   
/*    */   boolean isWithinSpawnDistance(@Nonnull Vector3d paramVector3d, double paramDouble);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\spawn\ISpawnProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */