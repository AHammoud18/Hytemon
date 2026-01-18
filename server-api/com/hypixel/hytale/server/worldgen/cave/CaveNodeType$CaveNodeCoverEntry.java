/*     */ package com.hypixel.hytale.server.worldgen.cave;
/*     */ 
/*     */ import com.hypixel.hytale.common.map.IWeightedMap;
/*     */ import com.hypixel.hytale.procedurallib.condition.IBlockFluidCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateRndCondition;
/*     */ import com.hypixel.hytale.server.worldgen.util.BlockFluidEntry;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class CaveNodeCoverEntry
/*     */ {
/* 228 */   public static final CaveNodeCoverEntry[] EMPTY_ARRAY = new CaveNodeCoverEntry[0];
/*     */   
/*     */   @Nonnull
/*     */   protected final IWeightedMap<Entry> entries;
/*     */   
/*     */   @Nonnull
/*     */   protected final ICoordinateRndCondition heightCondition;
/*     */   
/*     */   @Nonnull
/*     */   protected final ICoordinateCondition mapCondition;
/*     */   
/*     */   @Nonnull
/*     */   protected final ICoordinateCondition densityCondition;
/*     */   
/*     */   @Nonnull
/*     */   protected final IBlockFluidCondition parentCondition;
/*     */   
/*     */   @Nonnull
/*     */   protected final CaveNodeType.CaveNodeCoverType type;
/*     */   
/*     */   public CaveNodeCoverEntry(@Nonnull IWeightedMap<Entry> entries, @Nonnull ICoordinateRndCondition heightCondition, @Nonnull ICoordinateCondition mapCondition, @Nonnull ICoordinateCondition densityCondition, @Nonnull IBlockFluidCondition parentCondition, @Nonnull CaveNodeType.CaveNodeCoverType type) {
/* 249 */     this.entries = entries;
/* 250 */     this.heightCondition = heightCondition;
/* 251 */     this.mapCondition = mapCondition;
/* 252 */     this.densityCondition = densityCondition;
/* 253 */     this.type = type;
/* 254 */     this.parentCondition = parentCondition;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Entry get(Random random) {
/* 259 */     return (Entry)this.entries.get(random);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ICoordinateRndCondition getHeightCondition() {
/* 264 */     return this.heightCondition;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ICoordinateCondition getMapCondition() {
/* 269 */     return this.mapCondition;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ICoordinateCondition getDensityCondition() {
/* 274 */     return this.densityCondition;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public IBlockFluidCondition getParentCondition() {
/* 279 */     return this.parentCondition;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public CaveNodeType.CaveNodeCoverType getType() {
/* 284 */     return this.type;
/*     */   }
/*     */   
/*     */   public static class Entry {
/* 288 */     public static final Entry[] EMPTY_ARRAY = new Entry[0];
/*     */     
/*     */     protected final BlockFluidEntry entry;
/*     */     protected final int offset;
/*     */     
/*     */     public Entry(BlockFluidEntry entry, int offset) {
/* 294 */       this.entry = entry;
/* 295 */       this.offset = offset;
/*     */     }
/*     */     
/*     */     public int getOffset() {
/* 299 */       return this.offset;
/*     */     }
/*     */     
/*     */     public BlockFluidEntry getEntry() {
/* 303 */       return this.entry;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 309 */       return "Entry{entry=" + String.valueOf(this.entry) + ", offset=" + this.offset + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\CaveNodeType$CaveNodeCoverEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */