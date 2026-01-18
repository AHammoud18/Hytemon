/*     */ package com.hypixel.hytale.assetstore.event;
/*     */ 
/*     */ import com.hypixel.hytale.event.IEvent;
/*     */ import java.nio.file.Path;
/*     */ import java.util.List;
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
/*     */ public abstract class AssetMonitorEvent<T>
/*     */   implements IEvent<T>
/*     */ {
/*     */   @Nonnull
/*     */   private final List<Path> createdOrModifiedFilesToLoad;
/*     */   @Nonnull
/*     */   private final List<Path> removedFilesToUnload;
/*     */   @Nonnull
/*     */   private final List<Path> createdOrModifiedDirectories;
/*     */   @Nonnull
/*     */   private final List<Path> removedFilesAndDirectories;
/*     */   @Nonnull
/*     */   private final String assetPack;
/*     */   
/*     */   public AssetMonitorEvent(@Nonnull String assetPack, @Nonnull List<Path> createdOrModified, @Nonnull List<Path> removed, @Nonnull List<Path> createdDirectories, @Nonnull List<Path> removedDirectories) {
/*  60 */     this.assetPack = assetPack;
/*  61 */     this.createdOrModifiedFilesToLoad = createdOrModified;
/*  62 */     this.removedFilesToUnload = removed;
/*  63 */     this.createdOrModifiedDirectories = createdDirectories;
/*  64 */     this.removedFilesAndDirectories = removedDirectories;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String getAssetPack() {
/*  72 */     return this.assetPack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<Path> getCreatedOrModifiedFilesToLoad() {
/*  80 */     return this.createdOrModifiedFilesToLoad;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<Path> getRemovedFilesToUnload() {
/*  88 */     return this.removedFilesToUnload;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<Path> getRemovedFilesAndDirectories() {
/*  96 */     return this.removedFilesAndDirectories;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<Path> getCreatedOrModifiedDirectories() {
/* 104 */     return this.createdOrModifiedDirectories;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\event\AssetMonitorEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */