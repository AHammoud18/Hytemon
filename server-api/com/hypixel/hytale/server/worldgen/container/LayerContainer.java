/*     */ package com.hypixel.hytale.server.worldgen.container;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateCondition;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleCoordinateSupplier;
/*     */ import com.hypixel.hytale.server.worldgen.util.BlockFluidEntry;
/*     */ import com.hypixel.hytale.server.worldgen.util.NoiseBlockArray;
/*     */ import java.util.Arrays;
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
/*     */ public class LayerContainer
/*     */ {
/*     */   @Nonnull
/*     */   protected final BlockFluidEntry filling;
/*     */   protected final int fillingEnvironment;
/*     */   protected final StaticLayer[] staticLayers;
/*     */   protected final DynamicLayer[] dynamicLayers;
/*     */   
/*     */   public LayerContainer(int filling, int fillingEnvironment, StaticLayer[] staticLayers, DynamicLayer[] dynamicLayers) {
/*  28 */     this.filling = new BlockFluidEntry(filling, 0, 0);
/*  29 */     this.fillingEnvironment = fillingEnvironment;
/*  30 */     this.staticLayers = staticLayers;
/*  31 */     this.dynamicLayers = dynamicLayers;
/*     */   }
/*     */   
/*     */   public BlockFluidEntry getFilling() {
/*  35 */     return this.filling;
/*     */   }
/*     */   
/*     */   public int getFillingEnvironment() {
/*  39 */     return this.fillingEnvironment;
/*     */   }
/*     */   
/*     */   public StaticLayer[] getStaticLayers() {
/*  43 */     return this.staticLayers;
/*     */   }
/*     */   
/*     */   public DynamicLayer[] getDynamicLayers() {
/*  47 */     return this.dynamicLayers;
/*     */   }
/*     */   
/*     */   public BlockFluidEntry getTopBlockAt(int seed, int x, int z) {
/*  51 */     for (DynamicLayer layer : this.dynamicLayers) {
/*  52 */       DynamicLayerEntry entry = layer.getActiveEntry(seed, x, z);
/*  53 */       if (entry != null) {
/*  54 */         return entry.blockArray.getTopBlockAt(seed, x, z);
/*     */       }
/*     */     } 
/*  57 */     return this.filling;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  63 */     return "LayerContainer{filling=" + String.valueOf(this.filling) + ", staticLayers=" + 
/*     */       
/*  65 */       Arrays.toString((Object[])this.staticLayers) + ", dynamicLayers=" + 
/*  66 */       Arrays.toString((Object[])this.dynamicLayers) + "}";
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Layer<T extends LayerEntry>
/*     */   {
/*     */     protected final T[] entries;
/*     */     
/*     */     protected final ICoordinateCondition mapCondition;
/*     */     protected final int environmentId;
/*     */     
/*     */     public Layer(T[] entries, ICoordinateCondition mapCondition, int environmentId) {
/*  78 */       this.entries = entries;
/*  79 */       this.mapCondition = mapCondition;
/*  80 */       this.environmentId = environmentId;
/*     */     }
/*     */     
/*     */     public int getEnvironmentId() {
/*  84 */       return this.environmentId;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public T getActiveEntry(int seed, int x, int z) {
/*  89 */       if (!this.mapCondition.eval(seed, x, z)) return null; 
/*  90 */       for (T entry : this.entries) {
/*  91 */         if (entry.isActive(seed, x, z)) {
/*  92 */           return entry;
/*     */         }
/*     */       } 
/*  95 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 102 */       return "Layer{entries=" + Arrays.toString((Object[])this.entries) + "}";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class StaticLayer
/*     */     extends Layer<StaticLayerEntry>
/*     */   {
/*     */     public StaticLayer(LayerContainer.StaticLayerEntry[] entries, ICoordinateCondition mapCondition, int environmentId) {
/* 111 */       super(entries, mapCondition, environmentId);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 118 */       return "StaticLayer{entries=" + Arrays.toString((Object[])this.entries) + "}";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class DynamicLayer
/*     */     extends Layer<DynamicLayerEntry>
/*     */   {
/*     */     protected final IDoubleCoordinateSupplier offset;
/*     */ 
/*     */     
/*     */     public DynamicLayer(LayerContainer.DynamicLayerEntry[] entries, ICoordinateCondition mapCondition, int environmentId, IDoubleCoordinateSupplier offset) {
/* 130 */       super(entries, mapCondition, environmentId);
/* 131 */       this.offset = offset;
/*     */     }
/*     */     
/*     */     public int getOffset(int seed, int x, int z) {
/* 135 */       return MathUtil.floor(this.offset.get(seed, x, z));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 142 */       return "DynamicLayer{entries=" + Arrays.toString((Object[])this.entries) + ", offset=" + String.valueOf(this.offset) + "}";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static abstract class LayerEntry
/*     */   {
/*     */     protected final NoiseBlockArray blockArray;
/*     */     protected final ICoordinateCondition mapCondition;
/*     */     
/*     */     public LayerEntry(NoiseBlockArray blockArray, ICoordinateCondition mapCondition) {
/* 153 */       this.blockArray = blockArray;
/* 154 */       this.mapCondition = mapCondition;
/*     */     }
/*     */     
/*     */     public boolean isActive(int seed, int x, int z) {
/* 158 */       return this.mapCondition.eval(seed, x, z);
/*     */     }
/*     */     
/*     */     public NoiseBlockArray getBlockArray() {
/* 162 */       return this.blockArray;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 168 */       return "LayerEntry{blockArray=" + String.valueOf(this.blockArray) + ", mapCondition=" + String.valueOf(this.mapCondition) + "}";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class StaticLayerEntry
/*     */     extends LayerEntry
/*     */   {
/*     */     protected final IDoubleCoordinateSupplier min;
/*     */     
/*     */     protected final IDoubleCoordinateSupplier max;
/*     */ 
/*     */     
/*     */     public StaticLayerEntry(NoiseBlockArray blockArray, ICoordinateCondition mapCondition, IDoubleCoordinateSupplier min, IDoubleCoordinateSupplier max) {
/* 182 */       super(blockArray, mapCondition);
/* 183 */       this.min = min;
/* 184 */       this.max = max;
/*     */     }
/*     */     
/*     */     public int getMinInt(int seed, int x, int z) {
/* 188 */       return MathUtil.floor(getMinValue(seed, x, z));
/*     */     }
/*     */     
/*     */     public double getMinValue(int seed, int x, int z) {
/* 192 */       return this.min.get(seed, x, z);
/*     */     }
/*     */     
/*     */     public int getMaxInt(int seed, int x, int z) {
/* 196 */       return MathUtil.floor(getMaxValue(seed, x, z));
/*     */     }
/*     */     
/*     */     public double getMaxValue(int seed, int x, int z) {
/* 200 */       return this.max.get(seed, x, z);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 206 */       return "StaticLayerEntry{blockArray=" + String.valueOf(this.blockArray) + ", mapCondition=" + String.valueOf(this.mapCondition) + ", min=" + String.valueOf(this.min) + ", max=" + String.valueOf(this.max) + "}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class DynamicLayerEntry
/*     */     extends LayerEntry
/*     */   {
/*     */     public DynamicLayerEntry(NoiseBlockArray blockArray, ICoordinateCondition mapCondition) {
/* 218 */       super(blockArray, mapCondition);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 224 */       return "DynamicLayerEntry{blockArray=" + String.valueOf(this.blockArray) + ", mapCondition=" + String.valueOf(this.mapCondition) + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\container\LayerContainer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */