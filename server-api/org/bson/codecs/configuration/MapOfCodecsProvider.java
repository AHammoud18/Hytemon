/*    */ package org.bson.codecs.configuration;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.bson.codecs.Codec;
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
/*    */ final class MapOfCodecsProvider
/*    */   implements CodecProvider
/*    */ {
/* 26 */   private final Map<Class<?>, Codec<?>> codecsMap = new HashMap<>();
/*    */   
/*    */   MapOfCodecsProvider(List<? extends Codec<?>> codecsList) {
/* 29 */     for (Codec<?> codec : codecsList) {
/* 30 */       this.codecsMap.put(codec.getEncoderClass(), codec);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
/* 37 */     return (Codec<T>)this.codecsMap.get(clazz);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\configuration\MapOfCodecsProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */