/*     */ package com.hypixel.hytale.server.worldgen.container;
/*     */ 
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.impl.IPrefabBuffer;
/*     */ import com.hypixel.hytale.server.worldgen.loader.WorldGenPrefabSupplier;
/*     */ import com.hypixel.hytale.server.worldgen.prefab.PrefabCategory;
/*     */ import com.hypixel.hytale.server.worldgen.util.bounds.IChunkBounds;
/*     */ import com.hypixel.hytale.server.worldgen.util.condition.BlockMaskCondition;
/*     */ import java.util.Objects;
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
/*     */ public class UniquePrefabEntry
/*     */ {
/*     */   protected final String name;
/*     */   protected final PrefabCategory category;
/*     */   @Nonnull
/*     */   protected final Vector3i position;
/*     */   @Nonnull
/*     */   protected final WorldGenPrefabSupplier prefabSupplier;
/*     */   protected final BlockMaskCondition configuration;
/*     */   @Nonnull
/*     */   protected final PrefabRotation rotation;
/*     */   protected final Vector3d spawnOffset;
/*     */   protected final int lowBoundX;
/*     */   protected final int lowBoundY;
/*     */   protected final int lowBoundZ;
/*     */   protected final int highBoundX;
/*     */   protected final int highBoundY;
/*     */   protected final int highBoundZ;
/*     */   protected final int environmentId;
/*     */   protected final boolean fitHeightmap;
/*     */   protected final boolean submerge;
/*     */   protected final double exclusionRadiusSquared;
/*     */   protected final boolean spawnLocation;
/*     */   protected final boolean showOnMap;
/*     */   
/*     */   private UniquePrefabEntry(String name, @Nonnull PrefabCategory category, @Nonnull Vector3i position, @Nonnull WorldGenPrefabSupplier prefabSupplier, BlockMaskCondition configuration, @Nonnull PrefabRotation rotation, Vector3d spawnOffset, int environmentId, boolean fitHeightmap, boolean submergeable, double exclusionRadiusSquared, boolean spawnLocation, boolean showOnMap) {
/* 109 */     this.name = name;
/* 110 */     this.category = category;
/* 111 */     this.position = position;
/* 112 */     this.prefabSupplier = prefabSupplier;
/* 113 */     this.configuration = configuration;
/* 114 */     this.rotation = rotation;
/* 115 */     this.spawnOffset = spawnOffset;
/* 116 */     this.environmentId = environmentId;
/* 117 */     this.fitHeightmap = fitHeightmap;
/* 118 */     this.submerge = submergeable;
/* 119 */     this.exclusionRadiusSquared = exclusionRadiusSquared;
/* 120 */     this.spawnLocation = spawnLocation;
/* 121 */     this.showOnMap = showOnMap;
/*     */ 
/*     */     
/* 124 */     IPrefabBuffer prefab = Objects.<IPrefabBuffer>requireNonNull(prefabSupplier.get());
/* 125 */     IChunkBounds bounds = prefabSupplier.getBounds(prefab);
/*     */ 
/*     */     
/* 128 */     this.lowBoundY = position.y + prefab.getMinY();
/* 129 */     this.highBoundY = position.y + prefab.getMaxY();
/*     */     
/* 131 */     this.lowBoundX = position.x + bounds.getLowBoundX(rotation);
/* 132 */     this.lowBoundZ = position.z + bounds.getLowBoundZ(rotation);
/* 133 */     this.highBoundX = position.x + bounds.getHighBoundX(rotation);
/* 134 */     this.highBoundZ = position.z + bounds.getHighBoundZ(rotation);
/*     */   }
/*     */   
/*     */   public String getName() {
/* 138 */     return this.name;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public PrefabCategory getCategory() {
/* 143 */     return this.category;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i getPosition() {
/* 148 */     return this.position;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public WorldGenPrefabSupplier getPrefabSupplier() {
/* 153 */     return this.prefabSupplier;
/*     */   }
/*     */   
/*     */   public BlockMaskCondition getConfiguration() {
/* 157 */     return this.configuration;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public PrefabRotation getRotation() {
/* 162 */     return this.rotation;
/*     */   }
/*     */   
/*     */   public Vector3d getSpawnOffset() {
/* 166 */     return this.spawnOffset;
/*     */   }
/*     */   
/*     */   public int getLowBoundX() {
/* 170 */     return this.lowBoundX;
/*     */   }
/*     */   
/*     */   public int getLowBoundY() {
/* 174 */     return this.lowBoundY;
/*     */   }
/*     */   
/*     */   public int getLowBoundZ() {
/* 178 */     return this.lowBoundZ;
/*     */   }
/*     */   
/*     */   public int getHighBoundX() {
/* 182 */     return this.highBoundX;
/*     */   }
/*     */   
/*     */   public int getHighBoundY() {
/* 186 */     return this.highBoundY;
/*     */   }
/*     */   
/*     */   public int getHighBoundZ() {
/* 190 */     return this.highBoundZ;
/*     */   }
/*     */   
/*     */   public int getEnvironmentId() {
/* 194 */     return this.environmentId;
/*     */   }
/*     */   
/*     */   public boolean isFitHeightmap() {
/* 198 */     return this.fitHeightmap;
/*     */   }
/*     */   
/*     */   public boolean isSubmerge() {
/* 202 */     return this.submerge;
/*     */   }
/*     */   
/*     */   public double getExclusionRadiusSquared() {
/* 206 */     return this.exclusionRadiusSquared;
/*     */   }
/*     */   
/*     */   public boolean isSpawnLocation() {
/* 210 */     return this.spawnLocation;
/*     */   }
/*     */   
/*     */   public boolean isShowOnMap() {
/* 214 */     return this.showOnMap;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 220 */     return "UniquePrefabEntry{position=" + String.valueOf(this.position) + ", prefabSupplier=" + String.valueOf(this.prefabSupplier) + ", configuration=" + String.valueOf(this.configuration) + ", rotation=" + String.valueOf(this.rotation) + ", lowBoundX=" + this.lowBoundX + ", lowBoundZ=" + this.lowBoundZ + ", highBoundX=" + this.highBoundX + ", highBoundZ=" + this.highBoundZ + ", fitHeightmap=" + this.fitHeightmap + ", submerge=" + this.submerge + ", exclusionRadiusSquared=" + this.exclusionRadiusSquared + ", spawnLocation=" + this.spawnLocation + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\container\UniquePrefabContainer$UniquePrefabEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */