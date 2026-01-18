/*    */ package org.bson.codecs;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import org.bson.UuidRepresentation;
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
/*    */ public class UuidCodecProvider
/*    */   implements CodecProvider
/*    */ {
/*    */   private UuidRepresentation uuidRepresentation;
/*    */   
/*    */   public UuidCodecProvider(UuidRepresentation uuidRepresentation) {
/* 44 */     this.uuidRepresentation = uuidRepresentation;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
/* 50 */     if (clazz == UUID.class) {
/* 51 */       return new UuidCodec(this.uuidRepresentation);
/*    */     }
/* 53 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\UuidCodecProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */