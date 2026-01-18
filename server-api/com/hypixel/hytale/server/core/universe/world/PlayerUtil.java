/*     */ package com.hypixel.hytale.server.core.universe.world;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.function.consumer.TriConsumer;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.cosmetics.CosmeticsModule;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.HiddenPlayersManager;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.player.PlayerSkinComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerUtil
/*     */ {
/*     */   public static void forEachPlayerThatCanSeeEntity(@Nonnull Ref<EntityStore> ref, @Nonnull TriConsumer<Ref<EntityStore>, PlayerRef, ComponentAccessor<EntityStore>> consumer, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  35 */     Store<EntityStore> store = ((EntityStore)componentAccessor.getExternalData()).getStore();
/*  36 */     ComponentType<EntityStore, PlayerRef> playerRefComponentType = PlayerRef.getComponentType();
/*     */ 
/*     */     
/*  39 */     store.forEachChunk((Query)playerRefComponentType, (archetypeChunk, commandBuffer) -> {
/*     */           for (int index = 0; index < archetypeChunk.size(); index++) {
/*     */             EntityTrackerSystems.EntityViewer entityViewerComponent = (EntityTrackerSystems.EntityViewer)archetypeChunk.getComponent(index, EntityTrackerSystems.EntityViewer.getComponentType());
/*     */             if (entityViewerComponent != null && entityViewerComponent.visible.contains(ref)) {
/*     */               Ref<EntityStore> targetPlayerRef = archetypeChunk.getReferenceTo(index);
/*     */               PlayerRef targetPlayerRefComponent = (PlayerRef)archetypeChunk.getComponent(index, playerRefComponentType);
/*     */               assert targetPlayerRefComponent != null;
/*     */               consumer.accept(targetPlayerRef, targetPlayerRefComponent, commandBuffer);
/*     */             } 
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void forEachPlayerThatCanSeeEntity(@Nonnull Ref<EntityStore> ref, @Nonnull TriConsumer<Ref<EntityStore>, PlayerRef, ComponentAccessor<EntityStore>> consumer, @Nullable Ref<EntityStore> ignoredPlayerRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  67 */     Store<EntityStore> store = ((EntityStore)componentAccessor.getExternalData()).getStore();
/*  68 */     ComponentType<EntityStore, PlayerRef> playerRefComponentType = PlayerRef.getComponentType();
/*     */ 
/*     */     
/*  71 */     store.forEachChunk((Query)playerRefComponentType, (archetypeChunk, commandBuffer) -> {
/*     */           for (int index = 0; index < archetypeChunk.size(); index++) {
/*     */             EntityTrackerSystems.EntityViewer entityViewerComponent = (EntityTrackerSystems.EntityViewer)archetypeChunk.getComponent(index, EntityTrackerSystems.EntityViewer.getComponentType());
/*     */             if (entityViewerComponent != null && entityViewerComponent.visible.contains(ref)) {
/*     */               Ref<EntityStore> targetRef = archetypeChunk.getReferenceTo(index);
/*     */               if (!targetRef.equals(ignoredPlayerRef)) {
/*     */                 PlayerRef targetPlayerRefComponent = (PlayerRef)archetypeChunk.getComponent(index, playerRefComponentType);
/*     */                 assert targetPlayerRefComponent != null;
/*     */                 consumer.accept(targetRef, targetPlayerRefComponent, commandBuffer);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void broadcastMessageToPlayers(@Nullable UUID sourcePlayerUuid, @Nonnull Message message, @Nonnull Store<EntityStore> store) {
/*  97 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/*  99 */     for (PlayerRef targetPlayerRef : world.getPlayerRefs()) {
/* 100 */       HiddenPlayersManager targetHiddenPlayersManager = targetPlayerRef.getHiddenPlayersManager();
/* 101 */       if (sourcePlayerUuid == null || !targetHiddenPlayersManager.isPlayerHidden(sourcePlayerUuid)) {
/* 102 */         targetPlayerRef.sendMessage(message);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void broadcastPacketToPlayers(@Nonnull ComponentAccessor<EntityStore> componentAccessor, @Nonnull Packet packet) {
/* 114 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*     */     
/* 116 */     for (PlayerRef targetPlayerRef : world.getPlayerRefs()) {
/* 117 */       targetPlayerRef.getPacketHandler().write(packet);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void broadcastPacketToPlayersNoCache(@Nonnull ComponentAccessor<EntityStore> componentAccessor, @Nonnull Packet packet) {
/* 129 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*     */     
/* 131 */     for (PlayerRef targetPlayerRef : world.getPlayerRefs()) {
/* 132 */       targetPlayerRef.getPacketHandler().writeNoCache(packet);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void broadcastPacketToPlayers(@Nonnull ComponentAccessor<EntityStore> componentAccessor, @Nonnull Packet... packets) {
/* 143 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*     */     
/* 145 */     for (PlayerRef targetPlayerRef : world.getPlayerRefs()) {
/* 146 */       targetPlayerRef.getPacketHandler().write(packets);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   public static void resetPlayerModel(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 158 */     PlayerSkinComponent playerSkinComponent = (PlayerSkinComponent)componentAccessor.getComponent(ref, PlayerSkinComponent.getComponentType());
/* 159 */     if (playerSkinComponent == null) {
/*     */       return;
/*     */     }
/*     */     
/* 163 */     playerSkinComponent.setNetworkOutdated();
/*     */     
/* 165 */     Model newModel = CosmeticsModule.get().createModel(playerSkinComponent.getPlayerSkin());
/* 166 */     if (newModel != null)
/* 167 */       componentAccessor.putComponent(ref, ModelComponent.getComponentType(), (Component)new ModelComponent(newModel)); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\PlayerUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */