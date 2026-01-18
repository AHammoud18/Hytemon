/*    */ package com.hypixel.hytale.server.core.asset.packet;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetMap;
/*    */ import com.hypixel.hytale.assetstore.AssetUpdateQuery;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SimpleAssetPacketGenerator<K, T extends JsonAssetWithMap<K, M>, M extends AssetMap<K, T>>
/*    */   extends AssetPacketGenerator<K, T, M>
/*    */ {
/*    */   public abstract Packet generateInitPacket(M paramM, Map<K, T> paramMap);
/*    */   
/*    */   public Packet generateUpdatePacket(M assetMap, Map<K, T> loadedAssets, @Nonnull AssetUpdateQuery query) {
/* 24 */     return generateUpdatePacket(assetMap, loadedAssets);
/*    */   }
/*    */ 
/*    */   
/*    */   public Packet generateRemovePacket(M assetMap, Set<K> removed, @Nonnull AssetUpdateQuery query) {
/* 29 */     return generateRemovePacket(assetMap, removed);
/*    */   }
/*    */   
/*    */   protected abstract Packet generateUpdatePacket(M paramM, Map<K, T> paramMap);
/*    */   
/*    */   @Nullable
/*    */   protected abstract Packet generateRemovePacket(M paramM, Set<K> paramSet);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\packet\SimpleAssetPacketGenerator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */