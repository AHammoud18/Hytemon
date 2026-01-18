/*     */ package com.hypixel.hytale.server.worldgen.prefab;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.server.worldgen.util.BlockFluidEntry;
/*     */ import com.hypixel.hytale.server.worldgen.util.ResolvedBlockArray;
/*     */ import com.hypixel.hytale.server.worldgen.util.condition.BlockMaskCondition;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockPlacementMask
/*     */   implements BlockMaskCondition
/*     */ {
/*  19 */   public static final IMask DEFAULT_MASK = new DefaultMask();
/*     */ 
/*     */   
/*     */   private IMask defaultMask;
/*     */   
/*     */   private Long2ObjectMap<Mask> specificMasks;
/*     */ 
/*     */   
/*     */   public void set(IMask defaultMask, Long2ObjectMap<Mask> specificMasks) {
/*  28 */     this.defaultMask = defaultMask;
/*  29 */     this.specificMasks = specificMasks;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean eval(int currentBlock, int currentFluid, @Nonnull BlockFluidEntry entry) {
/*  34 */     IMask mask = (this.specificMasks == null) ? null : (IMask)this.specificMasks.get(MathUtil.packLong(entry.blockId(), entry.fluidId()));
/*  35 */     if (mask == null) {
/*  36 */       mask = this.defaultMask;
/*     */     }
/*  38 */     return mask.shouldReplace(currentBlock, currentFluid);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/*  43 */     if (this == o) return true; 
/*  44 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/*  46 */     BlockPlacementMask that = (BlockPlacementMask)o;
/*     */     
/*  48 */     if (!this.defaultMask.equals(that.defaultMask)) return false; 
/*  49 */     return Objects.equals(this.specificMasks, that.specificMasks);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  54 */     int result = this.defaultMask.hashCode();
/*  55 */     result = 31 * result + ((this.specificMasks != null) ? this.specificMasks.hashCode() : 0);
/*  56 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  62 */     return "BlockPlacementMask{defaultMask=" + String.valueOf(this.defaultMask) + ", specificMasks=" + String.valueOf(this.specificMasks) + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Mask
/*     */     implements IMask
/*     */   {
/*     */     private final BlockPlacementMask.IEntry[] entries;
/*     */ 
/*     */ 
/*     */     
/*     */     public Mask(BlockPlacementMask.IEntry[] entries) {
/*  76 */       this.entries = entries;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldReplace(int current, int fluid) {
/*  81 */       for (BlockPlacementMask.IEntry entry : this.entries) {
/*  82 */         if (entry.shouldHandle(current, fluid)) {
/*  83 */           return entry.shouldReplace();
/*     */         }
/*     */       } 
/*  86 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(@Nullable Object o) {
/*  91 */       if (this == o) return true; 
/*  92 */       if (o == null || getClass() != o.getClass()) return false;
/*     */       
/*  94 */       Mask mask = (Mask)o;
/*     */       
/*  96 */       return Arrays.equals((Object[])this.entries, (Object[])mask.entries);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 101 */       return Arrays.hashCode((Object[])this.entries);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 108 */       return "Mask{entries=" + Arrays.toString((Object[])this.entries) + "}";
/*     */     }
/*     */   }
/*     */   
/*     */   public static class DefaultMask
/*     */     implements IMask
/*     */   {
/*     */     public boolean shouldReplace(int block, int fluid) {
/* 116 */       return (block == 0 && fluid == 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 121 */       return 137635105;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 126 */       return o instanceof DefaultMask;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 132 */       return "DefaultMask{}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class WildcardEntry
/*     */     implements IEntry
/*     */   {
/*     */     private final boolean replace;
/*     */ 
/*     */     
/*     */     public WildcardEntry(boolean replace) {
/* 145 */       this.replace = replace;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldHandle(int block, int fluid) {
/* 150 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldReplace() {
/* 155 */       return this.replace;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(@Nullable Object o) {
/* 160 */       if (this == o) return true; 
/* 161 */       if (o == null || getClass() != o.getClass()) return false;
/*     */       
/* 163 */       WildcardEntry that = (WildcardEntry)o;
/*     */       
/* 165 */       return (this.replace == that.replace);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 170 */       return this.replace ? 1 : 0;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 176 */       return "WildcardEntry{replace=" + this.replace + "}";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class BlockArrayEntry
/*     */     implements IEntry
/*     */   {
/*     */     private ResolvedBlockArray blocks;
/*     */     
/*     */     private boolean replace;
/*     */ 
/*     */     
/*     */     public void set(ResolvedBlockArray blocks, boolean replace) {
/* 190 */       this.blocks = blocks;
/* 191 */       this.replace = replace;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldHandle(int current, int fluid) {
/* 196 */       return this.blocks.contains(current, fluid);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldReplace() {
/* 201 */       return this.replace;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(@Nullable Object o) {
/* 206 */       if (this == o) return true; 
/* 207 */       if (o == null || getClass() != o.getClass()) return false;
/*     */       
/* 209 */       BlockArrayEntry that = (BlockArrayEntry)o;
/*     */       
/* 211 */       if (this.replace != that.replace) return false; 
/* 212 */       return this.blocks.equals(that.blocks);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 217 */       int result = this.blocks.hashCode();
/* 218 */       result = 31 * result + (this.replace ? 1 : 0);
/* 219 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 225 */       return "BlockArrayEntry{blocks=" + String.valueOf(this.blocks) + ", replace=" + this.replace + "}";
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface IMask {
/*     */     boolean shouldReplace(int param1Int1, int param1Int2);
/*     */   }
/*     */   
/*     */   public static interface IEntry {
/*     */     boolean shouldHandle(int param1Int1, int param1Int2);
/*     */     
/*     */     boolean shouldReplace();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\prefab\BlockPlacementMask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */