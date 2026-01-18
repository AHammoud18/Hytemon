/*     */ package com.hypixel.hytale.server.worldgen.loader;
/*     */ 
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.PrefabLoader;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.PrefabSupplier;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.impl.IPrefabBuffer;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.impl.PrefabBuffer;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGenerator;
/*     */ import com.hypixel.hytale.server.worldgen.util.bounds.ChunkBounds;
/*     */ import com.hypixel.hytale.server.worldgen.util.bounds.IChunkBounds;
/*     */ import java.nio.file.Path;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class WorldGenPrefabSupplier
/*     */   implements PrefabSupplier {
/*  17 */   public static final WorldGenPrefabSupplier[] EMPTY_ARRAY = new WorldGenPrefabSupplier[0];
/*     */   
/*     */   private final WorldGenPrefabLoader loader;
/*     */   private final String prefabKey;
/*     */   private final Path path;
/*     */   private String prefabName;
/*     */   @Nullable
/*     */   private ChunkBounds bounds;
/*     */   
/*     */   public WorldGenPrefabSupplier(WorldGenPrefabLoader loader, String prefabKey, Path path) {
/*  27 */     this.loader = loader;
/*  28 */     this.path = path;
/*  29 */     this.bounds = null;
/*  30 */     this.prefabKey = prefabKey;
/*     */   }
/*     */   
/*     */   public WorldGenPrefabLoader getLoader() {
/*  34 */     return this.loader;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String getName() {
/*  39 */     return this.path.toString();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String getPrefabName() {
/*  44 */     if (this.prefabName == null)
/*     */     {
/*     */       
/*  47 */       this.prefabName = PrefabLoader.resolveRelativeJsonPath(this.prefabKey, this.path, this.loader.getRootFolder());
/*     */     }
/*  49 */     return this.prefabName;
/*     */   }
/*     */   
/*     */   public Path getPath() {
/*  53 */     return this.path;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public IPrefabBuffer get() {
/*  59 */     return (IPrefabBuffer)(ChunkGenerator.getResource()).prefabs.get(this);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public IChunkBounds getBounds(@Nonnull IPrefabBuffer buffer) {
/*  64 */     if (this.bounds == null) {
/*  65 */       this.bounds = getBounds(0, 0, 0, buffer, PrefabRotation.ROTATION_0, new ChunkBounds());
/*     */     }
/*  67 */     return (IChunkBounds)this.bounds;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/*  72 */     if (this == o) return true; 
/*  73 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/*  75 */     WorldGenPrefabSupplier that = (WorldGenPrefabSupplier)o;
/*     */     
/*  77 */     return this.path.equals(that.path);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  82 */     return this.path.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  88 */     return "WorldGenPrefabSupplier{path=" + String.valueOf(this.path) + "}";
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
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private ChunkBounds getBounds(int depth, int x, int z, @Nonnull IPrefabBuffer prefab, @Nonnull PrefabRotation rotation, @Nonnull ChunkBounds bounds) {
/* 107 */     if (depth >= 10) return bounds;
/*     */     
/* 109 */     int minX = x + prefab.getMinX(rotation);
/* 110 */     int minZ = z + prefab.getMinZ(rotation);
/* 111 */     int maxX = x + prefab.getMaxX(rotation);
/* 112 */     int maxZ = z + prefab.getMaxZ(rotation);
/* 113 */     bounds.include(minX, minZ, maxX, maxZ);
/*     */     
/* 115 */     for (PrefabBuffer.ChildPrefab child : prefab.getChildPrefabs()) {
/* 116 */       int childX = x + rotation.getX(child.getX(), child.getZ());
/* 117 */       int childZ = z + rotation.getZ(child.getX(), child.getZ());
/*     */       
/* 119 */       for (WorldGenPrefabSupplier supplier : this.loader.get(child.getPath())) {
/* 120 */         IPrefabBuffer childPrefab = supplier.get();
/* 121 */         PrefabRotation childRotation = rotation.add(child.getRotation());
/*     */         
/* 123 */         getBounds(depth + 1, childX, childZ, childPrefab, childRotation, bounds);
/*     */       } 
/*     */     } 
/*     */     
/* 127 */     return bounds;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\WorldGenPrefabSupplier.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */