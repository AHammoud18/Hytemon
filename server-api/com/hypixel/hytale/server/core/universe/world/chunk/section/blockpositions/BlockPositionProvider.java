/*     */ package com.hypixel.hytale.server.core.universe.world.chunk.section.blockpositions;
/*     */ 
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.function.consumer.IntObjectConsumer;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.modules.LegacyModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import java.util.BitSet;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.BiPredicate;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class BlockPositionProvider implements Component<ChunkStore> {
/*     */   private final BitSet searchedBlockSets;
/*     */   @Nullable
/*     */   private final Int2ObjectMap<List<IBlockPositionData>> blockData;
/*     */   private final short lightChangeCounter;
/*     */   
/*     */   public static ComponentType<ChunkStore, BlockPositionProvider> getComponentType() {
/*  31 */     return LegacyModule.get().getBlockPositionProviderComponentType();
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
/*     */   public BlockPositionProvider(@Nonnull BitSet blockSets, @Nullable Int2ObjectOpenHashMap<List<IBlockPositionData>> data, short lightChangeCounter) {
/*  44 */     this.searchedBlockSets = (BitSet)blockSets.clone();
/*  45 */     this.lightChangeCounter = lightChangeCounter;
/*  46 */     if (data != null) {
/*  47 */       this.blockData = Int2ObjectMaps.unmodifiable((Int2ObjectMap)data);
/*     */     } else {
/*  49 */       this.blockData = null;
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
/*     */   public boolean isStale(int currentBlockSet, @Nonnull BlockSection section) {
/*  61 */     return (this.lightChangeCounter != section.getLocalChangeCounter() || !this.searchedBlockSets.get(currentBlockSet));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> void findBlocks(@Nonnull List<IBlockPositionData> resultList, int blockSet, double range, double yRange, @Nonnull Ref<EntityStore> ref, @Nullable BiPredicate<IBlockPositionData, T> filter, T obj, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  84 */     if (this.blockData == null)
/*     */       return; 
/*  86 */     List<IBlockPositionData> data = (List<IBlockPositionData>)this.blockData.getOrDefault(blockSet, null);
/*  87 */     if (data == null)
/*     */       return; 
/*  89 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/*  90 */     assert transformComponent != null;
/*     */     
/*  92 */     Vector3d pos = transformComponent.getPosition();
/*     */     
/*  94 */     double range2 = range * range;
/*  95 */     for (int i = 0; i < data.size(); i++) {
/*  96 */       IBlockPositionData entry = data.get(i);
/*  97 */       double entryY = entry.getYCentre();
/*     */       
/*  99 */       if (Math.abs(pos.y - entryY) <= yRange && pos.distanceSquaredTo(entry.getXCentre(), entryY, entry.getZCentre()) <= range2 && (
/* 100 */         filter == null || !filter.test(entry, obj))) {
/* 101 */         resultList.add(entry);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BitSet getSearchedBlockSets() {
/* 112 */     return (BitSet)this.searchedBlockSets.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void forEachBlockSet(@Nonnull IntObjectConsumer<List<IBlockPositionData>> listConsumer) {
/* 123 */     if (this.blockData == null)
/* 124 */       return;  Objects.requireNonNull(listConsumer); this.blockData.forEach(listConsumer::accept);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<ChunkStore> clone() {
/* 131 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\chunk\section\blockpositions\BlockPositionProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */