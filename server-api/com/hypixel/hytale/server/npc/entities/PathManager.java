/*     */ package com.hypixel.hytale.server.npc.entities;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.path.WorldPathData;
/*     */ import com.hypixel.hytale.builtin.path.path.IPrefabPath;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.WorldGenId;
/*     */ import com.hypixel.hytale.server.core.universe.world.path.IPath;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Supplier;
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
/*     */ public class PathManager
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<PathManager> CODEC;
/*     */   @Nullable
/*     */   private UUID currentPathHint;
/*     */   @Nullable
/*     */   private IPath<?> currentPath;
/*     */   
/*     */   static {
/*  37 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(PathManager.class, PathManager::new).append(new KeyedCodec("CurrentPath", (Codec)Codec.UUID_BINARY), (npcEntity, uuid) -> npcEntity.currentPathHint = uuid, npcEntity -> npcEntity.currentPathHint).setVersionRange(5, 5).add()).build();
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPrefabPath(@Nonnull UUID currentPath, @Nonnull IPrefabPath path) {
/*  58 */     this.currentPathHint = currentPath;
/*  59 */     this.currentPath = (IPath<?>)path;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTransientPath(@Nonnull IPath<?> path) {
/*  68 */     this.currentPathHint = null;
/*  69 */     this.currentPath = path;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFollowingPath() {
/*  78 */     return (this.currentPathHint != null || this.currentPath != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public UUID getCurrentPathHint() {
/*  86 */     return this.currentPathHint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public IPath<?> getPath(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  98 */     if (this.currentPath == null) {
/*  99 */       if (this.currentPathHint == null) return null; 
/* 100 */       WorldPathData worldPathData = (WorldPathData)componentAccessor.getResource(WorldPathData.getResourceType());
/* 101 */       WorldGenId worldGenIdComponent = (WorldGenId)componentAccessor.getComponent(ref, WorldGenId.getComponentType());
/* 102 */       int worldGenId = (worldGenIdComponent != null) ? worldGenIdComponent.getWorldGenId() : 0;
/* 103 */       this.currentPath = (IPath<?>)worldPathData.getPrefabPath(worldGenId, this.currentPathHint, false);
/*     */     } 
/* 105 */     return this.currentPath;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\entities\PathManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */