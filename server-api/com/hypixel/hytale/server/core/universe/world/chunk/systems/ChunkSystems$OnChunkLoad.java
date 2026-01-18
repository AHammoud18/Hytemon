/*     */ package com.hypixel.hytale.server.core.universe.world.chunk.systems;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkColumn;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.ChunkSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import java.util.Arrays;
/*     */ import java.util.Set;
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
/*     */ public class OnChunkLoad
/*     */   extends RefSystem<ChunkStore>
/*     */ {
/*  74 */   private static final Query<ChunkStore> QUERY = (Query<ChunkStore>)Query.and(new Query[] { (Query)ChunkColumn.getComponentType(), (Query)WorldChunk.getComponentType() });
/*  75 */   private static final Set<Dependency<ChunkStore>> DEPENDENCIES = (Set)Set.of(new SystemDependency(Order.AFTER, ChunkSystems.OnNewChunk.class));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityAdded(@Nonnull Ref<ChunkStore> ref, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/*  82 */     ChunkColumn chunk = (ChunkColumn)commandBuffer.getComponent(ref, ChunkColumn.getComponentType());
/*  83 */     assert chunk != null;
/*  84 */     WorldChunk worldChunk = (WorldChunk)commandBuffer.getComponent(ref, WorldChunk.getComponentType());
/*  85 */     assert worldChunk != null;
/*     */     
/*  87 */     Ref[] arrayOfRef = chunk.getSections();
/*  88 */     Holder[] arrayOfHolder = chunk.takeSectionHolders();
/*     */     
/*  90 */     boolean isNonTicking = commandBuffer.getArchetype(ref).contains(ChunkStore.REGISTRY.getNonTickingComponentType());
/*     */     
/*  92 */     if (arrayOfHolder != null && arrayOfHolder.length > 0 && arrayOfHolder[0] != null) {
/*  93 */       for (int j = 0; j < arrayOfHolder.length; j++) {
/*  94 */         if (isNonTicking) {
/*  95 */           arrayOfHolder[j].ensureComponent(ChunkStore.REGISTRY.getNonTickingComponentType());
/*     */         } else {
/*  97 */           arrayOfHolder[j].tryRemoveComponent(ChunkStore.REGISTRY.getNonTickingComponentType());
/*     */         } 
/*  99 */         ChunkSection section = (ChunkSection)arrayOfHolder[j].getComponent(ChunkSection.getComponentType());
/* 100 */         if (section == null) {
/* 101 */           arrayOfHolder[j].addComponent(ChunkSection.getComponentType(), (Component)new ChunkSection(ref, worldChunk.getX(), j, worldChunk.getZ()));
/*     */         } else {
/*     */           
/* 104 */           section.load(ref, worldChunk.getX(), j, worldChunk.getZ());
/*     */         } 
/* 106 */       }  commandBuffer.addEntities(arrayOfHolder, 0, arrayOfRef, 0, arrayOfRef.length, AddReason.LOAD);
/*     */     } 
/*     */ 
/*     */     
/* 110 */     for (int i = 0; i < arrayOfRef.length; i++) {
/* 111 */       if (arrayOfRef[i] == null) {
/*     */ 
/*     */         
/* 114 */         Holder<ChunkStore> newSection = ChunkStore.REGISTRY.newHolder();
/* 115 */         if (isNonTicking) {
/* 116 */           newSection.ensureComponent(ChunkStore.REGISTRY.getNonTickingComponentType());
/*     */         } else {
/* 118 */           newSection.tryRemoveComponent(ChunkStore.REGISTRY.getNonTickingComponentType());
/*     */         } 
/* 120 */         newSection.addComponent(ChunkSection.getComponentType(), (Component)new ChunkSection(ref, worldChunk.getX(), i, worldChunk.getZ()));
/* 121 */         arrayOfRef[i] = commandBuffer.addEntity(newSection, AddReason.SPAWN);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onEntityRemove(@Nonnull Ref<ChunkStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/* 127 */     ChunkColumn chunk = (ChunkColumn)commandBuffer.getComponent(ref, ChunkColumn.getComponentType());
/* 128 */     assert chunk != null;
/* 129 */     Ref[] arrayOfRef = chunk.getSections();
/*     */ 
/*     */     
/* 132 */     Holder[] arrayOfHolder = new Holder[arrayOfRef.length];
/* 133 */     for (int i = 0; i < arrayOfRef.length; i++) {
/* 134 */       Ref<ChunkStore> section = arrayOfRef[i];
/* 135 */       arrayOfHolder[i] = ChunkStore.REGISTRY.newHolder();
/* 136 */       commandBuffer.removeEntity(section, arrayOfHolder[i], reason);
/*     */     } 
/* 138 */     chunk.putSectionHolders(arrayOfHolder);
/* 139 */     Arrays.fill((Object[])arrayOfRef, (Object)null);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<ChunkStore> getQuery() {
/* 145 */     return QUERY;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<ChunkStore>> getDependencies() {
/* 151 */     return DEPENDENCIES;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\chunk\systems\ChunkSystems$OnChunkLoad.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */