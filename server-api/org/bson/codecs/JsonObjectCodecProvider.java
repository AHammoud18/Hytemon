/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.codecs.configuration.CodecProvider;
/*    */ import org.bson.codecs.configuration.CodecRegistry;
/*    */ import org.bson.json.JsonObject;
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
/*    */ public final class JsonObjectCodecProvider
/*    */   implements CodecProvider
/*    */ {
/*    */   public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
/* 33 */     if (clazz.equals(JsonObject.class)) {
/* 34 */       return new JsonObjectCodec();
/*    */     }
/* 36 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\JsonObjectCodecProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */