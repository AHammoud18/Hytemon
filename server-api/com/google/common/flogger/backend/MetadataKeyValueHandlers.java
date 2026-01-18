/*    */ package com.google.common.flogger.backend;
/*    */ 
/*    */ import com.google.common.flogger.MetadataKey;
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
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
/*    */ public final class MetadataKeyValueHandlers
/*    */ {
/* 32 */   private static final MetadataHandler.ValueHandler<Object, MetadataKey.KeyValueHandler> EMIT_METADATA = new MetadataHandler.ValueHandler<Object, MetadataKey.KeyValueHandler>()
/*    */     {
/*    */       public void handle(MetadataKey<Object> key, Object value, MetadataKey.KeyValueHandler kvf)
/*    */       {
/* 36 */         key.emit(value, kvf);
/*    */       }
/*    */     };
/*    */   
/* 40 */   private static final MetadataHandler.RepeatedValueHandler<Object, MetadataKey.KeyValueHandler> EMIT_REPEATED_METADATA = new MetadataHandler.RepeatedValueHandler<Object, MetadataKey.KeyValueHandler>()
/*    */     {
/*    */       public void handle(MetadataKey<Object> key, Iterator<Object> value, MetadataKey.KeyValueHandler kvf)
/*    */       {
/* 44 */         key.emitRepeated(value, kvf);
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   public static MetadataHandler.ValueHandler<Object, MetadataKey.KeyValueHandler> getDefaultValueHandler() {
/* 50 */     return EMIT_METADATA;
/*    */   }
/*    */ 
/*    */   
/*    */   public static MetadataHandler.RepeatedValueHandler<Object, MetadataKey.KeyValueHandler> getDefaultRepeatedValueHandler() {
/* 55 */     return EMIT_REPEATED_METADATA;
/*    */   }
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
/*    */   public static MetadataHandler.Builder<MetadataKey.KeyValueHandler> getDefaultBuilder(Set<MetadataKey<?>> ignored) {
/* 70 */     return MetadataHandler.<MetadataKey.KeyValueHandler>builder(getDefaultValueHandler())
/* 71 */       .setDefaultRepeatedHandler(getDefaultRepeatedValueHandler())
/* 72 */       .ignoring(ignored);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static MetadataHandler<MetadataKey.KeyValueHandler> getDefaultHandler(Set<MetadataKey<?>> ignored) {
/* 84 */     return getDefaultBuilder(ignored).build();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\backend\MetadataKeyValueHandlers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */