/*    */ package com.hypixel.hytale.server.core.meta;
/*    */ 
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
/*    */ public interface IMetaStore<K>
/*    */ {
/*    */   IMetaStoreImpl<K> getMetaStore();
/*    */   
/*    */   default <T> T getMetaObject(MetaKey<T> key) {
/* 21 */     return (T)getMetaStore().getMetaObject(key);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   default <T> T getIfPresentMetaObject(MetaKey<T> key) {
/* 26 */     return (T)getMetaStore().getIfPresentMetaObject(key);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   default <T> T putMetaObject(MetaKey<T> key, T obj) {
/* 31 */     return (T)getMetaStore().putMetaObject(key, obj);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   default <T> T removeMetaObject(MetaKey<T> key) {
/* 36 */     return (T)getMetaStore().removeMetaObject(key);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   default <T> T removeSerializedMetaObject(MetaKey<T> key) {
/* 41 */     return (T)getMetaStore().removeSerializedMetaObject(key);
/*    */   }
/*    */   
/*    */   default boolean hasMetaObject(MetaKey<?> key) {
/* 45 */     return getMetaStore().hasMetaObject(key);
/*    */   }
/*    */   
/*    */   default void forEachMetaObject(MetaEntryConsumer consumer) {
/* 49 */     getMetaStore().forEachMetaObject(consumer);
/*    */   }
/*    */   
/*    */   default void markMetaStoreDirty() {
/* 53 */     getMetaStore().markMetaStoreDirty();
/*    */   }
/*    */   
/*    */   default boolean consumeMetaStoreDirty() {
/* 57 */     return getMetaStore().consumeMetaStoreDirty();
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface MetaEntryConsumer {
/*    */     <T> void accept(int param1Int, T param1T);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\meta\IMetaStore.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */