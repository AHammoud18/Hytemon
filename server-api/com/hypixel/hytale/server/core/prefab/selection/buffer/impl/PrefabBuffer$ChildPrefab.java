/*     */ package com.hypixel.hytale.server.core.prefab.selection.buffer.impl;
/*     */ 
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabWeights;
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
/*     */ public class ChildPrefab
/*     */ {
/*     */   private final int x;
/*     */   private final int y;
/*     */   private final int z;
/*     */   @Nonnull
/*     */   private final String path;
/*     */   private final boolean fitHeightmap;
/*     */   private final boolean inheritSeed;
/*     */   private final boolean inheritHeightCondition;
/*     */   @Nonnull
/*     */   private final PrefabWeights weights;
/*     */   @Nonnull
/*     */   private final PrefabRotation rotation;
/*     */   
/*     */   private ChildPrefab(int x, int y, int z, @Nonnull String path, boolean fitHeightmap, boolean inheritSeed, boolean inheritHeightCondition, @Nonnull PrefabWeights weights, @Nonnull PrefabRotation rotation) {
/* 266 */     this.x = x;
/* 267 */     this.y = y;
/* 268 */     this.z = z;
/* 269 */     this.path = path;
/* 270 */     this.fitHeightmap = fitHeightmap;
/* 271 */     this.inheritSeed = inheritSeed;
/* 272 */     this.inheritHeightCondition = inheritHeightCondition;
/* 273 */     this.weights = weights;
/* 274 */     this.rotation = rotation;
/*     */   }
/*     */   
/*     */   public int getX() {
/* 278 */     return this.x;
/*     */   }
/*     */   
/*     */   public int getY() {
/* 282 */     return this.y;
/*     */   }
/*     */   
/*     */   public int getZ() {
/* 286 */     return this.z;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String getPath() {
/* 291 */     return this.path;
/*     */   }
/*     */   
/*     */   public boolean isFitHeightmap() {
/* 295 */     return this.fitHeightmap;
/*     */   }
/*     */   
/*     */   public boolean isInheritSeed() {
/* 299 */     return this.inheritSeed;
/*     */   }
/*     */   
/*     */   public boolean isInheritHeightCondition() {
/* 303 */     return this.inheritHeightCondition;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public PrefabWeights getWeights() {
/* 308 */     return this.weights;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public PrefabRotation getRotation() {
/* 313 */     return this.rotation;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\prefab\selection\buffer\impl\PrefabBuffer$ChildPrefab.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */