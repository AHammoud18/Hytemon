/*     */ package com.hypixel.hytale.server.worldgen.util;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleCoordinateSupplier2d;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*     */ import java.util.Objects;
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
/*     */ public class Entry
/*     */ {
/*     */   protected final String blockName;
/*     */   protected final BlockFluidEntry blockEntry;
/*     */   protected final IDoubleRange repetitions;
/*     */   @Nonnull
/*     */   protected final NoiseProperty noise;
/*     */   @Nonnull
/*     */   protected final IDoubleCoordinateSupplier2d noiseSupplier;
/*     */   
/*     */   public Entry(String blockName, BlockFluidEntry blockEntry, IDoubleRange repetitions, @Nonnull NoiseProperty noise) {
/*  85 */     this.blockName = blockName;
/*  86 */     this.blockEntry = blockEntry;
/*  87 */     this.repetitions = repetitions;
/*  88 */     this.noise = noise;
/*  89 */     Objects.requireNonNull(noise); this.noiseSupplier = noise::get;
/*     */   }
/*     */   
/*     */   public String getBlockName() {
/*  93 */     return this.blockName;
/*     */   }
/*     */   
/*     */   public BlockFluidEntry getBlockEntry() {
/*  97 */     return this.blockEntry;
/*     */   }
/*     */   
/*     */   public int getRepetitions(int seed, double x, double z) {
/* 101 */     return MathUtil.floor(this.repetitions.getValue(seed, x, z, this.noiseSupplier));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 106 */     if (this == o) return true; 
/* 107 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 109 */     Entry entry = (Entry)o;
/*     */     
/* 111 */     if (this.blockEntry != entry.blockEntry) return false; 
/* 112 */     if (!this.blockName.equals(entry.blockName)) return false; 
/* 113 */     if (!this.repetitions.equals(entry.repetitions)) return false; 
/* 114 */     if (!this.noise.equals(entry.noise)) return false; 
/* 115 */     return this.noiseSupplier.equals(entry.noiseSupplier);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 120 */     int result = this.blockName.hashCode();
/* 121 */     result = 31 * result + this.blockEntry.hashCode();
/* 122 */     result = 31 * result + this.repetitions.hashCode();
/* 123 */     result = 31 * result + this.noise.hashCode();
/* 124 */     result = 31 * result + this.noiseSupplier.hashCode();
/* 125 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 131 */     return "Entry{blockName='" + this.blockName + "', blockEntry=" + String.valueOf(this.blockEntry) + ", repetitions=" + String.valueOf(this.repetitions) + ", noise=" + String.valueOf(this.noise) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\NoiseBlockArray$Entry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */