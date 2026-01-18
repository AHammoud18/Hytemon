/*    */ package com.hypixel.hytale.assetstore.event;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetStore;
/*    */ import com.hypixel.hytale.event.IEvent;
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
/*    */ public abstract class AssetStoreEvent<KeyType>
/*    */   implements IEvent<KeyType>
/*    */ {
/*    */   @Nonnull
/*    */   private final AssetStore<?, ?, ?> assetStore;
/*    */   
/*    */   public AssetStoreEvent(@Nonnull AssetStore<?, ?, ?> assetStore) {
/* 27 */     this.assetStore = assetStore;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public AssetStore<?, ?, ?> getAssetStore() {
/* 35 */     return this.assetStore;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 41 */     return "AssetStoreEvent{assetStore=" + String.valueOf(this.assetStore) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\event\AssetStoreEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */