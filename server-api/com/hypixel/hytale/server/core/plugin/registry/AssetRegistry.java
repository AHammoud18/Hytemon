/*    */ package com.hypixel.hytale.server.core.plugin.registry;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetStore;
/*    */ import com.hypixel.hytale.function.consumer.BooleanConsumer;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AssetRegistry
/*    */ {
/*    */   protected final List<BooleanConsumer> unregister;
/*    */   
/*    */   public AssetRegistry(List<BooleanConsumer> unregister) {
/* 16 */     this.unregister = unregister;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public <K, T extends com.hypixel.hytale.assetstore.map.JsonAssetWithMap<K, M>, M extends com.hypixel.hytale.assetstore.AssetMap<K, T>, S extends AssetStore<K, T, M>> AssetRegistry register(@Nonnull S assetStore) {
/* 21 */     com.hypixel.hytale.assetstore.AssetRegistry.register((AssetStore)assetStore);
/* 22 */     this.unregister.add(shutdown -> com.hypixel.hytale.assetstore.AssetRegistry.unregister(assetStore));
/* 23 */     return this;
/*    */   }
/*    */   
/*    */   public void shutdown() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\plugin\registry\AssetRegistry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */