/*    */ package com.hypixel.hytale.server.worldgen.container;
/*    */ 
/*    */ import com.hypixel.hytale.common.map.IWeightedMap;
/*    */ import com.hypixel.hytale.procedurallib.condition.DefaultCoordinateCondition;
/*    */ import com.hypixel.hytale.procedurallib.condition.ICoordinateCondition;
/*    */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TintContainer
/*    */ {
/*    */   private final DefaultTintContainerEntry defaultEntry;
/*    */   private final List<TintContainerEntry> entries;
/*    */   
/*    */   public TintContainer(DefaultTintContainerEntry defaultEntry, List<TintContainerEntry> entries) {
/* 21 */     this.defaultEntry = defaultEntry;
/* 22 */     this.entries = entries;
/*    */   }
/*    */   
/*    */   public int getTintColorAt(int seed, int x, int z) {
/* 26 */     for (int i = 0; i < this.entries.size(); i++) {
/* 27 */       if (((TintContainerEntry)this.entries.get(i)).shouldGenerate(seed, x, z)) {
/* 28 */         return ((TintContainerEntry)this.entries.get(i)).getTintColorAt(seed, x, z);
/*    */       }
/*    */     } 
/* 31 */     return this.defaultEntry.getTintColorAt(seed, x, z);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 37 */     return "TintContainer{defaultEntry=" + String.valueOf(this.defaultEntry) + ", entries=" + String.valueOf(this.entries) + "}";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static class DefaultTintContainerEntry
/*    */     extends TintContainerEntry
/*    */   {
/*    */     public DefaultTintContainerEntry(IWeightedMap<Integer> colorMapping, NoiseProperty valueNoise) {
/* 46 */       super(colorMapping, valueNoise, (ICoordinateCondition)DefaultCoordinateCondition.DEFAULT_TRUE);
/*    */     }
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     public String toString() {
/* 52 */       return "DefaultTintContainerEntry{}";
/*    */     }
/*    */   }
/*    */   
/*    */   public static class TintContainerEntry {
/*    */     private final IWeightedMap<Integer> colorMapping;
/*    */     private final NoiseProperty valueNoise;
/*    */     private final ICoordinateCondition mapCondition;
/*    */     
/*    */     public TintContainerEntry(IWeightedMap<Integer> colorMapping, NoiseProperty valueNoise, ICoordinateCondition mapCondition) {
/* 62 */       this.colorMapping = colorMapping;
/* 63 */       this.valueNoise = valueNoise;
/* 64 */       this.mapCondition = mapCondition;
/*    */     }
/*    */     
/*    */     public boolean shouldGenerate(int seed, int x, int z) {
/* 68 */       return this.mapCondition.eval(seed, x, z);
/*    */     }
/*    */     
/*    */     public int getTintColorAt(int seed, int x, int z) {
/* 72 */       return ((Integer)this.colorMapping.get(seed, x, z, (iSeed, ix, iz, entry) -> entry.valueNoise.get(iSeed, ix, iz), this)).intValue();
/*    */     }
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     public String toString() {
/* 78 */       return "TintContainerEntry{colorMapping=" + String.valueOf(this.colorMapping) + ", valueNoise=" + String.valueOf(this.valueNoise) + ", mapCondition=" + String.valueOf(this.mapCondition) + "}";
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\container\TintContainer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */