/*     */ package com.hypixel.hytale.server.worldgen.cave;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.IHeightThresholdInterpreter;
/*     */ import com.hypixel.hytale.procedurallib.logic.point.IPointGenerator;
/*     */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IFloatRange;
/*     */ import com.hypixel.hytale.server.worldgen.util.BlockFluidEntry;
/*     */ import com.hypixel.hytale.server.worldgen.util.condition.BlockMaskCondition;
/*     */ import com.hypixel.hytale.server.worldgen.util.condition.flag.Int2FlagsCondition;
/*     */ import java.util.Random;
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
/*     */ public class CaveType
/*     */ {
/*     */   protected final String name;
/*     */   protected final CaveNodeType entryNodeType;
/*     */   protected final IFloatRange yaw;
/*     */   protected final IFloatRange pitch;
/*     */   protected final IFloatRange depth;
/*     */   protected final IHeightThresholdInterpreter heightFactors;
/*     */   protected final IPointGenerator pointGenerator;
/*     */   protected final Int2FlagsCondition biomeMask;
/*     */   protected final BlockMaskCondition blockMask;
/*     */   protected final ICoordinateCondition mapCondition;
/*     */   protected final ICoordinateCondition heightCondition;
/*     */   protected final IDoubleRange fixedEntryHeight;
/*     */   protected final NoiseProperty fixedEntryHeightNoise;
/*     */   protected final FluidLevel fluidLevel;
/*     */   protected final int environment;
/*     */   protected final boolean surfaceLimited;
/*     */   protected final boolean submerge;
/*     */   protected final double maximumSize;
/*     */   protected final int hashCode;
/*     */   
/*     */   public CaveType(String name, CaveNodeType entryNodeType, IFloatRange yaw, IFloatRange pitch, IFloatRange depth, IHeightThresholdInterpreter heightFactors, IPointGenerator pointGenerator, Int2FlagsCondition biomeMask, BlockMaskCondition blockMask, ICoordinateCondition mapCondition, ICoordinateCondition heightCondition, IDoubleRange fixedEntryHeight, NoiseProperty fixedEntryHeightNoise, FluidLevel fluidLevel, int environment, boolean surfaceLimited, boolean submerge, double maximumSize) {
/*  63 */     this.name = name;
/*  64 */     this.entryNodeType = entryNodeType;
/*  65 */     this.yaw = yaw;
/*  66 */     this.pitch = pitch;
/*  67 */     this.depth = depth;
/*  68 */     this.heightFactors = heightFactors;
/*  69 */     this.pointGenerator = pointGenerator;
/*  70 */     this.biomeMask = biomeMask;
/*  71 */     this.blockMask = blockMask;
/*  72 */     this.mapCondition = mapCondition;
/*  73 */     this.heightCondition = heightCondition;
/*  74 */     this.fixedEntryHeight = fixedEntryHeight;
/*  75 */     this.fixedEntryHeightNoise = fixedEntryHeightNoise;
/*  76 */     this.fluidLevel = fluidLevel;
/*  77 */     this.environment = environment;
/*  78 */     this.surfaceLimited = surfaceLimited;
/*  79 */     this.submerge = submerge;
/*  80 */     this.maximumSize = maximumSize;
/*  81 */     this.hashCode = _hashCode();
/*     */   }
/*     */   
/*     */   public String getName() {
/*  85 */     return this.name;
/*     */   }
/*     */   
/*     */   public CaveNodeType getEntryNode() {
/*  89 */     return this.entryNodeType;
/*     */   }
/*     */   public int getModifiedStartHeight(int seed, int x, int y, int z, Random random) {
/*     */     double val;
/*  93 */     if (this.fixedEntryHeight == null) return y;
/*     */     
/*  95 */     if (this.fixedEntryHeightNoise != null) {
/*  96 */       val = this.fixedEntryHeight.getValue(this.fixedEntryHeightNoise.get(seed, x, z));
/*     */     } else {
/*  98 */       val = this.fixedEntryHeight.getValue(random);
/*     */     } 
/* 100 */     return MathUtil.floor(val);
/*     */   }
/*     */   
/*     */   public float getStartPitch(Random random) {
/* 104 */     return this.pitch.getValue(random);
/*     */   }
/*     */   
/*     */   public float getStartYaw(Random random) {
/* 108 */     return this.yaw.getValue(random);
/*     */   }
/*     */   
/*     */   public int getStartDepth(Random random) {
/* 112 */     return MathUtil.floor(this.depth.getValue(random));
/*     */   }
/*     */   
/*     */   public float getHeightRadiusFactor(int seed, double x, double z, int y) {
/* 116 */     return this.heightFactors.getThreshold(seed, x, z, y);
/*     */   }
/*     */   
/*     */   public ICoordinateCondition getHeightCondition() {
/* 120 */     return this.heightCondition;
/*     */   }
/*     */   
/*     */   public IPointGenerator getEntryPointGenerator() {
/* 124 */     return this.pointGenerator;
/*     */   }
/*     */   
/*     */   public Int2FlagsCondition getBiomeMask() {
/* 128 */     return this.biomeMask;
/*     */   }
/*     */   
/*     */   public BlockMaskCondition getBlockMask() {
/* 132 */     return this.blockMask;
/*     */   }
/*     */   
/*     */   public FluidLevel getFluidLevel() {
/* 136 */     return this.fluidLevel;
/*     */   }
/*     */   
/*     */   public int getEnvironment() {
/* 140 */     return this.environment;
/*     */   }
/*     */   
/*     */   public boolean isSurfaceLimited() {
/* 144 */     return this.surfaceLimited;
/*     */   }
/*     */   
/*     */   public boolean isSubmerge() {
/* 148 */     return this.submerge;
/*     */   }
/*     */   
/*     */   public boolean isEntryThreshold(int seed, int x, int z) {
/* 152 */     return this.mapCondition.eval(seed, x, z);
/*     */   }
/*     */   
/*     */   public boolean isHeightThreshold(int seed, int x, int y, int z) {
/* 156 */     return this.heightCondition.eval(seed, x, y, z);
/*     */   }
/*     */   
/*     */   public double getMaximumSize() {
/* 160 */     return this.maximumSize;
/*     */   }
/*     */   
/*     */   public static class FluidLevel
/*     */   {
/* 165 */     public static final FluidLevel EMPTY = new FluidLevel(new BlockFluidEntry(0, 0, 0), -1);
/*     */     
/*     */     private final BlockFluidEntry blockEntry;
/*     */     private final int height;
/*     */     
/*     */     public FluidLevel(BlockFluidEntry blockEntry, int height) {
/* 171 */       this.blockEntry = blockEntry;
/* 172 */       this.height = height;
/*     */     }
/*     */     
/*     */     public BlockFluidEntry getBlockEntry() {
/* 176 */       return this.blockEntry;
/*     */     }
/*     */     
/*     */     public int getHeight() {
/* 180 */       return this.height;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int _hashCode() {
/* 187 */     int result = (this.name != null) ? this.name.hashCode() : 0;
/* 188 */     result = 31 * result + ((this.entryNodeType != null) ? this.entryNodeType.hashCode() : 0);
/* 189 */     result = 31 * result + ((this.yaw != null) ? this.yaw.hashCode() : 0);
/* 190 */     result = 31 * result + ((this.pitch != null) ? this.pitch.hashCode() : 0);
/* 191 */     result = 31 * result + ((this.depth != null) ? this.depth.hashCode() : 0);
/* 192 */     result = 31 * result + ((this.heightFactors != null) ? this.heightFactors.hashCode() : 0);
/* 193 */     result = 31 * result + ((this.pointGenerator != null) ? this.pointGenerator.hashCode() : 0);
/* 194 */     result = 31 * result + ((this.blockMask != null) ? this.blockMask.hashCode() : 0);
/* 195 */     result = 31 * result + ((this.mapCondition != null) ? this.mapCondition.hashCode() : 0);
/* 196 */     result = 31 * result + ((this.fixedEntryHeight != null) ? this.fixedEntryHeight.hashCode() : 0);
/* 197 */     result = 31 * result + ((this.fluidLevel != null) ? this.fluidLevel.hashCode() : 0);
/* 198 */     result = 31 * result + (this.surfaceLimited ? 1 : 0);
/* 199 */     result = 31 * result + (this.submerge ? 1 : 0);
/* 200 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 205 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 211 */     return "CaveType{name='" + this.name + "', entryNodeType=" + String.valueOf(this.entryNodeType) + ", yaw=" + String.valueOf(this.yaw) + ", pitch=" + String.valueOf(this.pitch) + ", depth=" + String.valueOf(this.depth) + ", heightFactors=" + String.valueOf(this.heightFactors) + ", pointGenerator=" + String.valueOf(this.pointGenerator) + ", placementConfiguration=" + String.valueOf(this.blockMask) + ", mapCondition=" + String.valueOf(this.mapCondition) + ", heightCondition=" + String.valueOf(this.heightCondition) + ", fixedEntryHeight=" + String.valueOf(this.fixedEntryHeight) + ", fluidLevel=" + String.valueOf(this.fluidLevel) + ", environment=" + this.environment + ", surfaceLimited=" + this.surfaceLimited + ", submerge=" + this.submerge + ", maximumSize=" + this.maximumSize + ", hashCode=" + this.hashCode + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\CaveType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */