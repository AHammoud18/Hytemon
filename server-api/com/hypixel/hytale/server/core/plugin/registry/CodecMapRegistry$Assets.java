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
/*    */ public class Assets<T extends JsonAsset<?>, C extends Codec<? extends T>>
/*    */   extends CodecMapRegistry<T, C>
/*    */ {
/*    */   public Assets(List<BooleanConsumer> unregister, StringCodecMapCodec<T, C> mapCodec) {
/* 60 */     super(unregister, mapCodec);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Assets<T, C> register(@Nonnull String id, Class<? extends T> aClass, BuilderCodec<? extends T> codec) {
/* 65 */     ((AssetCodecMapCodec)this.mapCodec).register(id, aClass, codec);
/* 66 */     this.unregister.add(shutdown -> {
/*    */           AssetRegistry.ASSET_LOCK.writeLock().lock();
/*    */           try {
/*    */             this.mapCodec.remove(aClass);
/*    */           } finally {
/*    */             AssetRegistry.ASSET_LOCK.writeLock().unlock();
/*    */           } 
/*    */         });
/* 74 */     return this;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Assets<T, C> register(@Nonnull Priority priority, @Nonnull String id, Class<? extends T> aClass, BuilderCodec<? extends T> codec) {
/* 79 */     ((AssetCodecMapCodec)this.mapCodec).register(priority, id, aClass, codec);
/* 80 */     this.unregister.add(shutdown -> {
/*    */           AssetRegistry.ASSET_LOCK.writeLock().lock();
/*    */           try {
/*    */             this.mapCodec.remove(aClass);
/*    */           } finally {
/*    */             AssetRegistry.ASSET_LOCK.writeLock().unlock();
/*    */           } 
/*    */         });
/* 88 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\plugin\registry\CodecMapRegistry$Assets.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */