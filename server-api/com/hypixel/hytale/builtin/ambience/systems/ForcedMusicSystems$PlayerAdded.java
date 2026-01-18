/*    */ package com.hypixel.hytale.builtin.ambience.systems;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.ambience.components.AmbienceTracker;
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.HolderSystem;
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.packets.world.UpdateEnvironmentMusic;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerAdded
/*    */   extends HolderSystem<EntityStore>
/*    */ {
/*    */   public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 23 */     holder.ensureComponent(AmbienceTracker.getComponentType());
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {
/* 28 */     AmbienceTracker tracker = (AmbienceTracker)holder.getComponent(AmbienceTracker.getComponentType());
/* 29 */     PlayerRef playerRef = (PlayerRef)holder.getComponent(PlayerRef.getComponentType());
/*    */     
/* 31 */     UpdateEnvironmentMusic pooledPacket = tracker.getMusicPacket();
/* 32 */     pooledPacket.environmentIndex = 0;
/* 33 */     playerRef.getPacketHandler().write((Packet)pooledPacket);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Query<EntityStore> getQuery() {
/* 39 */     return (Query<EntityStore>)PlayerRef.getComponentType();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\ambience\systems\ForcedMusicSystems$PlayerAdded.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */