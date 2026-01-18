/*    */ package org.bson.internal;
/*    */ 
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
/*    */ public final class CodecRegistryHelper
/*    */ {
/*    */   public static CodecRegistry createRegistry(CodecRegistry codecRegistry, UuidRepresentation uuidRepresentation) {
/* 25 */     if (uuidRepresentation == UuidRepresentation.UNSPECIFIED) {
/* 26 */       return codecRegistry;
/*    */     }
/* 28 */     return new OverridableUuidRepresentationCodecRegistry((CodecProvider)codecRegistry, uuidRepresentation);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\internal\CodecRegistryHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */