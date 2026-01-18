/*     */ package com.hypixel.hytale.builtin.path;
/*     */ 
/*     */ import com.hypixel.fastutil.ints.Int2ObjectConcurrentHashMap;
/*     */ import com.hypixel.hytale.builtin.path.path.IPrefabPath;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Resource;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldPathData
/*     */   implements Resource<EntityStore>
/*     */ {
/*     */   public static ResourceType<EntityStore, WorldPathData> getResourceType() {
/*  28 */     return PathPlugin.get().getWorldPathDataResourceType();
/*     */   }
/*     */   
/*  31 */   private final Int2ObjectMap<PrefabPathCollection> prefabPaths = (Int2ObjectMap<PrefabPathCollection>)new Int2ObjectOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public IPrefabPath getNearestPrefabPath(int worldgenId, int nameIndex, @Nonnull Vector3d position, Set<UUID> disallowedPaths, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  38 */     PrefabPathCollection entry = getPrefabPathCollection(worldgenId);
/*  39 */     return entry.getNearestPrefabPath(nameIndex, position, disallowedPaths, componentAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public IPrefabPath getNearestPrefabPath(int worldgenId, @Nonnull Vector3d position, Set<UUID> disallowedPaths, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  47 */     return getPrefabPathCollection(worldgenId).getNearestPrefabPath(position, disallowedPaths, componentAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IPrefabPath getOrConstructPrefabPath(int worldgenId, @Nonnull UUID id, @Nonnull String name, @Nonnull Int2ObjectConcurrentHashMap.IntBiObjFunction<UUID, String, IPrefabPath> pathGenerator) {
/*  54 */     PrefabPathCollection entry = getPrefabPathCollection(worldgenId);
/*  55 */     return entry.getOrConstructPath(id, name, pathGenerator);
/*     */   }
/*     */   
/*     */   public void removePrefabPathWaypoint(int worldgenId, UUID id, int index) {
/*  59 */     PrefabPathCollection entry = getPrefabPathCollection(worldgenId);
/*  60 */     entry.removePathWaypoint(id, index);
/*  61 */     if (entry.isEmpty()) {
/*  62 */       this.prefabPaths.remove(worldgenId);
/*     */     }
/*     */   }
/*     */   
/*     */   public void unloadPrefabPathWaypoint(int worldgenId, UUID id, int index) {
/*  67 */     PrefabPathCollection entry = getPrefabPathCollection(worldgenId);
/*  68 */     entry.unloadPathWaypoint(id, index);
/*  69 */     if (entry.isEmpty()) {
/*  70 */       this.prefabPaths.remove(worldgenId);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removePrefabPath(int worldgenId, UUID id) {
/*  75 */     PrefabPathCollection entry = getPrefabPathCollection(worldgenId);
/*  76 */     entry.removePath(id);
/*  77 */     if (entry.isEmpty()) {
/*  78 */       this.prefabPaths.remove(worldgenId);
/*     */     }
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public IPrefabPath getPrefabPath(int worldgenId, UUID id, boolean ignoreLoadState) {
/*  84 */     PrefabPathCollection collection = getPrefabPathCollection(worldgenId);
/*  85 */     IPrefabPath path = collection.getPath(id);
/*  86 */     if (!ignoreLoadState && (path == null || !path.isFullyLoaded())) {
/*  87 */       return null;
/*     */     }
/*  89 */     return path;
/*     */   }
/*     */   
/*     */   public void compactPrefabPath(int worldgenId, UUID id) {
/*  93 */     IPrefabPath path = getPrefabPath(worldgenId, id, true);
/*  94 */     if (path == null) {
/*     */       return;
/*     */     }
/*  97 */     path.compact(worldgenId);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public List<IPrefabPath> getAllPrefabPaths() {
/* 102 */     ObjectArrayList<IPrefabPath> list = new ObjectArrayList();
/* 103 */     this.prefabPaths.forEach((id, entry) -> entry.forEach(()));
/* 104 */     return Collections.unmodifiableList((List<? extends IPrefabPath>)list);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public PrefabPathCollection getPrefabPathCollection(int worldgenId) {
/* 109 */     return (PrefabPathCollection)this.prefabPaths.computeIfAbsent(worldgenId, PrefabPathCollection::new);
/*     */   }
/*     */ 
/*     */   
/*     */   public Resource<EntityStore> clone() {
/* 114 */     throw new UnsupportedOperationException("Not implemented!");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\path\WorldPathData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */