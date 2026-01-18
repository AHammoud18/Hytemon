/*    */ package com.hypixel.hytale.builtin.asseteditor.util;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetMap;
/*    */ import com.hypixel.hytale.assetstore.AssetStore;
/*    */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.LookupTableAssetMap;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AssetStoreUtil
/*    */ {
/*    */   @Deprecated
/*    */   public static <K, T extends com.hypixel.hytale.assetstore.map.JsonAssetWithMap<K, M>, M extends AssetMap<K, T>> String getIdFromIndex(@Nonnull AssetStore<K, T, M> assetStore, int assetIndex) {
/* 20 */     AssetMap assetMap = assetStore.getAssetMap();
/*    */     
/* 22 */     if (assetMap instanceof BlockTypeAssetMap) {
/* 23 */       return ((BlockTypeAssetMap)assetMap).getAsset(assetIndex).getId().toString();
/*    */     }
/* 25 */     if (assetMap instanceof IndexedLookupTableAssetMap) {
/* 26 */       return ((IndexedLookupTableAssetMap)assetMap).getAsset(assetIndex).getId().toString();
/*    */     }
/* 28 */     if (assetMap instanceof LookupTableAssetMap) {
/* 29 */       return ((LookupTableAssetMap)assetMap).getAsset(assetIndex).getId().toString();
/*    */     }
/* 31 */     throw new IllegalArgumentException("Asset can't be looked up by index! " + assetIndex);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\assetedito\\util\AssetStoreUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */