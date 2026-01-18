/*    */ package com.hypixel.hytale.server.core.asset.type.gameplay.respawn;
/*    */ 
/*    */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public interface RespawnController
/*    */ {
/*    */   @Nonnull
/* 22 */   public static final CodecMapCodec<RespawnController> CODEC = new CodecMapCodec("Type");
/*    */   
/*    */   void respawnPlayer(@Nonnull World paramWorld, @Nonnull Ref<EntityStore> paramRef, @Nonnull CommandBuffer<EntityStore> paramCommandBuffer);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\gameplay\respawn\RespawnController.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */