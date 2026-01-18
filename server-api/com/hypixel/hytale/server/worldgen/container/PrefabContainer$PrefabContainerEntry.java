/*    */ package com.hypixel.hytale.server.worldgen.container;
/*    */ 
/*    */ import com.hypixel.hytale.common.map.IWeightedMap;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.server.worldgen.loader.WorldGenPrefabSupplier;
/*    */ import com.hypixel.hytale.server.worldgen.prefab.PrefabPatternGenerator;
/*    */ import com.hypixel.hytale.server.worldgen.util.bounds.IChunkBounds;
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
/*    */ public class PrefabContainerEntry
/*    */ {
/*    */   protected final IWeightedMap<WorldGenPrefabSupplier> prefabs;
/*    */   protected final PrefabPatternGenerator prefabPatternGenerator;
/*    */   protected final int environmentId;
/* 53 */   protected int extend = -1;
/*    */   
/*    */   public PrefabContainerEntry(IWeightedMap<WorldGenPrefabSupplier> prefabs, PrefabPatternGenerator prefabPatternGenerator, int environmentId) {
/* 56 */     this.prefabs = prefabs;
/* 57 */     this.prefabPatternGenerator = prefabPatternGenerator;
/* 58 */     this.environmentId = environmentId;
/*    */   }
/*    */   
/*    */   public IWeightedMap<WorldGenPrefabSupplier> getPrefabs() {
/* 62 */     return this.prefabs;
/*    */   }
/*    */   
/*    */   public int getEnvironmentId() {
/* 66 */     return this.environmentId;
/*    */   }
/*    */   
/*    */   public int getExtents() {
/* 70 */     if (this.extend == -1) {
/* 71 */       int max = 0;
/* 72 */       for (WorldGenPrefabSupplier supplier : (WorldGenPrefabSupplier[])this.prefabs.internalKeys()) {
/* 73 */         IChunkBounds bounds = supplier.getBounds(supplier.get());
/* 74 */         int lengthX = bounds.getHighBoundX() - bounds.getLowBoundX();
/* 75 */         int lengthZ = bounds.getHighBoundZ() - bounds.getLowBoundZ();
/* 76 */         max = MathUtil.maxValue(max, lengthX, lengthZ);
/*    */       } 
/* 78 */       this.extend = max;
/*    */     } 
/* 80 */     return this.extend;
/*    */   }
/*    */   
/*    */   public PrefabPatternGenerator getPrefabPatternGenerator() {
/* 84 */     return this.prefabPatternGenerator;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 90 */     return "PrefabContainerEntry{prefabs=" + String.valueOf(this.prefabs) + ", prefabPatternGenerator=" + String.valueOf(this.prefabPatternGenerator) + ", extend=" + this.extend + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\container\PrefabContainer$PrefabContainerEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */