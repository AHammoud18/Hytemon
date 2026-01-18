/*     */ package com.hypixel.hytale.server.worldgen.container;
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
/*     */ public class CoverContainerEntry
/*     */ {
/*     */   protected final IWeightedMap<CoverContainerEntryPart> entries;
/*     */   protected final ICoordinateCondition mapCondition;
/*     */   protected final ICoordinateRndCondition heightCondition;
/*     */   protected final IBlockFluidCondition parentCondition;
/*     */   protected final double coverDensity;
/*     */   protected final boolean onWater;
/*     */   
/*     */   public CoverContainerEntry(IWeightedMap<CoverContainerEntryPart> entries, ICoordinateCondition mapCondition, ICoordinateRndCondition heightCondition, IBlockFluidCondition parentCondition, double coverDensity, boolean onWater) {
/*  43 */     this.entries = entries;
/*  44 */     this.mapCondition = mapCondition;
/*  45 */     this.heightCondition = heightCondition;
/*  46 */     this.parentCondition = parentCondition;
/*  47 */     this.coverDensity = coverDensity;
/*  48 */     this.onWater = onWater;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public CoverContainerEntryPart get(Random random) {
/*  53 */     return (CoverContainerEntryPart)this.entries.get(random);
/*     */   }
/*     */   
/*     */   public IBlockFluidCondition getParentCondition() {
/*  57 */     return this.parentCondition;
/*     */   }
/*     */   
/*     */   public ICoordinateCondition getMapCondition() {
/*  61 */     return this.mapCondition;
/*     */   }
/*     */   
/*     */   public double getCoverDensity() {
/*  65 */     return this.coverDensity;
/*     */   }
/*     */   
/*     */   public ICoordinateRndCondition getHeightCondition() {
/*  69 */     return this.heightCondition;
/*     */   }
/*     */   
/*     */   public boolean isOnWater() {
/*  73 */     return this.onWater;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  79 */     return "CoverContainerEntry{entries=" + String.valueOf(this.entries) + ", mapCondition=" + String.valueOf(this.mapCondition) + ", heightCondition=" + String.valueOf(this.heightCondition) + ", parentCondition=" + String.valueOf(this.parentCondition) + ", coverDensity=" + this.coverDensity + ", onWater=" + this.onWater + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class CoverContainerEntryPart
/*     */   {
/*  90 */     public static final CoverContainerEntryPart[] EMPTY_ARRAY = new CoverContainerEntryPart[0];
/*     */     
/*     */     protected final BlockFluidEntry entry;
/*     */     protected final int offset;
/*     */     
/*     */     public CoverContainerEntryPart(BlockFluidEntry entry, int offset) {
/*  96 */       this.entry = entry;
/*  97 */       this.offset = offset;
/*     */     }
/*     */     
/*     */     public BlockFluidEntry getEntry() {
/* 101 */       return this.entry;
/*     */     }
/*     */     
/*     */     public int getOffset() {
/* 105 */       return this.offset;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 111 */       return "CoverContainerEntryPart{entry=" + String.valueOf(this.entry) + ", offset=" + this.offset + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\container\CoverContainer$CoverContainerEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */