/*    */ package com.hypixel.hytale.server.worldgen.container;
/*    */ 
/*    */ import com.hypixel.hytale.common.map.IWeightedMap;
/*    */ import com.hypixel.hytale.procedurallib.condition.ICoordinateCondition;
/*    */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TintContainerEntry
/*    */ {
/*    */   private final IWeightedMap<Integer> colorMapping;
/*    */   private final NoiseProperty valueNoise;
/*    */   private final ICoordinateCondition mapCondition;
/*    */   
/*    */   public TintContainerEntry(IWeightedMap<Integer> colorMapping, NoiseProperty valueNoise, ICoordinateCondition mapCondition) {
/* 62 */     this.colorMapping = colorMapping;
/* 63 */     this.valueNoise = valueNoise;
/* 64 */     this.mapCondition = mapCondition;
/*    */   }
/*    */   
/*    */   public boolean shouldGenerate(int seed, int x, int z) {
/* 68 */     return this.mapCondition.eval(seed, x, z);
/*    */   }
/*    */   
/*    */   public int getTintColorAt(int seed, int x, int z) {
/* 72 */     return ((Integer)this.colorMapping.get(seed, x, z, (iSeed, ix, iz, entry) -> entry.valueNoise.get(iSeed, ix, iz), this)).intValue();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 78 */     return "TintContainerEntry{colorMapping=" + String.valueOf(this.colorMapping) + ", valueNoise=" + String.valueOf(this.valueNoise) + ", mapCondition=" + String.valueOf(this.mapCondition) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\container\TintContainer$TintContainerEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */