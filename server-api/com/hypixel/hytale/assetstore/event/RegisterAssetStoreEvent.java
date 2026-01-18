/*    */ package com.hypixel.hytale.assetstore.event;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetStore;
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
/*    */ public class RegisterAssetStoreEvent
/*    */   extends AssetStoreEvent<Void>
/*    */ {
/*    */   public RegisterAssetStoreEvent(@Nonnull AssetStore<?, ?, ?> assetStore) {
/* 18 */     super(assetStore);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\event\RegisterAssetStoreEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */