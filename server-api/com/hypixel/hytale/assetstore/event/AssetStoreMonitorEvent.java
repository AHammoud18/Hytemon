/*    */ package com.hypixel.hytale.assetstore.event;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetStore;
/*    */ import java.nio.file.Path;
/*    */ import java.util.List;
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
/*    */ public class AssetStoreMonitorEvent
/*    */   extends AssetMonitorEvent<Void>
/*    */ {
/*    */   @Nonnull
/*    */   private final AssetStore<?, ?, ?> assetStore;
/*    */   
/*    */   public AssetStoreMonitorEvent(@Nonnull String assetPack, @Nonnull AssetStore<?, ?, ?> assetStore, @Nonnull List<Path> createdOrModified, @Nonnull List<Path> removed, @Nonnull List<Path> createdOrModifiedDirectories, @Nonnull List<Path> removedDirectories) {
/* 36 */     super(assetPack, createdOrModified, removed, createdOrModifiedDirectories, removedDirectories);
/*    */     
/* 38 */     this.assetStore = assetStore;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public AssetStore<?, ?, ?> getAssetStore() {
/* 46 */     return this.assetStore;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 52 */     return "AssetMonitorEvent{assetStore=" + String.valueOf(this.assetStore) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\event\AssetStoreMonitorEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */