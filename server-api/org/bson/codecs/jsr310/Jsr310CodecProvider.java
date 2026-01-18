/*    */ package org.bson.codecs.jsr310;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.bson.codecs.Codec;
/*    */ import org.bson.codecs.configuration.CodecProvider;
/*    */ import org.bson.codecs.configuration.CodecRegistry;
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
/*    */ public class Jsr310CodecProvider
/*    */   implements CodecProvider
/*    */ {
/* 42 */   private static final Map<Class<?>, Codec<?>> JSR310_CODEC_MAP = new HashMap<>();
/*    */   static {
/*    */     try {
/* 45 */       Class.forName("java.time.Instant");
/* 46 */       putCodec(new InstantCodec());
/* 47 */       putCodec(new LocalDateCodec());
/* 48 */       putCodec(new LocalDateTimeCodec());
/* 49 */       putCodec(new LocalTimeCodec());
/* 50 */     } catch (ClassNotFoundException classNotFoundException) {}
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static void putCodec(Codec<?> codec) {
/* 56 */     JSR310_CODEC_MAP.put(codec.getEncoderClass(), codec);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
/* 62 */     return (Codec<T>)JSR310_CODEC_MAP.get(clazz);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\jsr310\Jsr310CodecProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */