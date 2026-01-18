/*     */ package com.hypixel.hytale.server.worldgen.util;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleCoordinateSupplier2d;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NoiseBlockArray
/*     */ {
/*  17 */   public static final NoiseBlockArray EMPTY = new NoiseBlockArray(new Entry[0]);
/*     */   
/*     */   protected final Entry[] entries;
/*     */   
/*     */   public NoiseBlockArray(Entry[] entries) {
/*  22 */     this.entries = entries;
/*     */   }
/*     */   
/*     */   public Entry[] getEntries() {
/*  26 */     return this.entries;
/*     */   }
/*     */   
/*     */   public BlockFluidEntry getTopBlockAt(int seed, double x, double z) {
/*  30 */     for (int i = 0; i < this.entries.length; i++) {
/*  31 */       Entry entry = this.entries[i];
/*  32 */       int repetitions = entry.getRepetitions(seed, x, z);
/*  33 */       if (repetitions > 0) {
/*  34 */         return entry.blockEntry;
/*     */       }
/*     */     } 
/*  37 */     return BlockFluidEntry.EMPTY;
/*     */   }
/*     */   
/*     */   public BlockFluidEntry getBottomBlockAt(int seed, double x, double z) {
/*  41 */     for (int i = this.entries.length - 1; i >= 0; i--) {
/*  42 */       Entry entry = this.entries[i];
/*  43 */       int repetitions = entry.getRepetitions(seed, x, z);
/*  44 */       if (repetitions > 0) {
/*  45 */         return entry.blockEntry;
/*     */       }
/*     */     } 
/*  48 */     return BlockFluidEntry.EMPTY;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/*  53 */     if (this == o) return true; 
/*  54 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/*  56 */     NoiseBlockArray that = (NoiseBlockArray)o;
/*     */ 
/*     */     
/*  59 */     return Arrays.equals((Object[])this.entries, (Object[])that.entries);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  64 */     return Arrays.hashCode((Object[])this.entries);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  71 */     return "NoiseBlockArray{entries=" + Arrays.toString((Object[])this.entries) + "}";
/*     */   }
/*     */   
/*     */   public static class Entry
/*     */   {
/*     */     protected final String blockName;
/*     */     protected final BlockFluidEntry blockEntry;
/*     */     protected final IDoubleRange repetitions;
/*     */     @Nonnull
/*     */     protected final NoiseProperty noise;
/*     */     @Nonnull
/*     */     protected final IDoubleCoordinateSupplier2d noiseSupplier;
/*     */     
/*     */     public Entry(String blockName, BlockFluidEntry blockEntry, IDoubleRange repetitions, @Nonnull NoiseProperty noise) {
/*  85 */       this.blockName = blockName;
/*  86 */       this.blockEntry = blockEntry;
/*  87 */       this.repetitions = repetitions;
/*  88 */       this.noise = noise;
/*  89 */       Objects.requireNonNull(noise); this.noiseSupplier = noise::get;
/*     */     }
/*     */     
/*     */     public String getBlockName() {
/*  93 */       return this.blockName;
/*     */     }
/*     */     
/*     */     public BlockFluidEntry getBlockEntry() {
/*  97 */       return this.blockEntry;
/*     */     }
/*     */     
/*     */     public int getRepetitions(int seed, double x, double z) {
/* 101 */       return MathUtil.floor(this.repetitions.getValue(seed, x, z, this.noiseSupplier));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(@Nullable Object o) {
/* 106 */       if (this == o) return true; 
/* 107 */       if (o == null || getClass() != o.getClass()) return false;
/*     */       
/* 109 */       Entry entry = (Entry)o;
/*     */       
/* 111 */       if (this.blockEntry != entry.blockEntry) return false; 
/* 112 */       if (!this.blockName.equals(entry.blockName)) return false; 
/* 113 */       if (!this.repetitions.equals(entry.repetitions)) return false; 
/* 114 */       if (!this.noise.equals(entry.noise)) return false; 
/* 115 */       return this.noiseSupplier.equals(entry.noiseSupplier);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 120 */       int result = this.blockName.hashCode();
/* 121 */       result = 31 * result + this.blockEntry.hashCode();
/* 122 */       result = 31 * result + this.repetitions.hashCode();
/* 123 */       result = 31 * result + this.noise.hashCode();
/* 124 */       result = 31 * result + this.noiseSupplier.hashCode();
/* 125 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 131 */       return "Entry{blockName='" + this.blockName + "', blockEntry=" + String.valueOf(this.blockEntry) + ", repetitions=" + String.valueOf(this.repetitions) + ", noise=" + String.valueOf(this.noise) + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\NoiseBlockArray.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */