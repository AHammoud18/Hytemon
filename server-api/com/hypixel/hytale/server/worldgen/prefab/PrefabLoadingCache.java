/*    */ package com.hypixel.hytale.server.worldgen.prefab;
/*    */ import com.hypixel.hytale.server.core.prefab.selection.buffer.PrefabBufferUtil;
/*    */ import com.hypixel.hytale.server.core.prefab.selection.buffer.impl.IPrefabBuffer;
/*    */ import com.hypixel.hytale.server.core.prefab.selection.buffer.impl.PrefabBuffer;
/*    */ import com.hypixel.hytale.server.worldgen.loader.WorldGenPrefabSupplier;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class PrefabLoadingCache {
/*    */   private final Map<WorldGenPrefabSupplier, PrefabBuffer> cache;
/*    */   
/*    */   public PrefabLoadingCache() {
/* 14 */     this.cache = new ConcurrentHashMap<>();
/* 15 */     this.loader = (p -> PrefabBufferUtil.loadBuffer(p.getPath()));
/*    */   } private final Function<WorldGenPrefabSupplier, PrefabBuffer> loader;
/*    */   @Nonnull
/*    */   public IPrefabBuffer getPrefabAccessor(WorldGenPrefabSupplier prefabSupplier) {
/* 19 */     return (IPrefabBuffer)((PrefabBuffer)this.cache.computeIfAbsent(prefabSupplier, this.loader)).newAccess();
/*    */   }
/*    */   
/*    */   public void clear() {
/* 23 */     this.cache.values().removeIf(buffer -> {
/*    */           buffer.release();
/*    */           return true;
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 32 */     return "PrefabLoadingCache{cache=" + String.valueOf(this.cache) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\prefab\PrefabLoadingCache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */