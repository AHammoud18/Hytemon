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
/*    */ 
/*    */ 
/*    */ public class EnvironmentContainerEntry
/*    */ {
/* 59 */   public static final EnvironmentContainerEntry[] EMPTY_ARRAY = new EnvironmentContainerEntry[0];
/*    */   
/*    */   protected final IWeightedMap<Integer> environmentMapping;
/*    */   protected final NoiseProperty valueNoise;
/*    */   protected final ICoordinateCondition mapCondition;
/*    */   
/*    */   public EnvironmentContainerEntry(IWeightedMap<Integer> environmentMapping, NoiseProperty valueNoise, ICoordinateCondition mapCondition) {
/* 66 */     this.environmentMapping = environmentMapping;
/* 67 */     this.valueNoise = valueNoise;
/* 68 */     this.mapCondition = mapCondition;
/*    */   }
/*    */   
/*    */   public boolean shouldGenerate(int seed, int x, int z) {
/* 72 */     return this.mapCondition.eval(seed, x, z);
/*    */   }
/*    */   
/*    */   public int getEnvironmentAt(int seed, int x, int z) {
/* 76 */     return ((Integer)this.environmentMapping.get(seed, x, z, (iSeed, ix, iz, entry) -> entry.valueNoise.get(iSeed, ix, iz), this)).intValue();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 82 */     return "EnvironmentContainerEntry{environmentMapping=" + String.valueOf(this.environmentMapping) + ", valueNoise=" + String.valueOf(this.valueNoise) + ", mapCondition=" + String.valueOf(this.mapCondition) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\container\EnvironmentContainer$EnvironmentContainerEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */