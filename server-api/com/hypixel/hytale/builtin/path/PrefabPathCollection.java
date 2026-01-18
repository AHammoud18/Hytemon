/*     */ package com.hypixel.hytale.builtin.path;
/*     */ 
/*     */ import com.hypixel.fastutil.ints.Int2ObjectConcurrentHashMap;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.builtin.path.path.IPrefabPath;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrefabPathCollection
/*     */ {
/*  29 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*     */   private final int worldgenId;
/*     */   
/*  33 */   private final Map<UUID, IPrefabPath> paths = (Map<UUID, IPrefabPath>)new Object2ObjectOpenHashMap();
/*     */   
/*  35 */   private final Int2ObjectMap<PathSet> pathsByFriendlyName = (Int2ObjectMap<PathSet>)new Int2ObjectOpenHashMap();
/*     */   
/*     */   public PrefabPathCollection(int id) {
/*  38 */     this.worldgenId = id;
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
/*     */   @Nullable
/*     */   public IPrefabPath getNearestPrefabPath(int nameIndex, @Nonnull Vector3d position, Set<UUID> disallowedPaths, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  52 */     PathSet set = (PathSet)this.pathsByFriendlyName.getOrDefault(nameIndex, null);
/*  53 */     if (set == null) return null;
/*     */     
/*  55 */     return set.getNearestPath(position, disallowedPaths, componentAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IPrefabPath getPath(UUID id) {
/*  65 */     return this.paths.getOrDefault(id, null);
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
/*     */   public IPrefabPath getOrConstructPath(@Nonnull UUID id, @Nonnull String name, @Nonnull Int2ObjectConcurrentHashMap.IntBiObjFunction<UUID, String, IPrefabPath> pathGenerator) {
/*  77 */     IPrefabPath path = this.paths.computeIfAbsent(id, k -> {
/*     */           LOGGER.at(Level.FINER).log("Adding path %s.%s", this.worldgenId, k);
/*     */           
/*     */           return (IPrefabPath)pathGenerator.apply(this.worldgenId, k, name);
/*     */         });
/*  82 */     int nameIndex = AssetRegistry.getOrCreateTagIndex(name);
/*  83 */     PathSet set = (PathSet)this.pathsByFriendlyName.computeIfAbsent(nameIndex, s -> new PathSet());
/*  84 */     set.add(path);
/*     */     
/*  86 */     return path;
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
/*     */   @Nullable
/*     */   public IPrefabPath getNearestPrefabPath(@Nonnull Vector3d position, @Nullable Set<UUID> disallowedPaths, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  99 */     IPrefabPath nearest = null;
/* 100 */     double minDist2 = Double.MAX_VALUE;
/* 101 */     for (IPrefabPath path : this.paths.values()) {
/* 102 */       if (disallowedPaths != null && disallowedPaths.contains(path.getId()))
/*     */         continue; 
/* 104 */       double dist2 = position.distanceSquaredTo(path.getNearestWaypointPosition(position, componentAccessor));
/* 105 */       if (dist2 < minDist2) {
/* 106 */         nearest = path;
/* 107 */         minDist2 = dist2;
/*     */       } 
/*     */     } 
/* 110 */     return nearest;
/*     */   }
/*     */   
/*     */   public void removePathWaypoint(UUID id, int index) {
/* 114 */     removePathWaypoint(id, index, false);
/*     */   }
/*     */   
/*     */   public void unloadPathWaypoint(UUID id, int index) {
/* 118 */     removePathWaypoint(id, index, true);
/*     */   }
/*     */   
/*     */   private void removePathWaypoint(UUID id, int index, boolean unload) {
/* 122 */     IPrefabPath path = getPath(id);
/* 123 */     LOGGER.at(Level.FINER).log("%s waypoint %s from path %s.%s", unload ? "Unloading" : "Removing", Integer.valueOf(index), Integer.valueOf(this.worldgenId), id);
/* 124 */     if (path == null) {
/* 125 */       LOGGER.at(Level.SEVERE).log("Path %s.%s not found", this.worldgenId, id);
/*     */       
/*     */       return;
/*     */     } 
/* 129 */     if (unload) {
/* 130 */       path.unloadWaypoint(index);
/*     */     } else {
/* 132 */       path.removeWaypoint(index, this.worldgenId);
/*     */     } 
/*     */ 
/*     */     
/* 136 */     if (path.length() == 0 || !path.hasLoadedWaypoints()) {
/* 137 */       LOGGER.at(Level.FINER).log("%s path %s.%s", unload ? "Unloading" : "Removing", Integer.valueOf(this.worldgenId), id);
/* 138 */       removePath(id);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removePath(UUID id) {
/* 143 */     IPrefabPath removed = this.paths.remove(id);
/* 144 */     ((PathSet)this.pathsByFriendlyName.get(AssetRegistry.getTagIndex(removed.getName()))).remove(removed);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 148 */     return this.paths.isEmpty();
/*     */   }
/*     */   
/*     */   public void forEach(BiConsumer<UUID, IPrefabPath> consumer) {
/* 152 */     this.paths.forEach(consumer);
/*     */   }
/*     */   
/*     */   private static class PathSet {
/* 156 */     private final List<IPrefabPath> paths = (List<IPrefabPath>)new ObjectArrayList();
/*     */     
/*     */     public void add(IPrefabPath path) {
/* 159 */       this.paths.add(path);
/*     */     }
/*     */     
/*     */     public void remove(IPrefabPath path) {
/* 163 */       this.paths.remove(path);
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public IPrefabPath getNearestPath(@Nonnull Vector3d position, @Nullable Set<UUID> disallowedPaths, ComponentAccessor<EntityStore> componentAccessor) {
/* 168 */       IPrefabPath nearest = null;
/* 169 */       double minDist2 = Double.MAX_VALUE;
/* 170 */       for (int i = 0; i < this.paths.size(); i++) {
/* 171 */         IPrefabPath path = this.paths.get(i);
/* 172 */         if (disallowedPaths == null || !disallowedPaths.contains(path.getId())) {
/*     */           
/* 174 */           Vector3d nearestWp = path.getNearestWaypointPosition(position, componentAccessor);
/* 175 */           double dist2 = position.distanceSquaredTo(nearestWp);
/* 176 */           if (dist2 < minDist2) {
/* 177 */             nearest = path;
/* 178 */             minDist2 = dist2;
/*     */           } 
/*     */         } 
/* 181 */       }  return nearest;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\path\PrefabPathCollection.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */