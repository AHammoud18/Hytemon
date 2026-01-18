/*    */ package com.hypixel.hytale.server.core.plugin.registry;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.assetstore.JsonAsset;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.lookup.Priority;
/*    */ import com.hypixel.hytale.codec.lookup.StringCodecMapCodec;
/*    */ import com.hypixel.hytale.function.consumer.BooleanConsumer;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class CodecMapRegistry<T, C extends Codec<? extends T>>
/*    */   implements IRegistry
/*    */ {
/*    */   protected final StringCodecMapCodec<T, C> mapCodec;
/*    */   protected final List<BooleanConsumer> unregister;
/*    */   
/*    */   public CodecMapRegistry(List<BooleanConsumer> unregister, StringCodecMapCodec<T, C> mapCodec) {
/* 21 */     this.unregister = unregister;
/* 22 */     this.mapCodec = mapCodec;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public CodecMapRegistry<T, C> register(String id, Class<? extends T> aClass, C codec) {
/* 27 */     this.mapCodec.register(id, aClass, (Codec)codec);
/* 28 */     this.unregister.add(shutdown -> {
/*    */           AssetRegistry.ASSET_LOCK.writeLock().lock();
/*    */           try {
/*    */             this.mapCodec.remove(aClass);
/*    */           } finally {
/*    */             AssetRegistry.ASSET_LOCK.writeLock().unlock();
/*    */           } 
/*    */         });
/* 36 */     return this;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public CodecMapRegistry<T, C> register(@Nonnull Priority priority, @Nonnull String id, Class<? extends T> aClass, C codec) {
/* 41 */     this.mapCodec.register(priority, id, aClass, (Codec)codec);
/* 42 */     this.unregister.add(shutdown -> {
/*    */           AssetRegistry.ASSET_LOCK.writeLock().lock();
/*    */           try {
/*    */             this.mapCodec.remove(aClass);
/*    */           } finally {
/*    */             AssetRegistry.ASSET_LOCK.writeLock().unlock();
/*    */           } 
/*    */         });
/* 50 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void shutdown() {}
/*    */   
/*    */   public static class Assets<T extends JsonAsset<?>, C extends Codec<? extends T>>
/*    */     extends CodecMapRegistry<T, C>
/*    */   {
/*    */     public Assets(List<BooleanConsumer> unregister, StringCodecMapCodec<T, C> mapCodec) {
/* 60 */       super(unregister, mapCodec);
/*    */     }
/*    */     
/*    */     @Nonnull
/*    */     public Assets<T, C> register(@Nonnull String id, Class<? extends T> aClass, BuilderCodec<? extends T> codec) {
/* 65 */       ((AssetCodecMapCodec)this.mapCodec).register(id, aClass, codec);
/* 66 */       this.unregister.add(shutdown -> {
/*    */             AssetRegistry.ASSET_LOCK.writeLock().lock();
/*    */             try {
/*    */               this.mapCodec.remove(aClass);
/*    */             } finally {
/*    */               AssetRegistry.ASSET_LOCK.writeLock().unlock();
/*    */             } 
/*    */           });
/* 74 */       return this;
/*    */     }
/*    */     
/*    */     @Nonnull
/*    */     public Assets<T, C> register(@Nonnull Priority priority, @Nonnull String id, Class<? extends T> aClass, BuilderCodec<? extends T> codec) {
/* 79 */       ((AssetCodecMapCodec)this.mapCodec).register(priority, id, aClass, codec);
/* 80 */       this.unregister.add(shutdown -> {
/*    */             AssetRegistry.ASSET_LOCK.writeLock().lock();
/*    */             try {
/*    */               this.mapCodec.remove(aClass);
/*    */             } finally {
/*    */               AssetRegistry.ASSET_LOCK.writeLock().unlock();
/*    */             } 
/*    */           });
/* 88 */       return this;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\plugin\registry\CodecMapRegistry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */