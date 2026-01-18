/*     */ package com.hypixel.hytale.builtin.buildertools.utils;
/*     */ 
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockPattern;
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
/*     */ public final class Material
/*     */ {
/*  26 */   public static final Material EMPTY = new Material(0, 0, (byte)0, 0);
/*     */   
/*     */   private final int blockId;
/*     */   private final int fluidId;
/*     */   private final byte fluidLevel;
/*     */   private final int rotation;
/*     */   
/*     */   private Material(int blockId, int fluidId, byte fluidLevel, int rotation) {
/*  34 */     this.blockId = blockId;
/*  35 */     this.fluidId = fluidId;
/*  36 */     this.fluidLevel = fluidLevel;
/*  37 */     this.rotation = rotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Material block(int blockId) {
/*  48 */     return block(blockId, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Material block(int blockId, int rotation) {
/*  60 */     if (blockId == 0) {
/*  61 */       return EMPTY;
/*     */     }
/*  63 */     return new Material(blockId, 0, (byte)0, rotation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Material fluid(int fluidId, byte fluidLevel) {
/*  75 */     if (fluidId == 0) {
/*  76 */       return EMPTY;
/*     */     }
/*  78 */     return new Material(0, fluidId, fluidLevel, 0);
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
/*     */   @Nullable
/*     */   public static Material fromKey(@Nonnull String key) {
/*  95 */     if (key.equalsIgnoreCase("empty")) {
/*  96 */       return EMPTY;
/*     */     }
/*     */ 
/*     */     
/* 100 */     BlockPattern.BlockEntry blockEntry = BlockPattern.tryParseBlockTypeKey(key);
/* 101 */     if (blockEntry != null) {
/*     */       
/* 103 */       FluidPatternHelper.FluidInfo fluidInfo1 = FluidPatternHelper.getFluidInfo(blockEntry.blockTypeKey());
/* 104 */       if (fluidInfo1 != null) {
/* 105 */         return fluid(fluidInfo1.fluidId(), fluidInfo1.fluidLevel());
/*     */       }
/*     */ 
/*     */       
/* 109 */       int i = BlockType.getAssetMap().getIndex(blockEntry.blockTypeKey());
/* 110 */       if (i != Integer.MIN_VALUE) {
/* 111 */         return block(i, blockEntry.rotation());
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 116 */     FluidPatternHelper.FluidInfo fluidInfo = FluidPatternHelper.getFluidInfo(key);
/* 117 */     if (fluidInfo != null) {
/* 118 */       return fluid(fluidInfo.fluidId(), fluidInfo.fluidLevel());
/*     */     }
/*     */     
/* 121 */     int blockId = BlockType.getAssetMap().getIndex(key);
/* 122 */     if (blockId != Integer.MIN_VALUE) {
/* 123 */       return block(blockId);
/*     */     }
/*     */     
/* 126 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFluid() {
/* 133 */     return (this.fluidId != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBlock() {
/* 140 */     return (this.blockId != 0 && this.fluidId == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 147 */     return (this.blockId == 0 && this.fluidId == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBlockId() {
/* 154 */     return this.blockId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFluidId() {
/* 161 */     return this.fluidId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getFluidLevel() {
/* 168 */     return this.fluidLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRotation() {
/* 175 */     return this.rotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasRotation() {
/* 182 */     return (this.rotation != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 187 */     if (isEmpty())
/* 188 */       return "Material[empty]"; 
/* 189 */     if (isFluid()) {
/* 190 */       Fluid fluid = (Fluid)Fluid.getAssetMap().getAsset(this.fluidId);
/* 191 */       return "Material[fluid=" + String.valueOf((fluid != null) ? fluid.getId() : Integer.valueOf(this.fluidId)) + ", level=" + this.fluidLevel + "]";
/*     */     } 
/* 193 */     BlockType block = (BlockType)BlockType.getAssetMap().getAsset(this.blockId);
/* 194 */     String rotStr = hasRotation() ? (", rotation=" + String.valueOf(RotationTuple.get(this.rotation))) : "";
/* 195 */     return "Material[block=" + String.valueOf((block != null) ? block.getId() : Integer.valueOf(this.blockId)) + rotStr + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     Material other;
/* 201 */     if (this == obj) return true; 
/* 202 */     if (obj instanceof Material) { other = (Material)obj; } else { return false; }
/* 203 */      return (this.blockId == other.blockId && this.fluidId == other.fluidId && this.fluidLevel == other.fluidLevel && this.rotation == other.rotation);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 208 */     return 31 * (31 * (31 * this.blockId + this.fluidId) + this.fluidLevel) + this.rotation;
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
/*     */   @Nonnull
/*     */   public static Material fromPattern(@Nonnull BlockPattern pattern, @Nonnull Random random) {
/* 224 */     BlockPattern.BlockEntry blockEntry = pattern.nextBlockTypeKey(random);
/* 225 */     if (blockEntry != null) {
/* 226 */       FluidPatternHelper.FluidInfo fluidInfo = FluidPatternHelper.getFluidInfo(blockEntry.blockTypeKey());
/* 227 */       if (fluidInfo != null) {
/* 228 */         return fluid(fluidInfo.fluidId(), fluidInfo.fluidLevel());
/*     */       }
/*     */       
/* 231 */       int blockId = BlockType.getAssetMap().getIndex(blockEntry.blockTypeKey());
/* 232 */       if (blockId != Integer.MIN_VALUE) {
/* 233 */         return block(blockId, blockEntry.rotation());
/*     */       }
/*     */     } 
/*     */     
/* 237 */     return block(pattern.nextBlock(random));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertool\\utils\Material.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */