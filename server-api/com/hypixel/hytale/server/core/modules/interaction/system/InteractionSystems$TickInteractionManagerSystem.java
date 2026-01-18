/*    */ package com.hypixel.hytale.server.core.modules.interaction.system;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.SystemGroup;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.packets.interaction.SyncInteractionChain;
/*    */ import com.hypixel.hytale.protocol.packets.interaction.SyncInteractionChains;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionManager;
/*    */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageModule;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatsSystems;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.InteractionModule;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*    */ import java.util.logging.Level;
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
/*    */ public class TickInteractionManagerSystem
/*    */   extends EntityTickingSystem<EntityStore>
/*    */   implements EntityStatsSystems.StatModifyingSystem
/*    */ {
/*    */   @Nonnull
/* 48 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
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
/*    */   @Nonnull
/* 60 */   private final ComponentType<EntityStore, InteractionManager> interactionManagerComponent = InteractionModule.get().getInteractionManagerComponent();
/*    */ 
/*    */ 
/*    */   
/*    */   public Query<EntityStore> getQuery() {
/* 65 */     return (Query)this.interactionManagerComponent;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 70 */     Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/*    */     
/*    */     try {
/* 73 */       InteractionManager interactionManager = (InteractionManager)archetypeChunk.getComponent(index, this.interactionManagerComponent);
/* 74 */       assert interactionManager != null;
/* 75 */       PlayerRef playerRef = (PlayerRef)archetypeChunk.getComponent(index, PlayerRef.getComponentType());
/* 76 */       interactionManager.tick(ref, commandBuffer, dt);
/*    */       
/* 78 */       ObjectList<SyncInteractionChain> syncPackets = interactionManager.getSyncPackets();
/* 79 */       if (playerRef != null && !syncPackets.isEmpty()) {
/* 80 */         playerRef.getPacketHandler().writeNoCache((Packet)new SyncInteractionChains((SyncInteractionChain[])syncPackets.toArray(x$0 -> new SyncInteractionChain[x$0])));
/* 81 */         syncPackets.clear();
/*    */       } 
/* 83 */     } catch (Throwable e) {
/* 84 */       ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(e)).log("Exception while ticking entity interactions! Removing!");
/* 85 */       commandBuffer.removeEntity(ref, RemoveReason.REMOVE);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public SystemGroup<EntityStore> getGroup() {
/* 92 */     return DamageModule.get().getGatherDamageGroup();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\system\InteractionSystems$TickInteractionManagerSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */