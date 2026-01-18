/*    */ package com.hypixel.hytale.server.core.asset;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetPack;
/*    */ import com.hypixel.hytale.event.IEvent;
/*    */ 
/*    */ public class AssetPackRegisterEvent
/*    */   implements IEvent<Void> {
/*    */   private final AssetPack assetPack;
/*    */   
/*    */   public AssetPackRegisterEvent(AssetPack assetPack) {
/* 11 */     this.assetPack = assetPack;
/*    */   }
/*    */   
/*    */   public AssetPack getAssetPack() {
/* 15 */     return this.assetPack;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\AssetPackRegisterEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */