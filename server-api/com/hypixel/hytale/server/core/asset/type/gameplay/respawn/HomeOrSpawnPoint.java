/*    */ package com.hypixel.hytale.server.core.asset.type.gameplay.respawn;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.vector.Transform;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HomeOrSpawnPoint
/*    */   implements RespawnController
/*    */ {
/*    */   @Nonnull
/* 22 */   public static final HomeOrSpawnPoint INSTANCE = new HomeOrSpawnPoint();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 28 */   public static final BuilderCodec<HomeOrSpawnPoint> CODEC = BuilderCodec.builder(HomeOrSpawnPoint.class, () -> INSTANCE)
/* 29 */     .build();
/*    */ 
/*    */   
/*    */   public void respawnPlayer(@Nonnull World world, @Nonnull Ref<EntityStore> playerReference, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 33 */     Transform homePosition = Player.getRespawnPosition(playerReference, world.getName(), (ComponentAccessor)commandBuffer);
/* 34 */     commandBuffer.addComponent(playerReference, Teleport.getComponentType(), (Component)new Teleport(null, homePosition));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\gameplay\respawn\HomeOrSpawnPoint.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */