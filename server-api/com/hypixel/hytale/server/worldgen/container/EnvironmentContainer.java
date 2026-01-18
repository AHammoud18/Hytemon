/*    */ package com.hypixel.hytale.server.worldgen.container;
/*    */ 
/*    */ import com.hypixel.hytale.common.map.IWeightedMap;
/*    */ import com.hypixel.hytale.procedurallib.condition.DefaultCoordinateCondition;
/*    */ import com.hypixel.hytale.procedurallib.condition.ICoordinateCondition;
/*    */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*    */ import java.util.Arrays;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnvironmentContainer
/*    */ {
/*    */   protected final DefaultEnvironmentContainerEntry defaultEntry;
/*    */   protected final EnvironmentContainerEntry[] entries;
/*    */   
/*    */   public EnvironmentContainer(DefaultEnvironmentContainerEntry defaultEntry, EnvironmentContainerEntry[] entries) {
/* 20 */     this.defaultEntry = defaultEntry;
/* 21 */     this.entries = entries;
/*    */   }
/*    */   
/*    */   public int getEnvironmentAt(int seed, int x, int z) {
/* 25 */     for (EnvironmentContainerEntry entry : this.entries) {
/* 26 */       if (entry.shouldGenerate(seed, x, z)) {
/* 27 */         return entry.getEnvironmentAt(seed, x, z);
/*    */       }
/*    */     } 
/* 30 */     return this.defaultEntry.getEnvironmentAt(seed, x, z);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 36 */     return "EnvironmentContainer{defaultEntry=" + String.valueOf(this.defaultEntry) + ", entries=" + 
/*    */       
/* 38 */       Arrays.toString((Object[])this.entries) + "}";
/*    */   }
/*    */   
/*    */   public static class DefaultEnvironmentContainerEntry
/*    */     extends EnvironmentContainerEntry {
/*    */     public DefaultEnvironmentContainerEntry(IWeightedMap<Integer> environmentMapping, NoiseProperty valueNoise) {
/* 44 */       super(environmentMapping, valueNoise, (ICoordinateCondition)DefaultCoordinateCondition.DEFAULT_TRUE);
/*    */     }
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     public String toString() {
/* 50 */       return "DefaultEnvironmentContainerEntry{environmentMapping=" + String.valueOf(this.environmentMapping) + ", valueNoise=" + String.valueOf(this.valueNoise) + ", mapCondition=" + String.valueOf(this.mapCondition) + "}";
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class EnvironmentContainerEntry
/*    */   {
/* 59 */     public static final EnvironmentContainerEntry[] EMPTY_ARRAY = new EnvironmentContainerEntry[0];
/*    */     
/*    */     protected final IWeightedMap<Integer> environmentMapping;
/*    */     protected final NoiseProperty valueNoise;
/*    */     protected final ICoordinateCondition mapCondition;
/*    */     
/*    */     public EnvironmentContainerEntry(IWeightedMap<Integer> environmentMapping, NoiseProperty valueNoise, ICoordinateCondition mapCondition) {
/* 66 */       this.environmentMapping = environmentMapping;
/* 67 */       this.valueNoise = valueNoise;
/* 68 */       this.mapCondition = mapCondition;
/*    */     }
/*    */     
/*    */     public boolean shouldGenerate(int seed, int x, int z) {
/* 72 */       return this.mapCondition.eval(seed, x, z);
/*    */     }
/*    */     
/*    */     public int getEnvironmentAt(int seed, int x, int z) {
/* 76 */       return ((Integer)this.environmentMapping.get(seed, x, z, (iSeed, ix, iz, entry) -> entry.valueNoise.get(iSeed, ix, iz), this)).intValue();
/*    */     }
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     public String toString() {
/* 82 */       return "EnvironmentContainerEntry{environmentMapping=" + String.valueOf(this.environmentMapping) + ", valueNoise=" + String.valueOf(this.valueNoise) + ", mapCondition=" + String.valueOf(this.mapCondition) + "}";
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\container\EnvironmentContainer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */