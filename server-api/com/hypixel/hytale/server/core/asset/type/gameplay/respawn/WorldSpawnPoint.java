/*    */ package com.hypixel.hytale.server.core.asset.type.gameplay.respawn;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.vector.Transform;
/*    */ import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.spawn.ISpawnProvider;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*    */ 
/*    */ public class WorldSpawnPoint implements RespawnController {
/* 15 */   public static final WorldSpawnPoint INSTANCE = new WorldSpawnPoint();
/*    */   
/* 17 */   public static final BuilderCodec<WorldSpawnPoint> CODEC = BuilderCodec.builder(WorldSpawnPoint.class, () -> INSTANCE).build();
/*    */ 
/*    */   
/*    */   public void respawnPlayer(@NonNullDecl World world, @NonNullDecl Ref<EntityStore> playerReference, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
/* 21 */     ISpawnProvider spawnProvider = world.getWorldConfig().getSpawnProvider();
/* 22 */     Transform spawnPoint = spawnProvider.getSpawnPoint(playerReference, (ComponentAccessor)commandBuffer);
/* 23 */     commandBuffer.addComponent(playerReference, Teleport.getComponentType(), (Component)new Teleport(null, spawnPoint));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\gameplay\respawn\WorldSpawnPoint.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */