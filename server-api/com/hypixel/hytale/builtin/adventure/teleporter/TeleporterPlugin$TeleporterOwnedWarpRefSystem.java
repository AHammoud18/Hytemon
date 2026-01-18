/*     */ package com.hypixel.hytale.builtin.adventure.teleporter;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.teleporter.component.Teleporter;
/*     */ import com.hypixel.hytale.builtin.teleport.TeleportPlugin;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import javax.annotation.Nonnull;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class TeleporterOwnedWarpRefSystem
/*     */   extends RefSystem<ChunkStore>
/*     */ {
/*  98 */   public static final ComponentType<ChunkStore, Teleporter> COMPONENT_TYPE = Teleporter.getComponentType();
/*     */   public void onEntityAdded(@Nonnull Ref<ChunkStore> ref, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/*     */     Teleporter component;
/*     */     String ownedWarp;
/* 102 */     switch (TeleporterPlugin.null.$SwitchMap$com$hypixel$hytale$component$AddReason[reason.ordinal()]) {
/*     */ 
/*     */ 
/*     */       
/*     */       case 2:
/* 107 */         component = (Teleporter)commandBuffer.getComponent(ref, COMPONENT_TYPE);
/* 108 */         ownedWarp = component.getOwnedWarp();
/* 109 */         if (ownedWarp == null || ownedWarp.isEmpty() || 
/* 110 */           !TeleportPlugin.get().getWarps().containsKey(ownedWarp.toLowerCase()));
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityRemove(@Nonnull Ref<ChunkStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/* 120 */     if (reason == RemoveReason.REMOVE) {
/* 121 */       Teleporter component = (Teleporter)commandBuffer.getComponent(ref, COMPONENT_TYPE);
/* 122 */       String ownedWarp = component.getOwnedWarp();
/* 123 */       if (ownedWarp != null && !ownedWarp.isEmpty()) {
/* 124 */         TeleportPlugin.get().getWarps().remove(ownedWarp.toLowerCase());
/* 125 */         TeleportPlugin.get().saveWarps();
/* 126 */         component.setOwnedWarp(null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Query<ChunkStore> getQuery() {
/* 133 */     return (Query)COMPONENT_TYPE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\teleporter\TeleporterPlugin$TeleporterOwnedWarpRefSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */