/*    */ package com.hypixel.hytale.server.core.asset.common;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.Asset;
/*    */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
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
/*    */ public class PlayerCommonAssets
/*    */ {
/*    */   @Nonnull
/* 20 */   private final Map<String, String> assetMissing = (Map<String, String>)new Object2ObjectOpenHashMap(); public PlayerCommonAssets(@Nonnull Asset[] requiredAssets) {
/* 21 */     for (Asset requiredAsset : requiredAssets) {
/* 22 */       this.assetMissing.put(requiredAsset.hash, requiredAsset.name);
/*    */     }
/* 24 */     this.assetSent = (Map<String, String>)new Object2ObjectOpenHashMap();
/*    */   } @Nonnull
/*    */   private final Map<String, String> assetSent;
/*    */   public void sent(@Nullable Asset[] hashes) {
/* 28 */     Set<String> set = new HashSet<>();
/* 29 */     if (hashes != null) {
/* 30 */       for (Asset hash : hashes) {
/* 31 */         set.add(hash.hash);
/*    */       }
/*    */     }
/*    */     
/* 35 */     Iterator<String> iterator = this.assetMissing.keySet().iterator();
/* 36 */     while (iterator.hasNext()) {
/* 37 */       String hash = iterator.next();
/* 38 */       if (set.contains(hash)) {
/* 39 */         iterator.remove();
/* 40 */         set.remove(hash);
/*    */       } 
/*    */     } 
/*    */     
/* 44 */     if (!set.isEmpty()) throw new RuntimeException("Still had hashes: " + String.valueOf(set)); 
/* 45 */     this.assetSent.putAll(this.assetMissing);
/* 46 */     this.assetMissing.clear();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\common\PlayerCommonAssets.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */