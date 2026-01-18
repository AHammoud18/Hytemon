/*    */ package com.hypixel.hytale.builtin.instances.config;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.instances.InstancesPlugin;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.server.core.asset.type.gameplay.respawn.HomeOrSpawnPoint;
/*    */ import com.hypixel.hytale.server.core.asset.type.gameplay.respawn.RespawnController;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExitInstance
/*    */   implements RespawnController
/*    */ {
/*    */   @Nonnull
/*    */   public static final BuilderCodec<ExitInstance> CODEC;
/*    */   
/*    */   static {
/* 35 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ExitInstance.class, ExitInstance::new).append(new KeyedCodec("Fallback", (Codec)RespawnController.CODEC), (o, i) -> o.fallback = i, o -> o.fallback).addValidator(Validators.nonNull()).add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/* 40 */   private RespawnController fallback = (RespawnController)HomeOrSpawnPoint.INSTANCE;
/*    */ 
/*    */ 
/*    */   
/*    */   public void respawnPlayer(@Nonnull World world, @Nonnull Ref<EntityStore> playerReference, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*    */     try {
/* 46 */       InstancesPlugin.exitInstance(playerReference, (ComponentAccessor)commandBuffer);
/* 47 */     } catch (Exception e) {
/* 48 */       PlayerRef playerRefComponent = (PlayerRef)commandBuffer.getComponent(playerReference, PlayerRef.getComponentType());
/* 49 */       assert playerRefComponent != null;
/* 50 */       ((HytaleLogger.Api)InstancesPlugin.get().getLogger().at(Level.WARNING).withCause(e)).log(playerRefComponent.getUsername() + " failed to leave an instance");
/* 51 */       this.fallback.respawnPlayer(world, playerReference, commandBuffer);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\instances\config\ExitInstance.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */