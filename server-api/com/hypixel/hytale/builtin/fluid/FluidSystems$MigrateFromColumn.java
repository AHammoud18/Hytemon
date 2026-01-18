/*     */ package com.hypixel.hytale.builtin.fluid;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.server.core.modules.LegacyModule;
/*     */ import com.hypixel.hytale.server.core.modules.migrations.ChunkColumnMigrationSystem;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkColumn;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.FluidSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
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
/*     */ public class MigrateFromColumn
/*     */   extends ChunkColumnMigrationSystem
/*     */ {
/*     */   @Nonnull
/* 101 */   private final Query<ChunkStore> QUERY = (Query<ChunkStore>)Query.and(new Query[] { (Query)ChunkColumn.getComponentType(), (Query)BlockChunk.getComponentType() });
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 107 */   private final Set<Dependency<ChunkStore>> DEPENDENCIES = (Set)Set.of(new SystemDependency(Order.BEFORE, LegacyModule.MigrateLegacySections.class));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityAdd(@Nonnull Holder<ChunkStore> holder, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store) {
/* 113 */     ChunkColumn chunkColumnComponent = (ChunkColumn)holder.getComponent(ChunkColumn.getComponentType());
/* 114 */     assert chunkColumnComponent != null;
/*     */     
/* 116 */     BlockChunk blockChunkComponent = (BlockChunk)holder.getComponent(BlockChunk.getComponentType());
/* 117 */     assert blockChunkComponent != null;
/*     */     
/* 119 */     Holder[] arrayOfHolder = chunkColumnComponent.getSectionHolders();
/* 120 */     BlockSection[] legacySections = blockChunkComponent.getMigratedSections();
/*     */     
/* 122 */     if (legacySections == null)
/*     */       return; 
/* 124 */     for (int i = 0; i < arrayOfHolder.length; i++) {
/* 125 */       Holder<ChunkStore> section = arrayOfHolder[i];
/* 126 */       BlockSection paletteSection = legacySections[i];
/* 127 */       if (section != null && paletteSection != null) {
/*     */         
/* 129 */         FluidSection fluid = paletteSection.takeMigratedFluid();
/* 130 */         if (fluid != null) {
/* 131 */           section.putComponent(FluidSection.getComponentType(), (Component)fluid);
/*     */           
/* 133 */           blockChunkComponent.markNeedsSaving();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityRemoved(@Nonnull Holder<ChunkStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store) {}
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<ChunkStore> getQuery() {
/* 145 */     return this.QUERY;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<ChunkStore>> getDependencies() {
/* 151 */     return this.DEPENDENCIES;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\fluid\FluidSystems$MigrateFromColumn.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */