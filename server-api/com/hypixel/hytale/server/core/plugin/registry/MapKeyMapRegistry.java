/*    */ package com.hypixel.hytale.server.core.plugin.registry;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.lookup.MapKeyMapCodec;
/*    */ import com.hypixel.hytale.function.consumer.BooleanConsumer;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class MapKeyMapRegistry<V>
/*    */   implements IRegistry
/*    */ {
/*    */   protected final MapKeyMapCodec<V> mapCodec;
/*    */   protected final List<BooleanConsumer> unregister;
/*    */   
/*    */   public MapKeyMapRegistry(List<BooleanConsumer> unregister, MapKeyMapCodec<V> mapCodec) {
/* 17 */     this.unregister = unregister;
/* 18 */     this.mapCodec = mapCodec;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public <T extends V> MapKeyMapRegistry<V> register(@Nonnull Class<T> tClass, @Nonnull String id, @Nonnull Codec<T> codec) {
/* 23 */     this.mapCodec.register(tClass, id, codec);
/* 24 */     this.unregister.add(shutdown -> {
/*    */           if (shutdown)
/*    */             return;  AssetRegistry.ASSET_LOCK.writeLock().lock();
/*    */           try {
/*    */             this.mapCodec.unregister(tClass);
/*    */           } finally {
/*    */             AssetRegistry.ASSET_LOCK.writeLock().unlock();
/*    */           } 
/*    */         });
/* 33 */     return this;
/*    */   }
/*    */   
/*    */   public void shutdown() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\plugin\registry\MapKeyMapRegistry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */