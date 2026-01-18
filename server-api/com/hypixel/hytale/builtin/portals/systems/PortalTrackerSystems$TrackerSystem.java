/*    */ package com.hypixel.hytale.builtin.portals.systems;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.portals.resources.PortalWorld;
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.RefSystem;
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.packets.interface_.UpdatePortal;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
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
/*    */ 
/*    */ public class TrackerSystem
/*    */   extends RefSystem<EntityStore>
/*    */ {
/*    */   public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 33 */     PortalWorld portalWorld = (PortalWorld)store.getResource(PortalWorld.getResourceType());
/* 34 */     if (!portalWorld.exists())
/*    */       return; 
/* 36 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*    */     
/* 38 */     PlayerRef playerRef = (PlayerRef)commandBuffer.getComponent(ref, PlayerRef.getComponentType());
/* 39 */     if (portalWorld.getSeesUi().add(playerRef.getUuid())) {
/* 40 */       UpdatePortal packet = portalWorld.createFullPacket(world);
/* 41 */       playerRef.getPacketHandler().write((Packet)packet);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 47 */     PortalWorld portalWorld = (PortalWorld)store.getResource(PortalWorld.getResourceType());
/* 48 */     if (!portalWorld.exists())
/*    */       return; 
/* 50 */     PlayerRef playerRef = (PlayerRef)commandBuffer.getComponent(ref, PlayerRef.getComponentType());
/* 51 */     playerRef.getPacketHandler().write((Packet)new UpdatePortal(null, null));
/* 52 */     portalWorld.getSeesUi().remove(playerRef.getUuid());
/*    */   }
/*    */ 
/*    */   
/*    */   public Query<EntityStore> getQuery() {
/* 57 */     return (Query<EntityStore>)Query.and(new Query[] { (Query)Player.getComponentType(), (Query)PlayerRef.getComponentType() });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\systems\PortalTrackerSystems$TrackerSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */