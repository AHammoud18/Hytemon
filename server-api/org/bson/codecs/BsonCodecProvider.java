/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.codecs.configuration.CodecProvider;
/*    */ import org.bson.codecs.configuration.CodecRegistry;
/*    */ import org.bson.conversions.Bson;
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
/*    */ public class BsonCodecProvider
/*    */   implements CodecProvider
/*    */ {
/*    */   public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
/* 33 */     if (Bson.class.isAssignableFrom(clazz)) {
/* 34 */       return new BsonCodec(registry);
/*    */     }
/* 36 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BsonCodecProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */