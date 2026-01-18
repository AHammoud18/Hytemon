/*    */ package org.bson.internal;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import java.util.concurrent.ConcurrentMap;
/*    */ import org.bson.codecs.Codec;
/*    */ import org.bson.codecs.configuration.CodecConfigurationException;
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
/*    */ final class CodecCache
/*    */ {
/* 29 */   private final ConcurrentMap<Class<?>, Optional<Codec<?>>> codecCache = new ConcurrentHashMap<>();
/*    */   
/*    */   public boolean containsKey(Class<?> clazz) {
/* 32 */     return this.codecCache.containsKey(clazz);
/*    */   }
/*    */   
/*    */   public void put(Class<?> clazz, Codec<?> codec) {
/* 36 */     this.codecCache.put(clazz, Optional.ofNullable(codec));
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized <T> Codec<T> putIfMissing(Class<T> clazz, Codec<T> codec) {
/* 41 */     Optional<Codec<?>> cachedCodec = this.codecCache.computeIfAbsent(clazz, clz -> Optional.of(codec));
/* 42 */     if (cachedCodec.isPresent()) {
/* 43 */       return (Codec<T>)cachedCodec.get();
/*    */     }
/* 45 */     this.codecCache.put(clazz, Optional.of(codec));
/* 46 */     return codec;
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> Codec<T> getOrThrow(Class<T> clazz) {
/* 51 */     return (Codec<T>)((Optional)this.codecCache.getOrDefault(clazz, Optional.empty())).orElseThrow(() -> new CodecConfigurationException(String.format("Can't find a codec for %s.", new Object[] { clazz })));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\internal\CodecCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */