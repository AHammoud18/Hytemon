/*    */ package com.hypixel.hytale.builtin.ambience.systems;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.ambience.components.AmbienceTracker;
/*    */ import com.hypixel.hytale.builtin.ambience.resources.AmbienceResource;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.packets.world.UpdateEnvironmentMusic;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
/*    */ public class Tick
/*    */   extends EntityTickingSystem<EntityStore>
/*    */ {
/*    */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 46 */     AmbienceResource ambienceResource = (AmbienceResource)store.getResource(AmbienceResource.getResourceType());
/* 47 */     AmbienceTracker tracker = (AmbienceTracker)archetypeChunk.getComponent(index, AmbienceTracker.getComponentType());
/* 48 */     PlayerRef playerRef = (PlayerRef)archetypeChunk.getComponent(index, PlayerRef.getComponentType());
/*    */     
/* 50 */     int have = tracker.getForcedMusicIndex();
/* 51 */     int desired = ambienceResource.getForcedMusicIndex();
/* 52 */     if (have == desired) {
/*    */       return;
/*    */     }
/*    */     
/* 56 */     tracker.setForcedMusicIndex(desired);
/*    */     
/* 58 */     UpdateEnvironmentMusic pooledPacket = tracker.getMusicPacket();
/* 59 */     pooledPacket.environmentIndex = desired;
/* 60 */     playerRef.getPacketHandler().write((Packet)pooledPacket);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Query<EntityStore> getQuery() {
/* 66 */     return ForcedMusicSystems.TICK_QUERY;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\ambience\systems\ForcedMusicSystems$Tick.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */