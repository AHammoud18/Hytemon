/*    */ package com.hypixel.hytale.server.core.meta;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
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
/*    */ public interface IMetaRegistry<K>
/*    */ {
/*    */   <T> T newMetaObject(MetaKey<T> paramMetaKey, K paramK);
/*    */   
/*    */   void forEachMetaEntry(IMetaStore<K> paramIMetaStore, MetaEntryConsumer paramMetaEntryConsumer);
/*    */   
/*    */   @Nullable
/*    */   PersistentMetaKey<?> getMetaKeyForCodecKey(String paramString);
/*    */   
/*    */   <T> MetaKey<T> registerMetaObject(Function<K, T> paramFunction, boolean paramBoolean, String paramString, Codec<T> paramCodec);
/*    */   
/*    */   default <T> MetaKey<T> registerMetaObject(Function<K, T> supplier, String keyName, Codec<T> codec) {
/* 46 */     return registerMetaObject(supplier, true, keyName, codec);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default <T> MetaKey<T> registerMetaObject(Function<K, T> supplier) {
/* 57 */     return registerMetaObject(supplier, false, null, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default <T> MetaKey<T> registerMetaObject() {
/* 67 */     return registerMetaObject(parent -> null);
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface MetaEntryConsumer {
/*    */     <T> void accept(MetaKey<T> param1MetaKey, T param1T);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\meta\IMetaRegistry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */