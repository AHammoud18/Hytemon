/*     */ package com.hypixel.hytale.builtin.path;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.path.path.IPrefabPath;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PathSet
/*     */ {
/* 156 */   private final List<IPrefabPath> paths = (List<IPrefabPath>)new ObjectArrayList();
/*     */   
/*     */   public void add(IPrefabPath path) {
/* 159 */     this.paths.add(path);
/*     */   }
/*     */   
/*     */   public void remove(IPrefabPath path) {
/* 163 */     this.paths.remove(path);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public IPrefabPath getNearestPath(@Nonnull Vector3d position, @Nullable Set<UUID> disallowedPaths, ComponentAccessor<EntityStore> componentAccessor) {
/* 168 */     IPrefabPath nearest = null;
/* 169 */     double minDist2 = Double.MAX_VALUE;
/* 170 */     for (int i = 0; i < this.paths.size(); i++) {
/* 171 */       IPrefabPath path = this.paths.get(i);
/* 172 */       if (disallowedPaths == null || !disallowedPaths.contains(path.getId())) {
/*     */         
/* 174 */         Vector3d nearestWp = path.getNearestWaypointPosition(position, componentAccessor);
/* 175 */         double dist2 = position.distanceSquaredTo(nearestWp);
/* 176 */         if (dist2 < minDist2) {
/* 177 */           nearest = path;
/* 178 */           minDist2 = dist2;
/*     */         } 
/*     */       } 
/* 181 */     }  return nearest;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\path\PrefabPathCollection$PathSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */