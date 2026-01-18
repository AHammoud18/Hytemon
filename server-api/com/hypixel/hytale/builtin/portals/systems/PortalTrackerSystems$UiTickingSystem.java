/*    */ package com.hypixel.hytale.builtin.portals.systems;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.instances.removal.InstanceDataResource;
/*    */ import com.hypixel.hytale.builtin.portals.resources.PortalWorld;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.tick.DelayedEntitySystem;
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.packets.interface_.UpdatePortal;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.time.Instant;
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
/*    */ public class UiTickingSystem
/*    */   extends DelayedEntitySystem<EntityStore>
/*    */ {
/*    */   public UiTickingSystem() {
/* 63 */     super(1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 68 */     PortalWorld portalWorld = (PortalWorld)store.getResource(PortalWorld.getResourceType());
/* 69 */     if (!portalWorld.exists())
/*    */       return; 
/* 71 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*    */     
/* 73 */     Store<ChunkStore> chunkStore = world.getChunkStore().getStore();
/* 74 */     InstanceDataResource instanceData = (InstanceDataResource)chunkStore.getResource(InstanceDataResource.getResourceType());
/*    */     
/* 76 */     Instant timeout = instanceData.getTimeoutTimer();
/* 77 */     if (timeout == null) {
/*    */       return;
/*    */     }
/*    */     
/* 81 */     PlayerRef playerRef = (PlayerRef)archetypeChunk.getComponent(index, PlayerRef.getComponentType());
/*    */ 
/*    */     
/* 84 */     UpdatePortal packet = portalWorld.getSeesUi().add(playerRef.getUuid()) ? portalWorld.createFullPacket(world) : portalWorld.createUpdatePacket(world);
/* 85 */     playerRef.getPacketHandler().write((Packet)packet);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Query<EntityStore> getQuery() {
/* 91 */     return (Query<EntityStore>)Query.and(new Query[] { (Query)Player.getComponentType(), (Query)PlayerRef.getComponentType() });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\systems\PortalTrackerSystems$UiTickingSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */